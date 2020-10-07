package seedu.duke.bookmark;

import seedu.duke.book.Book;

public class BookmarkCommand {
    private Bookmark bookmark;

    public BookmarkCommand(Book book, int pageNum) {
        bookmark = new Bookmark(book, pageNum);
    }

    public void execute(BookmarkList bookmarks) {
        Bookmark targetBookmark = bookmarks.findBookmark(bookmark.getBook());
        if(targetBookmark == null) {
            bookmarks.add(bookmark);
        }
        else {
            targetBookmark.setPageNum(bookmark.getPageNum());
        }
    }

}
