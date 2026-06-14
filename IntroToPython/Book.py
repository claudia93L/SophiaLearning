# Book.py 

# This file defines the Book base class and the Comic subclass.
# Book represents a novel or any general book.
# Comic inherits from Book and adds fields specific to illustrated works,
# such as illustrator, illustrator gender, and visual style.


class Book:
    """Base class representing a novel or general book."""

    def __init__(self, title, author, author_gender, publisher, price, read_status):
        self.type = "novel"
        self.title = title
        self.author = author
        self.author_gender = author_gender
        self.publisher = publisher
        self.price = price
        self.read_status = read_status

    def display(self):
        """Print all book attributes in a readable format."""
        status = "Yes" if self.read_status else "No"
        print(f"\n  Type       : Novel")
        print(f"  Title      : {self.title}")
        print(f"  Author     : {self.author} ({self.author_gender})")
        print(f"  Publisher  : {self.publisher}")
        print(f"  Price      : {self.price} EUR")
        print(f"  Read       : {status}")
        print("  " + "-" * 40)

    def mark_as_read(self):
        """Set the read status to True."""
        self.read_status = True

    def update(self, field, new_value):
        """Update a field if it exists on this object."""
        valid_fields = ["title", "author", "author_gender", "publisher", "price", "read_status"]
        if field in valid_fields:
            if field == "price":
                try:
                    new_value = float(new_value)
                except ValueError:
                    print("  Invalid price. Please enter a number.")
                    return
            if field == "read_status":
                new_value = new_value.lower() in ["yes", "true", "1"]
            setattr(self, field, new_value)
            print(f"  Field '{field}' updated successfully.")
        else:
            print(f"  Field '{field}' not found.")

    def to_dict(self):
        """Convert the object to a dictionary for JSON serialization."""
        return {
            "type": self.type,
            "title": self.title,
            "author": self.author,
            "author_gender": self.author_gender,
            "publisher": self.publisher,
            "price": self.price,
            "read_status": self.read_status
        }


class Comic(Book):
    """Subclass representing a comic, graphic novel, or manga."""

    def __init__(self, title, author, author_gender, publisher, price, read_status,
                 illustrator, illustrator_gender, style):
        super().__init__(title, author, author_gender, publisher, price, read_status)
        self.type = "comic"
        self.illustrator = illustrator
        self.illustrator_gender = illustrator_gender
        self.style = style

    def display(self):
        """Print all comic attributes, including illustrator and style."""
        status = "Yes" if self.read_status else "No"
        print(f"\n  Type       : Comic")
        print(f"  Title      : {self.title}")
        print(f"  Author     : {self.author} ({self.author_gender})")
        print(f"  Illustrator: {self.illustrator} ({self.illustrator_gender})")
        print(f"  Style      : {self.style}")
        print(f"  Publisher  : {self.publisher}")
        print(f"  Price      : {self.price} EUR")
        print(f"  Read       : {status}")
        print("  " + "-" * 40)

    def update_illustrator(self, field, new_value):
        """Update a Comic-specific field."""
        valid_fields = ["illustrator", "illustrator_gender", "style"]
        if field in valid_fields:
            setattr(self, field, new_value)
            print(f"  Field '{field}' updated successfully.")
        else:
            print(f"  Field '{field}' is not a valid comic-specific field.")

    def to_dict(self):
        """Convert the Comic object to a dictionary for JSON serialization."""
        data = super().to_dict()
        data["illustrator"] = self.illustrator
        data["illustrator_gender"] = self.illustrator_gender
        data["style"] = self.style
        return data
