package seedu.duke.book;

import seedu.duke.author.Author;
import seedu.duke.category.Category;
import seedu.duke.lists.QuotesifyList;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class BookList extends QuotesifyList<Book> {
    private ArrayList<Book> books = super.getList();

    public BookList() {
        super(new ArrayList<>());
    }

    public BookList(ArrayList<Book> books) {
        super(books);
    }

    @Override
    public void add(Book newBook) {
        books.add(newBook);
    }

    @Override
    public void delete(int index) {
        books.remove(index);
    }

    public void deleteByBook(Book book) {
        books.remove(book);
    }

    public boolean isEmpty() {
        return books.isEmpty();
    }

    @Override
    public String toString() {
        String booksToReturn = "";

        for (Book book : books) {
            booksToReturn += book.toString() + System.lineSeparator();
        }

        return booksToReturn;
    }

    public ArrayList<Book> find(String title, String author) {
        assert !title.isEmpty() || !author.isEmpty();

        ArrayList<Book> filteredBooks = (ArrayList<Book>) books.stream()
                .filter(book -> {
                    Author bookAuthor = book.getAuthor();
                    return bookAuthor.getName().equals(author) && book.getTitle().equals(title);
                }).collect(Collectors.toList());
        return filteredBooks;
    }

    public Book findByTitle(String title) {
        assert !title.isEmpty();

        for (Book book : books) {
            if (book.getTitle().equals(title)) {
                return book;
            }
        }
        return null;
    }

    public BookList filterByAuthor(String authorName) {
        ArrayList<Book> filteredBooks = (ArrayList<Book>) books.stream()
                .filter(book -> {
                    Author bookAuthor = book.getAuthor();
                    return bookAuthor.getName().equals(authorName);
                }).collect(Collectors.toList());
        return new BookList(filteredBooks);
    }

    public BookList filterByCategory(String categoryName) {
        try {
            ArrayList<Book> filteredBooks = (ArrayList<Book>) books.stream()
                    .filter(book -> {
                        Category category = book.getCategory();
                        return category.getCategoryName().equals(categoryName);
                    }).collect(Collectors.toList());
            return new BookList(filteredBooks);
        } catch (NullPointerException e) {
            // Do nothing
        }
        return new BookList();
    }
}
