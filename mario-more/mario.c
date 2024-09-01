#include <cs50.h>
#include <stdio.h>

int main(void)
{
    int height;
    do
    {
        height = get_int("Height: ");
    }
     while (!(height >= 1 && height <= 8));

    for (int col = 0; col < height; col++) {
        for (int spaceNum = 0; spaceNum < (height-1)-col; spaceNum++) {
            printf(" ");
        }
        for (int row = 0; row <= col; row++) {

            printf("#");
        }
        printf("  ");
        for (int row = 0; row <= col; row++) {
            printf("#");
        }
        printf("\n");

    }

    // for (int col = 0; col < height; col++) {
    //     for (int row = 0; row <= col; row++) {
    //         printf("#");
    //     }
    //     printf("\n");

    // }

}