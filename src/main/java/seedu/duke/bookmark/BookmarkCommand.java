package seedu.duke.bookmark;

import seedu.duke.book.Book;
import seedu.duke.book.BookList;
import seedu.duke.commands.Command;
import seedu.duke.lists.ListManager;
import seedu.duke.ui.TextUi;

import java.io.IOException;

public class BookmarkCommand extends Command {
    private String type;
    private String information;
    private Bookmark bookmark;

    public BookmarkCommand(String arguments) {
        String[] details = arguments.split(" ", 2);
        type = details[0];
        information = details[1];
    }

    public void execute(TextUi ui, ListManager listManager) {
        switch (type) {
        case TAG_BOOK:
            BookmarkList bookmarks = (BookmarkList) listManager.getList(ListManager.BOOKMARK_LIST);
            BookList books = (BookList)listManager.getList(ListManager.BOOK_LIST);
            Bookmark newBookmark = addBookmark(bookmarks,books);
            ui.printAddBookmark(newBookmark);
            break;
        default:
            break;
        }
    }

    private Bookmark addBookmark(BookmarkList bookmarks, BookList books) {
        String[] titleAndPageNum = information.split("/pg" , 2);
        String titleName = titleAndPageNum[0].trim();
        int pageNum = 0;
        Bookmark newBookmark = null;
        try {
            pageNum = Integer.parseInt(titleAndPageNum[1].trim());
        }catch (NumberFormatException e) {
            System.out.println("Error: Invalid pageNum!");
        }
        Book targetBook = books.find(titleName);
        newBookmark = bookmarks.findBookmark(targetBook);
        if ( newBookmark == null) {
            newBookmark = new Bookmark(targetBook,pageNum);
            bookmarks.add(newBookmark);
        }
        else {
            newBookmark.setPageNum(pageNum);
        }

        return newBookmark;
    }

}
