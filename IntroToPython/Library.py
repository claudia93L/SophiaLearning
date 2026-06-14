import json
import os
from Book import Book, Comic

class Library:
    """Manages the full collection of Book and Comic objects."""

    def __init__(self):
        self.books = []

    def load(self, filename):
        """Load books from a JSON file if it exists."""
        if os.path.exists(filename):
            with open(filename, "r", encoding="utf-8") as f:
                data = json.load(f)
            for entry in data:
                if entry["type"] == "comic":
                    book = Comic(
                        entry["title"], entry["author"], entry["author_gender"],
                        entry["publisher"], entry["price"], entry["read_status"],
                        entry["illustrator"], entry["illustrator_gender"], entry["style"]
                    )
                else:
                    book = Book(
                        entry["title"], entry["author"], entry["author_gender"],
                        entry["publisher"], entry["price"], entry["read_status"]
                    )
                self.books.append(book)
            print(f"  Library loaded: {len(self.books)} book(s) found.")
        else:
            print("  No existing library found. Starting fresh.")

    def save(self, filename):
        """Save all books to a JSON file."""
        with open(filename, "w", encoding="utf-8") as f:
            json.dump([b.to_dict() for b in self.books], f, indent=2, ensure_ascii=False)

    def add_book(self, book, filename):
        """Add a book to the collection and save."""
        self.books.append(book)
        self.save(filename)
        print(f"  '{book.title}' added to the library.")

    def remove_book(self, title, filename):
        """Remove a book by title and save."""
        for book in self.books:
            if book.title.lower() == title.lower():
                self.books.remove(book)
                self.save(filename)
                print(f"  '{book.title}' removed from the library.")
                return
        print("  Book not found.")

    def edit_book(self, title, field, new_value, filename):
        """Edit a field of a book identified by title."""
        comic_fields = ["illustrator", "illustrator_gender", "style"]
        for book in self.books:
            if book.title.lower() == title.lower():
                if field in comic_fields and isinstance(book, Comic):
                    book.update_illustrator(field, new_value)
                else:
                    book.update(field, new_value)
                self.save(filename)
                return
        print("  Book not found.")

    def mark_read(self, title, filename):
        """Mark a book as read by title."""
        for book in self.books:
            if book.title.lower() == title.lower():
                book.mark_as_read()
                self.save(filename)
                print(f"  '{book.title}' marked as read.")
                return
        print("  Book not found.")

    def display_all(self):
        """Display all books in the library."""
        if not self.books:
            print("  The library is empty.")
            return
        print(f"\n  === Library ({len(self.books)} book(s)) ===")
        for book in self.books:
            book.display()

    def search_by_title(self, query):
        """Search books by title (case-insensitive partial match)."""
        results = [b for b in self.books if query.lower() in b.title.lower()]
        self._print_results(results, f"title containing '{query}'")

    def search_by_author(self, query):
        """Search books by author name (case-insensitive partial match)."""
        results = [b for b in self.books if query.lower() in b.author.lower()]
        self._print_results(results, f"author containing '{query}'")

    def filter_by_type(self, book_type):
        """Filter books by type: 'novel' or 'comic'."""
        results = [b for b in self.books if b.type == book_type.lower()]
        self._print_results(results, f"type '{book_type}'")

    def filter_by_style(self, style):
        """Filter comics by visual style."""
        results = [b for b in self.books
                   if isinstance(b, Comic) and b.style.lower() == style.lower()]
        self._print_results(results, f"style '{style}'")

    def filter_by_author_gender(self, gender):
        """Filter books by author gender."""
        results = [b for b in self.books if b.author_gender.lower() == gender.lower()]
        self._print_results(results, f"author gender '{gender}'")

    def filter_by_illustrator_gender(self, gender):
        """Filter comics by illustrator gender."""
        results = [b for b in self.books
                   if isinstance(b, Comic) and b.illustrator_gender.lower() == gender.lower()]
        self._print_results(results, f"illustrator gender '{gender}'")

    def total_value(self):
        """Calculate and display the total value of the library."""
        total = sum(b.price for b in self.books)
        print(f"\n  Total library value: {total:.2f} EUR")
        print(f"  ({len(self.books)} book(s) counted)")

    def _print_results(self, results, label):
        """Helper method to print a list of results."""
        if not results:
            print(f"  No results found for {label}.")
            return
        print(f"\n  === Results for {label} ({len(results)} found) ===")
        for book in results:
            book.display()