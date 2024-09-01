#include <cs50.h>
#include <stdio.h>

int main(void)
{
    // American Express numbers start with 34 or 37
    // American Express uses 15-digit numbers
    // MasterCard numbers start with 51, 52, 53, 54, or 55
    // MasterCard uses 16-digit numbers
    // Visa numbers start with 4
    // Visa uses 13/16-digit
    long number;
    int length = 0;
    int beginning;
    int num;
    int a;
    int b;
    int sumOfMultiplied = 0;
    int sumOfNotMultiplied = 0;
    int sum = 0;
    int n;
    bool possiblyValid = false;
    number = get_long("Number: ");
    long card = number;
    do
    {
        number /= 10;
        length++;
        if (number >= 10 && number <= 99)
        {
            beginning = number;
        }
    }
    while (number != 0);
    //int times = length/2;
    int digits [length];
    //int notMultiplied [times];
    for (int i = 0; i < length; i++)
    {
        n = card % 10;
        digits [i] = n;
        card /= 10;
    }
    for (int i = 0; i < length; i++)
    {
        // odd place
        if (i % 2 != 0)
        {
            num = digits[i] * 2;
            if (num / 10 != 0)
            {
                a = num % 10;
                b = (num - a) / 10;
                sumOfMultiplied += (a + b);
            }
            else
            {
                sumOfMultiplied += num;
            }
        }
        else
        {
            sumOfNotMultiplied += digits[i];
        }
    }
    sum = sumOfNotMultiplied + sumOfMultiplied;
    if (sum % 10 == 0)
    {
        possiblyValid = true;
    }
    if ((beginning == 34 || beginning == 37) && length == 15 && possiblyValid)
    {
        printf("AMEX\n");
    }
    else if ((beginning >= 51 && beginning <= 55) && length == 16 && possiblyValid)
    {
        printf("MASTERCARD\n");
    }
    else if (((beginning / 10) == 4) && (length == 16 || length == 13) && possiblyValid)
    {
        printf("VISA\n");
    }
    else
    {
        printf("INVALID\n");
    }
}