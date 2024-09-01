#include <ctype.h>
#include <cs50.h>
#include <stdio.h>
#include <string.h>

int main(int argc, string argv[])
{
    string key = argv[1];
    if (argc < 2) // no key
    {
        printf("Usage: ./substitution key\n");
        return 1;
    }
    if (argc > 2) // more than two arguments
    {
        printf("Usage: ./substitution key\n");
        return 1;
    }
    if (strlen(argv[1]) != 26) // length of key is not 26
    {
        printf("Key must contain 26 characters.\n");
        return 1;
    }
    // checking for invalid characters in key
    for (int i = 0, length = strlen(key); i < length; i++)
    {
        if (!isalpha(key[i]))
        {
            return 1;
        }
    }
    // checking for duplicates in key using double for loop
    for (int i = 0, length = strlen(key); i < length; i++)
    {
        for (int j = i + 1; j < length; j++)
        {
            if (tolower(key[j]) == tolower(key[i]))
            {
                return 1;
            }
        }
    }

    string plaintext = get_string("plaintext: ");
    bool wasLower;
    for (int i = 0, length = strlen(plaintext); i < length; i++)
    {
        if (isalpha(plaintext[i])) // is a letter
        {
            if (islower(plaintext[i]))
            {
                wasLower = true;
            }
            else
            {
                wasLower = false;
            }
            plaintext[i] = tolower(plaintext[i]);
            plaintext[i] = key[plaintext[i] - 97];
            plaintext[i] = tolower(plaintext[i]);
            if (!wasLower)
            {
                plaintext[i] = toupper(plaintext[i]);
            }
        }
    }
    printf("ciphertext: %s\n", plaintext);
    return 0;
}