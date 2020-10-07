package seedu.duke.commands;

import seedu.duke.book.Book;
import seedu.duke.book.BookList;
import seedu.duke.bookmark.Bookmark;
import seedu.duke.bookmark.BookmarkList;
import seedu.duke.category.Category;
import seedu.duke.category.CategoryList;
import seedu.duke.category.CategoryParser;
import seedu.duke.lists.ListManager;
import seedu.duke.quote.Quote;
import seedu.duke.quote.QuoteList;
import seedu.duke.ui.TextUi;

import java.util.ArrayList;

public class DeleteCommand extends Command {
    private String type;
    private String information;

    public DeleteCommand(String arguments) {
        String[] details = arguments.split(" ", 2);
        type = details[0];
        information = details[1];
    }

    @Override
    public void execute(TextUi ui, ListManager listManager) {
        switch (type) {
        case TAG_CATEGORY:
            CategoryList categories = (CategoryList) listManager.getList(ListManager.CATEGORY_LIST);
            deleteCategoryFromBookOrQuote(categories, ui, listManager);
            break;
        case KEY_BOOKMARK:
            BookList books = (BookList)listManager.getList(ListManager.BOOK_LIST);
            BookmarkList bookmarks = (BookmarkList) listManager.getList(ListManager.BOOKMARK_LIST);
            Bookmark removedBookmark = deleteBookmarkFromBook(books,bookmarks);
            ui.printRemoveBookmark(removedBookmark);
            break;
        default:
        }
    }

    private void deleteCategoryFromBookOrQuote(CategoryList categories, TextUi ui, ListManager listManager) {
        String[] tokens = information.split(" ");
        String[] parameters = CategoryParser.getRequiredParameters(tokens);
        executeParameters(categories, parameters, ui, listManager);
    }

    private void executeParameters(CategoryList categories, String[] parameters, TextUi ui, ListManager listManager) {
        try {
            String categoryName = parameters[0];
            String bookTitle = parameters[1];
            int quoteNum = Integer.parseInt(parameters[2]);
            Category category = categories.getCategoryByName(categoryName);

            if (deleteCategoryFromBook(category, bookTitle, listManager)) {
                ui.printRemoveCategoryFromBook(bookTitle, categoryName);
            }

            if (deleteCategoryFromQuote(category, quoteNum, listManager)) {
                QuoteList quoteList = (QuoteList) listManager.getList(ListManager.QUOTE_LIST);
                ArrayList<Quote> quotes = quoteList.getList();
                ui.printRemoveCategoryFromQuote(quotes.get(quoteNum).getQuote(), categoryName);
            }
            ui.printCategorySize(category);
        } catch (NumberFormatException e) {
            System.out.println(ERROR_INVALID_QUOTE_NUM);
        }
    }

    private boolean deleteCategoryFromBook(Category category, String bookTitle, ListManager listManager) {
        if (bookTitle == null || category == null) {
            return false;
        }

        BookList bookList = (BookList) listManager.getList(ListManager.BOOK_LIST);
        ArrayList<Book> books = bookList.getList();
        for (Book book : books) {
            if (book.getTitle().equals(bookTitle)) {
                book.setCategory(null);
                category.getBooks().getList().remove(book);
                return true;
            }
        }
        return false;
    }

    private boolean deleteCategoryFromQuote(Category category, int quoteNum, ListManager listManager) {
        if (quoteNum == -1 || category == null) {
            return false;
        }

        QuoteList quoteList = (QuoteList) listManager.getList(ListManager.QUOTE_LIST);
        ArrayList<Quote> quotes = quoteList.getList();

        try {
            Quote quote = quotes.get(quoteNum);
            quote.setCategory(null);
            category.getQuotes().getList().remove(quote);
        } catch (IndexOutOfBoundsException e) {
            System.out.println(ERROR_INVALID_QUOTE_NUM);
            return false;
        }
        return true;
    }

    private Bookmark deleteBookmarkFromBook(BookList books, BookmarkList bookmarks) {
        String[] tagAndTitle = information.split(" ", 2);
        String tag = tagAndTitle[0].trim();
        String title = tagAndTitle[1].trim();
        Book targetBook = null;
        Bookmark targetBookmark = null;

        if (tag.equals(TAG_BOOK) && title != null) {
            targetBook = books.find(title);
            if (targetBook != null){
                targetBookmark = bookmarks.findBookmark(targetBook);
                if(targetBookmark != null){
                    bookmarks.deleteByTitle(targetBook);
                    return targetBookmark;
                }
            }
        }
        return targetBookmark;
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
