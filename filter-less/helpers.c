#include "helpers.h"
#include <math.h>
#include <string.h>

// Convert image to grayscale
int cap(int px);
int averageRGB(int height, int width, RGBTRIPLE copy[height][width], char *type, int i, int j);

void grayscale(int height, int width, RGBTRIPLE image[height][width])
{
    float average;
    for (int i = 0; i < height; i++)
    {
        for (int j = 0; j < width; j++)
        {
            average = round((image[i][j].rgbtBlue + image[i][j].rgbtRed + image[i][j].rgbtGreen) / 3.0);
            image[i][j].rgbtBlue = average;
            image[i][j].rgbtRed = average;
            image[i][j].rgbtGreen = average;
        }
    }

    return;
}

int cap(int px)
{
    if (px > 255)
    {
        return 255;
    }
    return px;
}
// Convert image to sepia
void sepia(int height, int width, RGBTRIPLE image[height][width])
{

    for (int i = 0; i < height; i++)
    {
        for (int j = 0; j < width; j++)
        {
            int sepiaRed = cap(round(.393 * image[i][j].rgbtRed + .769 * image[i][j].rgbtGreen + .189 * image[i][j].rgbtBlue));
            int sepiaGreen = cap(round(.349 * image[i][j].rgbtRed + .686 * image[i][j].rgbtGreen + .168 * image[i][j].rgbtBlue));
            int sepiaBlue = cap(round(.272 * image[i][j].rgbtRed + .534 * image[i][j].rgbtGreen + .131 * image[i][j].rgbtBlue));
            image[i][j].rgbtBlue = sepiaBlue;
            image[i][j].rgbtRed = sepiaRed;
            image[i][j].rgbtGreen = sepiaGreen;
        }
    }
    return;
}

// Reflect image horizontally
void reflect(int height, int width, RGBTRIPLE image[height][width])
{
    int temp[3];
    for (int i = 0; i < height; i++)
    {
        for (int j = 0; j < width / 2; j++)
        {
            temp[0] = image[i][j].rgbtBlue;
            image[i][j].rgbtBlue = image[i][width - (j + 1)].rgbtBlue;
            image[i][width - (j + 1)].rgbtBlue = temp[0];

            temp[1] = image[i][j].rgbtRed;
            image[i][j].rgbtRed = image[i][width - (j + 1)].rgbtRed;
            image[i][width - (j + 1)].rgbtRed = temp[1];

            temp[2] = image[i][j].rgbtGreen;
            image[i][j].rgbtGreen = image[i][width - (j + 1)].rgbtGreen;
            image[i][width - (j + 1)].rgbtGreen = temp[2];
        }
    }

    return;
}

// Blur image
void blur(int height, int width, RGBTRIPLE image[height][width])
{
    RGBTRIPLE copy[height][width];
    for (int i = 0; i < height; i++)
    {
        for (int j = 0; j < width; j++)
        {
            copy[i][j] = image[i][j];
        }
    }

    int averageRed;
    int averageBlue;
    int averageGreen;

    for (int i = 0; i < height; i++)
    {
        for (int j = 0; j < width; j++)
        {
            averageRed = averageRGB(height, width, copy, "red", i, j);
            averageBlue = averageRGB(height, width, copy, "blue", i, j);
            averageGreen = averageRGB(height, width, copy, "green", i, j);
            image[i][j].rgbtRed = averageRed;
            image[i][j].rgbtBlue = averageBlue;
            image[i][j].rgbtGreen = averageGreen;
        }
    }
    return;

}

int averageRGB(int height, int width, RGBTRIPLE copy[height][width], char *type, int i, int j)
{
    int sum = 0;
    float count = 0;
    for (int row = i - 1; row < (i + 2); row++)
    {
        for (int col = j - 1; col < (j + 2); col++)
        {
            // if invalid position continue
            if (row > height - 1 || row < 0 || col > width - 1 || col < 0)
            {
                continue;
            }
            if (strcmp(type, "red") == 0)
            {
                sum += copy[row][col].rgbtRed;
            }
            else if (strcmp(type, "green") == 0)
            {
                sum += copy[row][col].rgbtGreen;
            }
            else
            {
                sum += copy[row][col].rgbtBlue;
            }
            count++;
        }
    }

    return round(sum / count);
}
