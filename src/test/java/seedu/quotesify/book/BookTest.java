package seedu.quotesify.book;

import org.junit.jupiter.api.Test;
import seedu.quotesify.author.Author;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BookTest {
    @Test
    public void toString_titleAuthor_titleByAuthor() {
        Author author = new Author("author");
        Book book = new Book(author, "title");
        assertEquals("title by author", book.toString());
    }
}