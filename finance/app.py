import os

from cs50 import SQL
from flask import Flask, flash, redirect, render_template, request, session
from flask_session import Session
from tempfile import mkdtemp
from werkzeug.security import check_password_hash, generate_password_hash
import datetime

from helpers import apology, login_required, lookup, usd

# Configure application
app = Flask(__name__)

# Ensure templates are auto-reloaded
app.config["TEMPLATES_AUTO_RELOAD"] = True

# Custom filter
app.jinja_env.filters["usd"] = usd

# Configure session to use filesystem (instead of signed cookies)
app.config["SESSION_PERMANENT"] = False
app.config["SESSION_TYPE"] = "filesystem"
Session(app)

# Configure CS50 Library to use SQLite database
db = SQL("sqlite:///finance.db")

# Make sure API key is set
if not os.environ.get("API_KEY"):
    raise RuntimeError("API_KEY not set")


@app.after_request
def after_request(response):
    """Ensure responses aren't cached"""
    response.headers["Cache-Control"] = "no-cache, no-store, must-revalidate"
    response.headers["Expires"] = 0
    response.headers["Pragma"] = "no-cache"
    return response


@app.route("/")
@login_required
def index():
    """Show portfolio of stocks"""
    total = 0
    user_id = session["user_id"]

    transactions = db.execute(
        "SELECT symbol, price, SUM(shares) AS shares FROM transactions WHERE user_id = ? GROUP BY symbol", user_id)
    cash = db.execute("SELECT cash FROM users WHERE id = ?", user_id)[0]["cash"]

    for transaction in transactions:
        total += transaction["price"] * transaction["shares"]
    return render_template("index.html", transactions=transactions, cash=cash, total_stock=total)


@app.route("/buy", methods=["GET", "POST"])
@login_required
def buy():
    """Buy shares of stock"""
    if request.method == "POST":
        symbol = request.form.get("symbol")
        try:
            num_shares = int(request.form.get("shares"))
        except:
            return apology("must input positive integer")

        if not symbol:
            return apology("must input symbol")
        if lookup(symbol.upper()) == None:
            return apology("symbol does not exist")
        if num_shares < 0:
            return apology("must input positive integer")

        price = lookup(symbol.upper())["price"]

        user_id = session["user_id"]

        cash = db.execute("SELECT cash FROM users WHERE id = ?", user_id)[0]["cash"]

        cost = num_shares * price

        if cash < cost:
            return apology("cannot afford the number of shares at the current price")

        db.execute("UPDATE users SET cash = ? WHERE id = ?", cash - cost, user_id)
        db.execute("INSERT INTO transactions(user_id, symbol, price, shares, date) VALUES (?, ?, ?, ?, ?)",
                   user_id, symbol.upper(), price, num_shares, datetime.datetime.now())

        flash("Bought shares!")
        return redirect("/")

    else:
        return render_template("buy.html")


@app.route("/history")
@login_required
def history():
    """Show history of transactions"""
    user_id = session["user_id"]
    transactions = db.execute("SELECT symbol, price, shares, date FROM transactions WHERE user_id = ?", user_id)
    return render_template("history.html", transactions=transactions)


@app.route("/cash", methods=["GET", "POST"])
@login_required
def cash():
    """Allow user to add cash"""
    if request.method == "POST":

        amount = int(request.form.get("amount"))
        if not amount:
            return apology("must enter amount")
        if amount < 0:
            return apology("must enter positive amount")
        user_id = session["user_id"]
        cash = db.execute("SELECT cash FROM users WHERE id = ?", user_id)[0]["cash"]
        db.execute("UPDATE users SET cash = ? WHERE id = ?", cash + amount, user_id)
        flash("Cash Added!")
        return redirect("/")
    else:
        return render_template("cash.html")


@app.route("/login", methods=["GET", "POST"])
def login():
    """Log user in"""

    # Forget any user_id
    session.clear()

    # User reached route via POST (as by submitting a form via POST)
    if request.method == "POST":

        # Ensure username was submitted
        if not request.form.get("username"):
            return apology("must provide username", 403)

        # Ensure password was submitted
        elif not request.form.get("password"):
            return apology("must provide password", 403)

        # Query database for username
        rows = db.execute("SELECT * FROM users WHERE username = ?", request.form.get("username"))

        # Ensure username exists and password is correct
        if len(rows) != 1 or not check_password_hash(rows[0]["hash"], request.form.get("password")):
            return apology("invalid username and/or password", 403)

        # Remember which user has logged in
        session["user_id"] = rows[0]["id"]

        # Redirect user to home page
        return redirect("/")

    # User reached route via GET (as by clicking a link or via redirect)
    else:
        return render_template("login.html")


@app.route("/logout")
def logout():
    """Log user out"""

    # Forget any user_id
    session.clear()

    # Redirect user to login form
    return redirect("/")


@app.route("/quote", methods=["GET", "POST"])
@login_required
def quote():
    """Get stock quote."""
    if request.method == "GET":
        return render_template("quote.html")
    else:
        symbol = request.form.get("symbol")

        if not symbol:
            return apology("must enter stock symbol")

        stock = lookup(symbol.upper())

        if stock == None:
            return apology("this symbol does not exist")

        return render_template("quoted.html", name=stock["name"], price=stock["price"], symbol=stock["symbol"])


@app.route("/register", methods=["GET", "POST"])
def register():
    """Register user"""
    if request.method == "POST":
        username = request.form.get("username")
        password = request.form.get("password")
        confirmation = request.form.get("confirmation")
        if not username:
            return apology("must provide username")

        if not password or not confirmation:
            return apology("must provide password and confirmation")

        if not password == confirmation:
            return apology("passwords do not match")

        try:
            newUser = db.execute("INSERT INTO users(username, hash) VALUES (?, ?)", username, generate_password_hash(password))
        except:
            return apology("username already exists")

        session["user_id"] = newUser

        return redirect("/")

    else:
        return render_template("register.html")


@app.route("/sell", methods=["GET", "POST"])
@login_required
def sell():
    """Sell shares of stock"""
    if request.method == "POST":
        user_id = session["user_id"]
        symbol = request.form.get("symbol")
        num_shares = int(request.form.get("shares"))
        if not symbol:
            return apology("must select symbol")
        shares = db.execute(
            "SELECT SUM(shares) AS shares FROM transactions WHERE symbol = ? AND user_id = ? GROUP BY symbol", symbol, user_id)[0]["shares"]
        if shares == 0:
            return apology("you do not own any shares of this stock")

        if num_shares < 0:
            return apology("must input positive integer")

        if num_shares > shares:
            return apology("insufficent shares")

        price = lookup(symbol.upper())["price"]

        cash = db.execute("SELECT cash FROM users WHERE id = ?", user_id)[0]["cash"]

        cost = num_shares * price

        db.execute("UPDATE users SET cash = ? WHERE id = ?", cash + cost, user_id)
        db.execute("INSERT INTO transactions(user_id, symbol, price, shares, date) VALUES (?, ?, ?, ?, ?)",
                   user_id, symbol.upper(), price, -num_shares, datetime.datetime.now())
        flash("Sold!")
        return redirect("/")
    else:
        user_id = session["user_id"]
        transactions = db.execute("SELECT symbol FROM transactions WHERE user_id = ? GROUP BY symbol", user_id)
        return render_template("sell.html", transactions=transactions)

