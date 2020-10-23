package seedu.quotesify.book;

import org.json.simple.JSONArray;
import seedu.quotesify.author.Author;
import seedu.quotesify.commands.Command;
import seedu.quotesify.exception.QuotesifyException;
import seedu.quotesify.lists.QuotesifyList;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class BookList extends QuotesifyList<Book> {
    private ArrayList<Book> books = super.getList();
    private BookTitleComparator comparator = new BookTitleComparator();

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

    public int getIndex(Book book) {
        return books.indexOf(book);
    }

    public Book getBook(int index) {
        return books.get(index);
    }

    public void sort() {
        books.sort(comparator);
    }

    @Override
    public String toString() {
        String booksToReturn = "";

        for (Book book : books) {
            booksToReturn += book.toString() + System.lineSeparator();
        }

        return booksToReturn;
    }

    public String toStringWithIndex() {
        String booksToReturn = "";

        for (Book book : books) {
            booksToReturn += getIndex(book) + 1 + ". " + book.getStatusIcon()
                    + book.toString() + System.lineSeparator();
        }

        return booksToReturn;
    }

    public void ensureNoSimilarBooks(String title, String authorName) throws QuotesifyException {
        ArrayList<Book> similarBooks = find(title, authorName);

        if (!similarBooks.isEmpty()) {
            throw new QuotesifyException(Command.ERROR_BOOK_ALREADY_EXISTS);
        }
    }

    public ArrayList<Book> find(String title, String authorName) {
        assert !title.isEmpty() || !authorName.isEmpty();
        String lowerCaseTitle = title.toLowerCase();
        String lowerCaseAuthor = authorName.toLowerCase();

        ArrayList<Book> filteredBooks = (ArrayList<Book>) books.stream()
                .filter(book -> {
                    Author bookAuthor = book.getAuthor();
                    String bookAuthorName = bookAuthor.getName();
                    String bookTitle = book.getTitle();
                    return bookAuthorName.toLowerCase().equals(lowerCaseAuthor)
                            && bookTitle.toLowerCase().equals(lowerCaseTitle);
                }).collect(Collectors.toList());

        return filteredBooks;
    }

    public BookList findByKeyword(String keyword) {
        assert !keyword.isEmpty();
        String lowerCaseKeyword = keyword.toLowerCase();

        ArrayList<Book> filteredBooks = (ArrayList<Book>) books.stream()
                .filter(book -> {
                    String authorName = book.getAuthor().getName();
                    String bookTitle = book.getTitle();
                    return authorName.toLowerCase().contains(lowerCaseKeyword)
                            || bookTitle.toLowerCase().contains(lowerCaseKeyword);
                }).collect(Collectors.toList());

        return new BookList(filteredBooks);
    }

    public Book findByTitle(String title) {
        assert !title.isEmpty();
        String lowerCaseTitle = title.toLowerCase();

        for (Book book : books) {
            if (book.getTitle().toLowerCase().equals(lowerCaseTitle)) {
                return book;
            }
        }
        return null;
    }

    public Book findByNum(int num) {
        int index = num - 1;
        if (num <= books.size()) {
            return books.get(index);
        } else {
            return null;
        }
    }

    public Author findExistingAuthor(String authorName) {
        BookList filteredBooks = filterByAuthor(authorName);

        if (filteredBooks.isEmpty()) {
            return null;
        }
        Author author = filteredBooks.getBook(0).getAuthor();

        return author;
    }

    public BookList filterDone(boolean isDone) {
        ArrayList<Book> filteredBooks;

        if (isDone) {
            filteredBooks = (ArrayList<Book>) books.stream()
                    .filter(book -> book.isDone())
                    .collect(Collectors.toList());
        } else {
            filteredBooks = (ArrayList<Book>) books.stream()
                    .filter(book -> !book.isDone())
                    .collect(Collectors.toList());
        }

        return new BookList(filteredBooks);
    }

    public BookList filterByAuthor(String authorName) {
        String lowerCaseAuthor = authorName.toLowerCase();

        ArrayList<Book> filteredBooks = (ArrayList<Book>) books.stream()
                .filter(book -> {
                    Author bookAuthor = book.getAuthor();
                    String bookAuthorName = bookAuthor.getName();
                    return bookAuthorName.toLowerCase().equals(lowerCaseAuthor);
                }).collect(Collectors.toList());

        return new BookList(filteredBooks);
    }

    public BookList filterByCategory(String categoryName) {
        ArrayList<Book> filteredBooks = (ArrayList<Book>) books.stream()
                .filter(book -> {
                    ArrayList<String> categories = book.getCategories();
                    return categories.contains(categoryName);
                }).collect(Collectors.toList());
        return new BookList(filteredBooks);
    }

    @Override
    public JSONArray toJsonArray() {
        JSONArray list = new JSONArray();
        for (Book book : books) {
            list.add(book.toJson());
        }
        return list;
    }
}
