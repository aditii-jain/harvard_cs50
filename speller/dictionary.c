// Implements a dictionary's functionality
#include <string.h>
#include <stdbool.h>
#include <ctype.h>
#include <stdio.h>
#include <stdlib.h>
#include <strings.h>

#include "dictionary.h"

// Represents a node in a hash table
typedef struct node
{
    char word[LENGTH + 1];
    struct node *next;
}
node;

// TODO: Choose number of buckets in hash table
const unsigned int N = 17576;

unsigned int num_words = 0;

// Hash table
node *table[N];

// Returns true if word is in dictionary, else false
bool check(const char *word)
{
    // hash word to obtain a hash value
    unsigned int bucket = hash(word);
    // access linked list at that index in the hash table
    node *cursor = table[bucket];
    // traverse linked list, looking for the index (strcasecmp)
    while (cursor != NULL)
    {
        if (strcasecmp(cursor->word, word) == 0)
        {
            return true;
        }

        cursor = cursor->next;
    }
    return false;
}

// Hashes word to a number
unsigned int hash(const char *word)
{
    unsigned int bucket = 0;
    if (strlen(word) > 2)
    {
        bucket = (toupper(word[0]) - 'A') * 676 + (toupper(word[1]) - 'A') * 26 + (toupper(word[2]) - 'A');
    }
    else if (strlen(word) > 1)
    {
        bucket = (toupper(word[0]) - 'A') * 26 + (toupper(word[1]) - 'A');
    }
    else
    {
        bucket = toupper(word[0]) - 'A';
    }
    return bucket % N;
}

// Loads dictionary into memory, returning true if successful, else false
bool load(const char *dictionary)
{
    // opening dictionary
    FILE *file = fopen(dictionary, "r");
    if (file == NULL)
    {
        // unload();
        return false;
    }

    char buffer[LENGTH + 1];
    // put each string in buffer, which is added to a node
    while (fscanf(file, "%s", buffer) != EOF)
    {
        node *n = malloc(sizeof(node));
        if (n == NULL)
        {
            // unload();
            return false;
        }
        strcpy(n->word, buffer);
        n->next = NULL;
        // call hash function on that word to get a hash value
        int bucket = hash(buffer);

        // insert node into hash table at that location
        // nothing comes after this node
        if (table[bucket] == NULL)
        {
            table[bucket] = n;

        }
        else
        {
            n->next = table[bucket];
            table[bucket] = n;
        }
        num_words++;
    }
    fclose(file);
    return true;
}

// Returns number of words in dictionary if loaded, else 0 if not yet loaded
unsigned int size(void)
{
    return num_words;
}

// Unloads dictionary from memory, returning true if successful, else false
bool unload(void)
{
    node *blink = table[0];
    // node *cursor = malloc(sizeof(node));

    for (int i = 0; i < N; i++)
    {
        blink = table[i];
        node *tmp = table[i];

        while (blink != NULL)
        {
            blink = blink->next;
            free(tmp);
            tmp = blink;
        }
    }
    return true;

}
