// Book.java
// Base class representing a novel or general book entry in the catalog.
// All other entry types inherit from this class.

public class Book {

    // Fields common to all catalog entries
    private String type;
    private String title;
    private String author;
    private String authorGender;
    private String publisher;
    private double price;
    private boolean readStatus;

    // Constructor: initializes all fields for a standard book entry
    public Book(String title, String author, String authorGender,
                String publisher, double price, boolean readStatus) {
        this.type = "novel";
        this.title = title;
        this.author = author;
        this.authorGender = authorGender;
        this.publisher = publisher;
        this.price = price;
        this.readStatus = readStatus;
    }

    // Protected constructor used by subclasses to set a different type
    protected Book(String type, String title, String author, String authorGender,
                   String publisher, double price, boolean readStatus) {
        this.type = type;
        this.title = title;
        this.author = author;
        this.authorGender = authorGender;
        this.publisher = publisher;
        this.price = price;
        this.readStatus = readStatus;
    }

    // Prints all fields of this entry in a readable format
    public void display() {
        String status = readStatus ? "Yes" : "No";
        System.out.println("\n  Type       : Novel");
        System.out.println("  Title      : " + title);
        System.out.println("  Author     : " + author + " (" + authorGender + ")");
        System.out.println("  Publisher  : " + publisher);
        System.out.printf ("  Price      : %.2f EUR%n", price);
        System.out.println("  Read       : " + status);
        System.out.println("  " + "-".repeat(40));
    }

    // Sets the read status to true
    public void markAsRead() {
        this.readStatus = true;
    }

    // Updates a field by name. Returns true if the update succeeded.
    public boolean update(String field, String newValue) {
        switch (field) {
            case "title":
                this.title = newValue;
                break;
            case "author":
                this.author = newValue;
                break;
            case "authorGender":
                this.authorGender = newValue;
                break;
            case "publisher":
                this.publisher = newValue;
                break;
            case "price":
                // Price must be a valid number; reject non-numeric input
                try {
                    this.price = Double.parseDouble(newValue);
                } catch (NumberFormatException e) {
                    System.out.println("  Invalid price. Please enter a number.");
                    return false;
                }
                break;
            case "readStatus":
                // Accept common truthy/falsy strings
                this.readStatus = newValue.equalsIgnoreCase("yes")
                               || newValue.equalsIgnoreCase("true")
                               || newValue.equals("1");
                break;
            default:
                System.out.println("  Field '" + field + "' not found.");
                return false;
        }
        System.out.println("  Field '" + field + "' updated successfully.");
        return true;
    }

    // Converts this entry to a pipe-separated string for file storage
    public String toFileLine() {
        return type + "|" + title + "|" + author + "|" + authorGender + "|"
             + publisher + "|" + price + "|" + readStatus;
    }

    // Getters
    public String getType()         { return type; }
    public String getTitle()        { return title; }
    public String getAuthor()       { return author; }
    public String getAuthorGender() { return authorGender; }
    public String getPublisher()    { return publisher; }
    public double getPrice()        { return price; }
    public boolean getReadStatus()  { return readStatus; }

    // Allows subclasses to override the type field
    protected void setType(String type) { this.type = type; }
}
