// Main.java
// Entry point of the program.
// Initializes the Library, loads existing data from the catalog file,
// and runs the main menu loop until the user chooses to quit.

import java.util.Scanner;

public class Main {

    // The filename used to persist catalog data between sessions
    private static final String FILENAME = System.getProperty("user.dir") + 
                                       java.io.File.separator + "library.txt";

    // Prints the main menu options to the terminal
    private static void printMenu() {
        System.out.println("\n" + "=".repeat(44));
        System.out.println("        MY HOME LIBRARY CATALOG");
        System.out.println("=".repeat(44));
        System.out.println("  1.  Add an entry");
        System.out.println("  2.  Remove an entry");
        System.out.println("  3.  Edit an entry");
        System.out.println("  4.  Mark an entry as read");
        System.out.println("  5.  View full catalog");
        System.out.println("  6.  Search by title");
        System.out.println("  7.  Search by author");
        System.out.println("  8.  Filter by type (novel / comic)");
        System.out.println("  9.  Filter by style");
        System.out.println("  10. Filter by author gender");
        System.out.println("  11. Filter by illustrator gender");
        System.out.println("  12. Total catalog value");
        System.out.println("  13. Quit");
        System.out.println("=".repeat(44));
    }

    public static void main(String[] args) {
        Library library = new Library();
        // Load any previously saved catalog data from the text file
        library.load(FILENAME);

        Scanner scanner = new Scanner(System.in);

        // The loop continues until the user selects option 13
        while (true) {
            printMenu();
            System.out.print("  Choose an option: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {

                case "1":
                    // Ask the user whether the new entry is a novel or a comic,
                    // then collect the appropriate fields
                    System.out.print("  Type (novel / comic): ");
                    String entryType = scanner.nextLine().trim().toLowerCase();
                    if (entryType.equals("novel")) {
                        Book book = InputHelpers.collectNovelData();
                        library.addBook(book, FILENAME);
                    } else if (entryType.equals("comic")) {
                        Comic comic = InputHelpers.collectComicData();
                        library.addBook(comic, FILENAME);
                    } else {
                        System.out.println("  Invalid type. Please enter 'novel' or 'comic'.");
                    }
                    break;

                case "2":
                    String removeTitle = InputHelpers.getString("Title to remove", false);
                    library.removeBook(removeTitle, FILENAME);
                    break;

                case "3":
                    String editTitle = InputHelpers.getString("Title to edit", false);
                    System.out.println("  Fields: title, author, authorGender, publisher, price, readStatus");
                    System.out.println("  Comic-only fields: illustrator, illustratorGender, style");
                    String field    = InputHelpers.getString("Field to edit", false);
                    String newValue = InputHelpers.getString("New value", false);
                    library.editBook(editTitle, field, newValue, FILENAME);
                    break;

                case "4":
                    String readTitle = InputHelpers.getString("Title to mark as read", false);
                    library.markRead(readTitle, FILENAME);
                    break;

                case "5":
                    library.displayAll();
                    break;

                case "6":
                    String titleQuery = InputHelpers.getString("Search by title", false);
                    library.searchByTitle(titleQuery);
                    break;

                case "7":
                    String authorQuery = InputHelpers.getString("Search by author", false);
                    library.searchByAuthor(authorQuery);
                    break;

                case "8":
                    // The type filter only accepts "novel" or "comic"
                    System.out.print("  Filter by type (novel / comic): ");
                    String filterType = scanner.nextLine().trim().toLowerCase();
                    library.filterByType(filterType);
                    break;

                case "9":
                    String style = InputHelpers.getString("Filter by style", false);
                    library.filterByStyle(style);
                    break;

                case "10":
                    String authorGender = InputHelpers.getGender("Filter by author gender");
                    library.filterByAuthorGender(authorGender);
                    break;

                case "11":
                    String illustratorGender = InputHelpers.getGender("Filter by illustrator gender");
                    library.filterByIllustratorGender(illustratorGender);
                    break;

                case "12":
                    library.totalValue();
                    break;

                case "13":
                    System.out.println("\n  Goodbye!\n");
                    scanner.close();
                    return;

                default:
                    System.out.println("  Invalid option. Please choose a number from 1 to 13.");
            }
        }
    }
}
