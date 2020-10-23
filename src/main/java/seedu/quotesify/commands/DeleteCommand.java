package seedu.quotesify.commands;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import seedu.quotesify.author.Author;
import seedu.quotesify.book.Book;
import seedu.quotesify.book.BookList;
import seedu.quotesify.bookmark.Bookmark;
import seedu.quotesify.bookmark.BookmarkList;
import seedu.quotesify.category.Category;
import seedu.quotesify.category.CategoryList;
import seedu.quotesify.category.CategoryParser;
import seedu.quotesify.exception.QuotesifyException;
import seedu.quotesify.lists.ListManager;
import seedu.quotesify.lists.QuotesifyList;
import seedu.quotesify.parser.JsonSerializer;
import seedu.quotesify.quote.Quote;
import seedu.quotesify.quote.QuoteList;
import seedu.quotesify.rating.Rating;
import seedu.quotesify.rating.RatingList;
import seedu.quotesify.rating.RatingParser;
import seedu.quotesify.store.Storage;
import seedu.quotesify.todo.ToDo;
import seedu.quotesify.todo.ToDoList;
import seedu.quotesify.ui.TextUi;
import seedu.quotesify.ui.UiMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class DeleteCommand extends Command {
    private String type;
    private String information;

    public DeleteCommand(String arguments) {
        String[] details = arguments.split(" ", 2);

        // if user did not provide arguments, let details[1] be empty string
        if (details.length == 1) {
            details = new String[]{details[0], ""};
        }
        type = details[0];
        information = details[1];
    }

    @Override
    public void execute(TextUi ui, Storage storage) {
        switch (type) {
        case TAG_CATEGORY:
            CategoryList categories = (CategoryList) ListManager.getList(ListManager.CATEGORY_LIST);
            deleteCategoryFromBookOrQuote(categories, ui);
            break;
        case TAG_BOOK:
            BookList books = (BookList) ListManager.getList(ListManager.BOOK_LIST);
            deleteBook(books, ui);
            break;
        case TAG_RATING:
            RatingList ratings = (RatingList) ListManager.getList(ListManager.RATING_LIST);
            deleteRating(ratings, ui);
            break;
        case TAG_TODO:
            ToDoList toDos = (ToDoList) ListManager.getList(ListManager.TODO_LIST);
            int index = computeToDoIndex(information.trim());
            deleteToDo(toDos, index, ui);
            break;
        case TAG_BOOKMARK:
            BookList bookList = (BookList) ListManager.getList(ListManager.BOOK_LIST);
            BookmarkList bookmarks = (BookmarkList) ListManager.getList(ListManager.BOOKMARK_LIST);
            deleteBookmarkByIndex(bookmarks,information.trim(),ui);
            break;
        case TAG_QUOTE:
            QuoteList quotes = (QuoteList) ListManager.getList(ListManager.QUOTE_LIST);
            deleteQuote(quotes, ui, information);
            break;
        case TAG_QUOTE_REFLECTION:
            QuoteList quoteList = (QuoteList) ListManager.getList(ListManager.QUOTE_LIST);
            deleteQuoteReflection(quoteList, ui, information);
            break;
        default:
            ui.printListOfDeleteCommands();
            break;
        }
        storage.save();
    }

    private void deleteQuoteReflection(QuoteList quoteList, TextUi ui, String information) {
        try {
            int quoteNumber = Integer.parseInt(information.trim()) - 1;
            quoteList.deleteReflection(quoteNumber);
            ui.printDeleteQuoteReflection(quoteList.getQuote(quoteNumber).getQuote());
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            System.out.println(ERROR_INVALID_QUOTE_NUM);
        }
    }

    private void deleteQuote(QuoteList quotes, TextUi ui, String information) {
        try {
            int quoteNumber = Integer.parseInt(information.trim()) - 1;
            Quote quoteToBeDeleted = quotes.getQuote(quoteNumber);
            quotes.delete(quoteNumber);
            ui.printDeleteQuote(quoteToBeDeleted.getQuote());
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            System.out.println(ERROR_INVALID_QUOTE_NUM);
        }
    }

    private void deleteRating(RatingList ratings, TextUi ui) {
        if (information.isEmpty()) {
            System.out.println(ERROR_RATING_MISSING_INPUTS);
            return;
        }

        String[] titleAndAuthor;
        String title;
        String author;
        try {
            titleAndAuthor = information.split(Command.FLAG_AUTHOR, 2);
            title = titleAndAuthor[0].trim();
            author = titleAndAuthor[1].trim();
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(RatingParser.ERROR_INVALID_FORMAT_RATING);
            return;
        }

        Rating ratingToBeDeleted = null;
        for (Rating rating : ratings.getList()) {
            if (rating.getTitleOfRatedBook().equals(title)
                    && rating.getAuthorOfRatedBook().equals(author)) {
                ratingToBeDeleted = rating;
                break;
            }
        }

        if (ratingToBeDeleted == null) {
            System.out.println(ERROR_RATING_NOT_FOUND);
            return;
        }
        ratingToBeDeleted.getRatedBook().setRating(0);
        ratings.delete(ratings.getList().indexOf(ratingToBeDeleted));
        ui.printDeleteRating(title, author);
    }

    private void deleteBook(BookList books, TextUi ui) {
        try {
            int bookIndex = Integer.parseInt(information.trim()) - 1;
            Book book = books.getBook(bookIndex);
            String bookTitle = book.getTitle();
            String author = book.getAuthor().getName();

            // clear bookmarks before deleting the entire book.
            BookmarkList bookmarks = (BookmarkList) ListManager.getList(ListManager.BOOKMARK_LIST);
            clearBookmark(books, bookmarks, bookTitle, ui);

            // delete ratings before deleting the entire book.
            RatingList ratings = (RatingList) ListManager.getList(ListManager.RATING_LIST);
            for (Rating rating : ratings.getList()) {
                if (rating.getTitleOfRatedBook().equals(bookTitle)
                        && rating.getAuthorOfRatedBook().equals(author)) {
                    ratings.delete(ratings.getList().indexOf(rating));
                    break;
                }
            }

            books.delete(bookIndex);
            ui.printDeleteBook(book);

        } catch (IndexOutOfBoundsException e) {
            ui.printErrorMessage(ERROR_NO_BOOK_FOUND);
        } catch (NumberFormatException e) {
            ui.printErrorMessage(ERROR_INVALID_BOOK_NUM);
        }
    }

    private void deleteCategoryFromBookOrQuote(CategoryList categories, TextUi ui) {
        String[] tokens = information.split(" ");
        String[] parameters = CategoryParser.getRequiredParameters(tokens);
        int result = CategoryParser.validateParametersResult(parameters);
        if (result == 1) {
            executeParameters(categories, parameters, ui);
        } else if (result == 0) {
            deleteCategory(categories, parameters[0], ui);
        } else {
            ui.printErrorMessage(ERROR_MISSING_CATEGORY);
        }
    }

    private void executeParameters(CategoryList categoryList, String[] parameters, TextUi ui) {
        try {
            String categoryNames = parameters[0];
            assert !categoryNames.isEmpty() : "category name should not be empty";

            List<String> categories = CategoryParser.parseCategoriesToList(categoryNames);
            for (String categoryName : categories) {
                Category category = categoryList.getCategoryByName(categoryName);

                String bookTitle = parameters[1];
                String quoteNum = parameters[2];

                deleteCategoryFromBook(category, bookTitle, ui);
                deleteCategoryFromQuote(category, quoteNum, ui);
                categoryList.updateListInCategory(category);
            }
        } catch (QuotesifyException e) {
            ui.printErrorMessage(e.getMessage());
        }
    }

    private void deleteCategoryFromBook(Category category, String bookTitle, TextUi ui) {
        // ignore this action if user did not provide book title
        if (bookTitle.isEmpty()) {
            return;
        }

        BookList bookList = category.getBookList();
        try {
            int bookNum = Integer.parseInt(bookTitle) - 1;
            Book book = bookList.getBook(bookNum);
            ArrayList<String> categories = book.getCategories();
            categories.remove(category.getCategoryName());
            ui.printRemoveCategoryFromBook(book.getTitle(), category.getCategoryName());
        } catch (IndexOutOfBoundsException e) {
            ui.printErrorMessage(ERROR_NO_BOOK_FOUND + "\b tagged as [" + category.getCategoryName() + "]!");
        } catch (NumberFormatException e) {
            ui.printErrorMessage(ERROR_INVALID_BOOK_NUM);
        }
    }

    private void deleteCategoryFromQuote(Category category, String index, TextUi ui) {
        // ignore this action if user did not provide quote number
        if (index.isEmpty()) {
            return;
        }

        QuoteList quoteList = category.getQuoteList();
        ArrayList<Quote> quotes = quoteList.getList();
        try {
            int quoteNum = Integer.parseInt(index) - 1;
            Quote quote = quotes.get(quoteNum);
            ArrayList<String> categories = quote.getCategories();
            categories.remove(category.getCategoryName());
            ui.printRemoveCategoryFromQuote(quote.getQuote(), category.getCategoryName());
        } catch (IndexOutOfBoundsException e) {
            ui.printErrorMessage(ERROR_NO_QUOTE_FOUND + "\b tagged as [" + category.getCategoryName() + "]!");
        } catch (NumberFormatException e) {
            ui.printErrorMessage(ERROR_INVALID_QUOTE_NUM);
        }
    }

    private void deleteCategory(CategoryList categoryList, String categories, TextUi ui) {
        for (String name : categories.split(" ")) {
            try {
                Category category = categoryList.getCategoryByName(name);
                categoryList.deleteCategoryInBooksAndQuotes(name);
                categoryList.getList().remove(category);
                ui.printRemoveCategory(name);
            } catch (QuotesifyException e) {
                ui.printErrorMessage(e.getMessage());
            }
        }
    }

    private void deleteToDo(ToDoList toDos, int index, TextUi ui) {
        ToDo toDoToBeDeleted = toDos.find(index);
        if (toDoToBeDeleted != null) {
            toDos.delete(index);
            ui.printDeleteToDo(toDoToBeDeleted);
        } else {
            ui.printErrorMessage(ERROR_TODO_NOT_FOUND);
        }
    }

    private int computeToDoIndex(String information) {
        int index = 0;
        try {
            index = Integer.parseInt(information);
        } catch (NumberFormatException e) {
            System.out.println(ERROR_INVALID_TODO_NUM);
        }

        return index;
    }

    private void deleteBookmark(BookList books, BookmarkList bookmarks, String titleName, TextUi ui) {
        Book targetBook = books.findByTitle(titleName);
        if (targetBook != null) {
            removeBookmarkFromBook(targetBook, bookmarks, ui);
        } else {
            ui.printErrorMessage(ERROR_NO_BOOK_FOUND);
        }
    }

    private void deleteBookmarkByIndex(BookmarkList bookmarks, String index, TextUi ui) {
        int indexNum = convertBookmarkIndexToInt(index, ui);
        if (indexNum <= bookmarks.getSize()) {
            Bookmark targetBookmark = bookmarks.findByIndex(indexNum - 1);
            bookmarks.delete(indexNum);
            ui.printDeleteBookmark(targetBookmark);
        } else {
            ui.printErrorMessage(ERROR_NO_BOOK_FOUND);
        }
    }

    private void removeBookmarkFromBook(Book targetBook, BookmarkList bookmarks, TextUi ui) {
        Bookmark bookmarkToBeDeleted = bookmarks.find(targetBook);

        if (bookmarkToBeDeleted != null) {
            bookmarks.delete(bookmarkToBeDeleted);
            ui.printDeleteBookmark(bookmarkToBeDeleted);
        } else {
            ui.printErrorMessage(ERROR_BOOKMARK_NOT_FOUND);
        }
    }

    private void clearBookmark(BookList books, BookmarkList bookmarks, String titleName, TextUi ui) {
        Book targetBook = books.findByTitle(titleName);
        if (targetBook != null) {
            clearBookmarkFromDeletedBook(targetBook, bookmarks, ui);
        }
    }

    private void clearBookmarkFromDeletedBook(Book targetBook, BookmarkList bookmarks, TextUi ui) {
        Bookmark bookmarkToBeDeleted = bookmarks.find(targetBook);

        if (bookmarkToBeDeleted != null) {
            bookmarks.delete(bookmarkToBeDeleted);
        }
    }

    private int convertBookmarkIndexToInt(String indexString, TextUi ui) {
        int index = -1;
        try {
            index = Integer.parseInt(information) - 1;
        } catch (NumberFormatException e) {
            ui.printErrorMessage(ERROR_INVALID_BOOKMARK_NUM);
        }

        return index;
    }

    @Override
    public boolean isExit() {
        return false;
    }
}