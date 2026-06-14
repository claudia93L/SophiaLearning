from Book import Book, Comic

def get_input(prompt, optional=False):
    """Prompt the user for input. If optional, allow empty string."""
    while True:
        value = input(f"  {prompt}: ").strip()
        if value:
            return value
        if optional:
            return ""
        print("  This field cannot be empty. Please try again.")

def get_price():
    """Prompt the user for a valid price."""
    while True:
        value = input("  Price (EUR, optional): ").strip()
        if value == "":
            return 0.0
        try:
            return float(value)
        except ValueError:
            print("  Please enter a valid number.")

def get_gender(label):
    """Prompt the user for a gender value."""
    while True:
        value = input(f"  {label} (male / female / other): ").strip().lower()
        if value in ["male", "female", "other"]:
            return value
        print("  Please enter 'male', 'female', or 'other'.")

def get_read_status():
    """Prompt the user for read status."""
    while True:
        value = input("  Have you read this book? (yes / no): ").strip().lower()
        if value in ["yes", "y"]:
            return True
        if value in ["no", "n"]:
            return False
        print("  Please enter 'yes' or 'no'.")

def collect_novel_data():
    """Collect all fields needed to create a Book object."""
    title = get_input("Title")
    author = get_input("Author")
    author_gender = get_gender("Author gender")
    publisher = get_input("Publisher (optional)", optional=True)
    price = get_price()
    read_status = get_read_status()
    return Book(title, author, author_gender, publisher, price, read_status)

def collect_comic_data():
    """Collect all fields needed to create a Comic object."""
    title = get_input("Title")
    author = get_input("Author / Writer")
    author_gender = get_gender("Author gender")
    illustrator = get_input("Illustrator")
    illustrator_gender = get_gender("Illustrator gender")
    print("  Style options: manga, european, american, belgian, other")
    style = get_input("Style")
    publisher = get_input("Publisher (optional)", optional=True)
    price = get_price()
    read_status = get_read_status()
    return Comic(title, author, author_gender, publisher, price, read_status,
                 illustrator, illustrator_gender, style)