package seedu.duke.book;

import seedu.duke.lists.QuotesifyList;

import java.util.ArrayList;

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

    @Override
    public String toString() {
        String booksToReturn = "";

        for (Book book : books) {
            booksToReturn += book.toString() + System.lineSeparator();
        }

        return booksToReturn;
    }

    //return book objects with matching titles.
    public Book find(String titleName) {
        for (Book book : books) {
            if(titleName.equals(book.getTitle())){
                return book;
            }
        }
        return null;
    }
}
