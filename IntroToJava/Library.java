// Library.java
// Manages the full catalog of Book and Comic objects.
// Handles file I/O using BufferedReader and BufferedWriter,
// and provides all methods for adding, removing, editing,
// searching, and filtering entries.

import java.io.*;
import java.util.ArrayList;

public class Library {

    // The main data structure: a list of Book objects (may include Comic instances)
    private ArrayList<Book> books;

    // Constructor: initializes the catalog as an empty list
    public Library() {
        this.books = new ArrayList<>();
    }

    // Loads the catalog from a pipe-separated text file.
    // Each line represents one entry; the first field determines the type.
    public void load(String filename) {
        File file = new File(filename);
        if (!file.exists()) {
            System.out.println("  No existing catalog found. Starting fresh.");
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Skip empty lines
                if (line.trim().isEmpty()) continue;
                String[] fields = line.split("\\|", -1);
                String type = fields[0];
                if (type.equals("comic") && fields.length >= 10) {
                    // Reconstruct a Comic object from 10 fields
                    Comic comic = new Comic(
                        fields[1], fields[2], fields[3], fields[4],
                        Double.parseDouble(fields[5]),
                        Boolean.parseBoolean(fields[6]),
                        fields[7], fields[8], fields[9]
                    );
                    books.add(comic);
                } else if (fields.length >= 7) {
                    // Reconstruct a Book object from 7 fields
                    Book book = new Book(
                        fields[1], fields[2], fields[3], fields[4],
                        Double.parseDouble(fields[5]),
                        Boolean.parseBoolean(fields[6])
                    );
                    books.add(book);
                }
            }
            System.out.println("  Catalog loaded: " + books.size() + " entry/entries found.");
        } catch (IOException e) {
            System.out.println("  Error reading file: " + e.getMessage());
        }
    }

    // Writes all entries to the file, one per line in pipe-separated format
    public void save(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Book book : books) {
                writer.write(book.toFileLine());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("  Error saving file: " + e.getMessage());
        }
    }

    // Adds a new entry to the catalog and saves
    public void addBook(Book book, String filename) {
        books.add(book);
        save(filename);
        System.out.println("  '" + book.getTitle() + "' added to the catalog.");
    }

    // Removes an entry by title (case-insensitive exact match) and saves
    public void removeBook(String title, String filename) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                books.remove(book);
                save(filename);
                System.out.println("  '" + book.getTitle() + "' removed from the catalog.");
                return;
            }
        }
        System.out.println("  Entry not found.");
    }

    // Edits a specific field of an entry identified by title.
    // Comic-specific fields are routed to updateIllustrator(),
    // all other fields are routed to update().
    public void editBook(String title, String field, String newValue, String filename) {
        String[] comicFields = {"illustrator", "illustratorGender", "style"};
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                boolean isComicField = false;
                for (String cf : comicFields) {
                    if (cf.equals(field)) { isComicField = true; break; }
                }
                if (isComicField && book instanceof Comic) {
                    ((Comic) book).updateIllustrator(field, newValue);
                } else {
                    book.update(field, newValue);
                }
                save(filename);
                return;
            }
        }
        System.out.println("  Entry not found.");
    }

    // Marks an entry as read by title and saves
    public void markRead(String title, String filename) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                book.markAsRead();
                save(filename);
                System.out.println("  '" + book.getTitle() + "' marked as read.");
                return;
            }
        }
        System.out.println("  Entry not found.");
    }

    // Displays all entries in the catalog
    public void displayAll() {
        if (books.isEmpty()) {
            System.out.println("  The catalog is empty.");
            return;
        }
        System.out.println("\n  === Catalog (" + books.size() + " entry/entries) ===");
        for (Book book : books) {
            book.display();
        }
    }

    // Calculates and displays the total purchase value of all entries
    public void totalValue() {
        double total = 0.0;
        for (Book book : books) {
            total += book.getPrice();
        }
        System.out.printf("%n  Total catalog value: %.2f EUR%n", total);
        System.out.println("  (" + books.size() + " entry/entries counted)");
    }

    // Searches entries by title using a case-insensitive partial match
    public void searchByTitle(String query) {
        ArrayList<Book> results = new ArrayList<>();
        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(query.toLowerCase())) {
                results.add(book);
            }
        }
        printResults(results, "title containing '" + query + "'");
    }

    // Searches entries by author name using a case-insensitive partial match
    public void searchByAuthor(String query) {
        ArrayList<Book> results = new ArrayList<>();
        for (Book book : books) {
            if (book.getAuthor().toLowerCase().contains(query.toLowerCase())) {
                results.add(book);
            }
        }
        printResults(results, "author containing '" + query + "'");
    }

    // Filters entries by type: "novel" or "comic"
    public void filterByType(String type) {
        ArrayList<Book> results = new ArrayList<>();
        for (Book book : books) {
            if (book.getType().equalsIgnoreCase(type)) {
                results.add(book);
            }
        }
        printResults(results, "type '" + type + "'");
    }

    // Filters comic entries by visual style
    public void filterByStyle(String style) {
        ArrayList<Book> results = new ArrayList<>();
        for (Book book : books) {
            if (book instanceof Comic) {
                Comic comic = (Comic) book;
                if (comic.getStyle().equalsIgnoreCase(style)) {
                    results.add(book);
                }
            }
        }
        printResults(results, "style '" + style + "'");
    }

    // Filters all entries by author gender
    public void filterByAuthorGender(String gender) {
        ArrayList<Book> results = new ArrayList<>();
        for (Book book : books) {
            if (book.getAuthorGender().equalsIgnoreCase(gender)) {
                results.add(book);
            }
        }
        printResults(results, "author gender '" + gender + "'");
    }

    // Filters comic entries by illustrator gender
    public void filterByIllustratorGender(String gender) {
        ArrayList<Book> results = new ArrayList<>();
        for (Book book : books) {
            if (book instanceof Comic) {
                Comic comic = (Comic) book;
                if (comic.getIllustratorGender().equalsIgnoreCase(gender)) {
                    results.add(book);
                }
            }
        }
        printResults(results, "illustrator gender '" + gender + "'");
    }

    // Private helper: prints a list of results with a label.
    // The private modifier signals this method is for internal use only.
    private void printResults(ArrayList<Book> results, String label) {
        if (results.isEmpty()) {
            System.out.println("  No results found for " + label + ".");
            return;
        }
        System.out.println("\n  === Results for " + label + " (" + results.size() + " found) ===");
        for (Book book : results) {
            book.display();
        }
    }
}
