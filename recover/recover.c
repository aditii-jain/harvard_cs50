#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>

typedef uint8_t BYTE;

int main(int argc, char *argv[])
{
    if (argc != 2)
    {
        printf("Usage: ./recover IMAGE\n");
        return 1;
    }

    // open memory card
    FILE *memory_card = fopen(argv[1], "r");
    if (memory_card == NULL)
    {
        printf("Could not open file.\n");
        return 1;
    }

    int count = 0;
    char filename[8];
    FILE *jpeg;
    // repeat until end of card
    BYTE buffer[512];
    // read 512 bytes into a buffer (fread)
    while (fread(buffer, 512, 1, memory_card))
    {
        // if start of new JPEG
        if (buffer[0] == 0xff && buffer[1] == 0xd8
            && buffer[2] == 0xff && (buffer[3] & 0xf0) == 0xe0)
        {

            if (count != 0)
            {
                // if already found JPEG
                // close the previous file and open up a new one
                fclose(jpeg);

            }
            sprintf(filename, "%03i.jpg", count);
            // if first JPEG
            jpeg = fopen(filename, "w");
            count++;
            fwrite(buffer, 512, 1, jpeg);

        }
        else if (count > 0)
        {
            fwrite(buffer, 512, 1, jpeg);
        }
    }
}