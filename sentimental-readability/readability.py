from cs50 import get_string


def main():
    input = get_string("Text: ")
    letters = count_letters(input)
    words = count_words(input)
    sentences = count_sentences(input)

    L = (letters / words) * 100
    S = (sentences / words) * 100
    index = 0.0588 * L - 0.296 * S - 15.8

    # printing calculated grade level
    if index < 1:
        print("Before Grade 1")
    elif index > 16:
        print("Grade 16+")
    else:
        print(f"Grade {round(index)}")


def count_letters(text):
    letters = 0
    # length = len(text)
    for c in text:  # (int i = 0, length = strlen(text); i < length; i++)
        if c.isalpha():
            letters += 1
    return letters


def count_words(text):
    words = 0
    for c in text:
        if c == " ":
            words += 1
    return words + 1


def count_sentences(text):
    sentences = 0
    for c in text:
        if c == "." or c == "!" or c == "?":
            sentences += 1
    return sentences


main()