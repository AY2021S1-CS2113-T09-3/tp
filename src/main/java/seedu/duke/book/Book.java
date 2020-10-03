package seedu.duke.book;

import seedu.duke.author.Author;
import seedu.duke.category.Category;

public class Book {
    private Author author;
    private String title;
    private Category category;

    public Book(Author author, String title) {
        this.author = author;
        this.title = title;
    }

    public Book(Author author, String title, Category category) {
        this.author = author;
        this.title = title;
        this.category = category;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
