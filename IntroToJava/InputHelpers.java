// InputHelpers.java
// Provides static helper methods for collecting and validating user input.
// All methods use a shared Scanner instance and loop until valid input is received.
// Keeping these methods in a separate class avoids cluttering the main menu logic.

import java.util.Scanner;
import java.util.InputMismatchException;

public class InputHelpers {

    // Shared Scanner instance used by all input methods
    private static Scanner scanner = new Scanner(System.in);

    // Prompts the user for a text value.
    // If allowEmpty is false, re-prompts until a non-empty string is entered.
    public static String getString(String prompt, boolean allowEmpty) {
        while (true) {
            System.out.print("  " + prompt + ": ");
            String value = scanner.nextLine().trim();
            if (!value.isEmpty() || allowEmpty) {
                return value;
            }
            System.out.println("  This field cannot be empty. Please try again.");
        }
    }

    // Prompts the user for a numeric price.
    // Returns 0.0 if the input is left empty.
    // Repeats until a valid double is entered.
    public static double getDouble(String prompt) {
        while (true) {
            System.out.print("  " + prompt + ": ");
            String value = scanner.nextLine().trim();
            if (value.isEmpty()) return 0.0;
            try {
                return Double.parseDouble(value);
            } catch (NumberFormatException e) {
                // Catch non-numeric input and re-prompt instead of crashing
                System.out.println("  Please enter a valid number.");
            }
        }
    }

    // Prompts the user for a gender value.
    // Only accepts "male", "female", or "other".
    public static String getGender(String label) {
        while (true) {
            System.out.print("  " + label + " (male / female / other): ");
            String value = scanner.nextLine().trim().toLowerCase();
            if (value.equals("male") || value.equals("female") || value.equals("other")) {
                return value;
            }
            System.out.println("  Please enter 'male', 'female', or 'other'.");
        }
    }

    // Prompts the user for a read status.
    // Accepts "yes"/"y" for true and "no"/"n" for false.
    public static boolean getReadStatus() {
        while (true) {
            System.out.print("  Have you read this? (yes / no): ");
            String value = scanner.nextLine().trim().toLowerCase();
            if (value.equals("yes") || value.equals("y")) return true;
            if (value.equals("no")  || value.equals("n")) return false;
            System.out.println("  Please enter 'yes' or 'no'.");
        }
    }

    // Collects all fields required to create a Book (novel) object
    // and returns the fully initialized instance
    public static Book collectNovelData() {
        String title        = getString("Title", false);
        String author       = getString("Author", false);
        String authorGender = getGender("Author gender");
        String publisher    = getString("Publisher (optional, press Enter to skip)", true);
        double price        = getDouble("Price in EUR (optional, press Enter to skip)");
        boolean readStatus  = getReadStatus();
        return new Book(title, author, authorGender, publisher, price, readStatus);
    }

    // Collects all fields required to create a Comic object,
    // including the three fields specific to illustrated works,
    // and returns the fully initialized instance
    public static Comic collectComicData() {
        String title              = getString("Title", false);
        String author             = getString("Author / Writer", false);
        String authorGender       = getGender("Author gender");
        String illustrator        = getString("Illustrator", false);
        String illustratorGender  = getGender("Illustrator gender");
        System.out.println("  Style options: manga, european, american, belgian, other");
        String style              = getString("Style", false);
        String publisher          = getString("Publisher (optional, press Enter to skip)", true);
        double price              = getDouble("Price in EUR (optional, press Enter to skip)");
        boolean readStatus        = getReadStatus();
        return new Comic(title, author, authorGender, publisher, price, readStatus,
                         illustrator, illustratorGender, style);
    }
}
