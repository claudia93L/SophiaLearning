// Comic.java
// Subclass of Book representing comics, graphic novels, and manga.
// Adds three fields specific to illustrated works: illustrator,
// illustrator gender, and visual style.

public class Comic extends Book {

    // Fields specific to illustrated works
    private String illustrator;
    private String illustratorGender;
    private String style;

    // Constructor: calls the parent constructor for shared fields,
    // then initializes the Comic-specific fields
    public Comic(String title, String author, String authorGender,
                 String publisher, double price, boolean readStatus,
                 String illustrator, String illustratorGender, String style) {
        // Pass "comic" as the type to the protected Book constructor
        super("comic", title, author, authorGender, publisher, price, readStatus);
        this.illustrator = illustrator;
        this.illustratorGender = illustratorGender;
        this.style = style;
    }

    // Overrides Book.display() to include the Comic-specific fields
    @Override
    public void display() {
        String status = getReadStatus() ? "Yes" : "No";
        System.out.println("\n  Type       : Comic");
        System.out.println("  Title      : " + getTitle());
        System.out.println("  Author     : " + getAuthor() + " (" + getAuthorGender() + ")");
        System.out.println("  Illustrator: " + illustrator + " (" + illustratorGender + ")");
        System.out.println("  Style      : " + style);
        System.out.println("  Publisher  : " + getPublisher());
        System.out.printf ("  Price      : %.2f EUR%n", getPrice());
        System.out.println("  Read       : " + status);
        System.out.println("  " + "-".repeat(40));
    }

    // Updates a Comic-specific field by name
    public boolean updateIllustrator(String field, String newValue) {
        switch (field) {
            case "illustrator":
                this.illustrator = newValue;
                break;
            case "illustratorGender":
                this.illustratorGender = newValue;
                break;
            case "style":
                this.style = newValue;
                break;
            default:
                System.out.println("  Field '" + field + "' is not a valid comic-specific field.");
                return false;
        }
        System.out.println("  Field '" + field + "' updated successfully.");
        return true;
    }

    // Overrides toFileLine() to append the Comic-specific fields
    @Override
    public String toFileLine() {
        return super.toFileLine() + "|" + illustrator + "|" + illustratorGender + "|" + style;
    }

    // Getters for Comic-specific fields
    public String getIllustrator()       { return illustrator; }
    public String getIllustratorGender() { return illustratorGender; }
    public String getStyle()             { return style; }
}
