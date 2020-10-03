package seedu.duke.quote;

import seedu.duke.author.Author;
import seedu.duke.category.Category;

public class Quote {
    private Author author;
    private String quote;
    private Category category;

    public Quote(Author author, String quote) {
        this.author = author;
        this.quote = quote;
    }

    public Quote(Author author, String quote, Category category) {
        this.author = author;
        this.quote = quote;
        this.category = category;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
