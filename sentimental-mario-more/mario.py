from cs50 import get_int
while True:
    height = get_int("Height: ")
    if height >= 1 and height <= 8:
        break


for col in range(height):  # (int col = 0; col < height; col++)
    for spaceNum in range((height-1)-col):  # (int spaceNum = 0; spaceNum < (height-1)-col; spaceNum++)
        print(" ", end="")

    for row in range(col+1):  # (int row = 0; row <= col; row++)
        print("#", end="")

    print("  ", end="")

    for row in range(col+1):
        print("#", end="")

    print("")
