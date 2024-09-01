from cs50 import get_int
# American Express numbers start with 34 or 37
# American Express uses 15-digit numbers
# MasterCard numbers start with 51, 52, 53, 54, or 55
# MasterCard uses 16-digit numbers
# Visa numbers start with 4
# Visa uses 13/16-digit

length = 0
sumOfMultiplied = 0
sumOfNotMultiplied = 0
sum = 0
possiblyValid = False
number = get_int("Number: ")
card = number

while True:
    number = number // 10
    length += 1
    if number >= 10 and number <= 99:
        beginning = number
    if number == 0:
        break

digits = []

for i in range(length):  # for (int i = 0; i < length; i++)
    n = card % 10
    digits.insert(i, n)
    card = card // 10

for i in range(length):  # for (int i = 0; i < length; i++)
    # odd place
    if i % 2 != 0:
        num = digits[i] * 2
        if num / 10 != 0:
            a = num % 10
            b = (num - a) // 10
            sumOfMultiplied = (a + b) + sumOfMultiplied
        else:
            sumOfMultiplied = num + sumOfMultiplied
    else:
        sumOfNotMultiplied = digits[i] + sumOfNotMultiplied

sum = sumOfNotMultiplied + sumOfMultiplied

if sum % 10 == 0:
    possiblyValid = True

if ((beginning == 34 or beginning == 37) and length == 15 and possiblyValid):
    print("AMEX")
elif ((beginning >= 51 and beginning <= 55) and length == 16 and possiblyValid):
    print("MASTERCARD")
elif (((beginning // 10) == 4) and (length == 16 or length == 13) and possiblyValid):
    print("VISA")
else:
    print("INVALID")

