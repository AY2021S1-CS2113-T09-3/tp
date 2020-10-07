package seedu.duke.bookmark;

import seedu.duke.book.Book;

public class DeleteBookmarkCommand {
    private Book book;

    public DeleteBookmarkCommand(Book book) {
        this.book = book;
    }

    public void execute(BookmarkList bookmarks) {
        Bookmark targetBookmark = bookmarks.findBookmark(book);
        if(targetBookmark != null){
            bookmarks.deleteByTitle(book);
        }
        else{
            System.out.println("The bookmark of given title does not exit");
        }
    }
}
