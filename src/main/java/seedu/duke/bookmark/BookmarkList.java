package seedu.duke.bookmark;

import seedu.duke.book.Book;
import seedu.duke.lists.QuotesifyList;

import java.util.ArrayList;

public class BookmarkList extends QuotesifyList<Bookmark> {
    private ArrayList<Bookmark> bookmarks = super.getList();

    public BookmarkList() {
        super(new ArrayList<Bookmark>());
    }

    public BookmarkList(ArrayList<Bookmark> bookmarks) {
        super(bookmarks);
    }

    @Override
    public void add(Bookmark newBookmark) {
        System.out.println("Adding bookmark to the list...");
        bookmarks.add(newBookmark);
        System.out.println("Finished added");
    }

    public Bookmark findBookmark(Book book) {
        for (int i = 0; i < bookmarks.size(); i++) {
            if(bookmarks.get(i).getBook().equals(book)) {
                return bookmarks.get(i);
            }
        }
        return null;
    }

    @Override
    public void delete(int index) {
        bookmarks.remove(index);
    }

    @Override
    public String toString() {
        String bookmarksToReturn = "";

        for (Bookmark bookmark: bookmarks) {
            bookmarksToReturn += bookmark.toString() + System.lineSeparator();
        }

        return bookmarksToReturn;
    }
}
