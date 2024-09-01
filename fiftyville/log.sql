-- Keep a log of any SQL queries you execute as you solve the mystery.
SELECT description FROM crime_scene_reports
WHERE month = 7 AND day = 28 AND year = 2021 AND street = "Humphrey Street";

-- 10:15 am, bakery, interviews, 3
-- Theft of the CS50 duck took place at 10:15am at the Humphrey Street bakery.
-- Interviews were conducted today with three witnesses who were present at the time – each of their interview transcripts mentions the bakery.
-- now looking at the bakery security logs for more info
SELECT minute,activity,license_plate FROM bakery_security_logs
WHERE year = 2021 AND month = 7 AND day = 28 AND hour = 10;

SELECT transcript FROM interviews
WHERE year = 2021 AND month = 7 AND day = 28 AND transcript LIKE "%bakery%";

-- potential license plates: | 16     | exit     | 5P2BI95       |

SELECT account_number FROM atm_transactions
WHERE year = 2021 AND month = 7 AND day = 28 AND atm_location = "Leggett Street" AND transaction_type = "withdraw";

SELECT caller, receiver, duration FROM phone_calls
WHERE year = 2021 AND month = 7 AND day = 28 AND duration < 60;

SELECT name, license_plate, phone_number FROM people
JOIN phone_calls ON people.phone_number = phone_calls.caller
WHERE phone_calls.year = 2021 AND phone_calls.month = 7 AND phone_calls.day = 28 AND phone_calls.duration < 60;

SELECT name, license_plate, phone_number FROM people
JOIN bank_accounts ON people.id = bank_accounts.person_id
JOIN atm_transactions ON bank_accounts.account_number = atm_transactions.account_number
WHERE atm_transactions.year = 2021 AND atm_transactions.month = 7 AND atm_transactions.day = 28
AND atm_transactions.atm_location = "Leggett Street" AND atm_transactions.transaction_type = "withdraw";

SELECT minute,activity, people.license_plate, people.name, people.phone_number FROM bakery_security_logs
JOIN people ON bakery_security_logs.license_plate = people.license_plate
WHERE year = 2021 AND month = 7 AND day = 28 AND hour = 10;


SELECT id, abbreviation, full_name FROM airports
WHERE city = "Fiftyville"; -- 8

SELECT id FROM flights
WHERE origin_airport_id = 8 AND year = 2021 AND month = 7 AND day = 29;

-- intersects
SELECT people.name, people.passport_number FROM bakery_security_logs
JOIN people ON bakery_security_logs.license_plate = people.license_plate
WHERE year = 2021 AND month = 7 AND day = 28 AND hour = 10 AND minute BETWEEN 16 AND 23
INTERSECT
SELECT name, passport_number FROM people
JOIN bank_accounts ON people.id = bank_accounts.person_id
JOIN atm_transactions ON bank_accounts.account_number = atm_transactions.account_number
WHERE atm_transactions.year = 2021 AND atm_transactions.month = 7 AND atm_transactions.day = 28
AND atm_transactions.atm_location = "Leggett Street" AND atm_transactions.transaction_type = "withdraw"
INTERSECT
SELECT name, passport_number FROM people
JOIN phone_calls ON people.phone_number = phone_calls.caller
WHERE phone_calls.year = 2021 AND phone_calls.month = 7 AND phone_calls.day = 28 AND phone_calls.duration < 60
INTERSECT
SELECT name, people.passport_number FROM people
JOIN passengers ON people.passport_number = passengers.passport_number
WHERE passengers.flight_id IN (SELECT id FROM flights
WHERE origin_airport_id = 8 AND year = 2021 AND month = 7 AND day = 29 AND hour = 8);
-- BRUCE

SELECT flights.hour, flights.minute, airports.city FROM flights
JOIN airports ON flights.destination_airport_id = airports.id
WHERE origin_airport_id = 8 AND year = 2021 AND month = 7 AND day = 29;
-- New York City

SELECT name FROM people
JOIN phone_calls ON people.phone_number = phone_calls.receiver
WHERE caller = (SELECT phone_number FROM people WHERE name = "Bruce") AND
phone_calls.year = 2021 AND phone_calls.month = 7 AND phone_calls.day = 28 AND phone_calls.duration < 60;
-- ROBIN