# main.py 

# This is the entry point of the program.
# It initializes the Library, loads existing data from the JSON file,
# and runs the main menu loop until the user chooses to quit.


from Library import Library
from inputHelpers import collect_novel_data, collect_comic_data, get_input, get_gender

import json
import os

FILENAME = "library.json"

def print_menu():
    print("\n" + "=" * 44)
    print("         MY HOME LIBRARY MANAGER")
    print("=" * 44)
    print("  1.  Add a book")
    print("  2.  Remove a book")
    print("  3.  Edit a book")
    print("  4.  Mark a book as read")
    print("  5.  View full library")
    print("  6.  Search by title")
    print("  7.  Search by author")
    print("  8.  Filter by type (novel / comic)")
    print("  9.  Filter by style")
    print("  10. Filter by author gender")
    print("  11. Filter by illustrator gender")
    print("  12. Total library value")
    print("  13. Quit")
    print("=" * 44)

def main():
    library = Library()
    library.load(FILENAME)

    while True:
        print_menu()
        choice = input("  Choose an option: ").strip()

        if choice == "1":
            print("\n  Add a book")
            book_type = input("  Type (novel / comic): ").strip().lower()
            if book_type == "novel":
                book = collect_novel_data()
            elif book_type == "comic":
                book = collect_comic_data()
            else:
                print("  Invalid type. Please enter 'novel' or 'comic'.")
                continue
            library.add_book(book, FILENAME)

        elif choice == "2":
            title = get_input("Title to remove")
            library.remove_book(title, FILENAME)

        elif choice == "3":
            title = get_input("Title to edit")
            print("  Fields: title, author, author_gender, publisher, price, read_status")
            print("  Comic-only fields: illustrator, illustrator_gender, style")
            field = get_input("Field to edit")
            new_value = get_input("New value")
            library.edit_book(title, field, new_value, FILENAME)

        elif choice == "4":
            title = get_input("Title to mark as read")
            library.mark_read(title, FILENAME)

        elif choice == "5":
            library.display_all()

        elif choice == "6":
            query = get_input("Search by title")
            library.search_by_title(query)

        elif choice == "7":
            query = get_input("Search by author")
            library.search_by_author(query)

        elif choice == "8":
            book_type = input("  Filter by type (novel / comic): ").strip().lower()
            library.filter_by_type(book_type)

        elif choice == "9":
            style = get_input("Filter by style")
            library.filter_by_style(style)

        elif choice == "10":
            gender = get_gender("Filter by author gender")
            library.filter_by_author_gender(gender)

        elif choice == "11":
            gender = get_gender("Filter by illustrator gender")
            library.filter_by_illustrator_gender(gender)

        elif choice == "12":
            library.total_value()

        elif choice == "13":
            print("\n  Goodbye!\n")
            break

        else:
            print("  Invalid option. Please choose a number from 1 to 13.")

if __name__ == "__main__":
    main()