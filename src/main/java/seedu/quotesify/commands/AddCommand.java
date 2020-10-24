package seedu.quotesify.commands;

import seedu.quotesify.author.Author;
import seedu.quotesify.book.Book;
import seedu.quotesify.book.BookList;
import seedu.quotesify.category.Category;
import seedu.quotesify.category.CategoryList;
import seedu.quotesify.category.CategoryParser;
import seedu.quotesify.exception.QuotesifyException;
import seedu.quotesify.lists.ListManager;
import seedu.quotesify.quote.Quote;
import seedu.quotesify.quote.QuoteList;
import seedu.quotesify.quote.QuoteParser;
import seedu.quotesify.rating.Rating;
import seedu.quotesify.rating.RatingList;
import seedu.quotesify.rating.RatingParser;
import seedu.quotesify.store.Storage;
import seedu.quotesify.todo.ToDo;
import seedu.quotesify.todo.ToDoList;
import seedu.quotesify.ui.TextUi;

import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

import java.util.ArrayList;

public class AddCommand extends Command {
    public static Logger addLogger = Logger.getLogger("QuotesifyLogger");

    private String type;
    private String information;

    public AddCommand(String arguments) {
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
        case TAG_BOOK:
            addLogger.log(Level.INFO, "going to add book to booklist");
            BookList books = (BookList) ListManager.getList(ListManager.BOOK_LIST);
            addBook(books, ui);
            addLogger.log(Level.INFO, "added book to booklist");
            break;
        case TAG_QUOTE:
            addLogger.log(Level.INFO, "going to add quote to quote list");
            QuoteList quotes = (QuoteList) ListManager.getList(ListManager.QUOTE_LIST);
            addQuote(quotes, ui);
            break;
        case TAG_QUOTE_REFLECTION:
            addLogger.log(Level.INFO, "going to add reflection to quote list");
            QuoteList quoteList = (QuoteList) ListManager.getList(ListManager.QUOTE_LIST);
            addQuoteReflection(quoteList, ui);
            break;
        case TAG_CATEGORY:
            addLogger.log(Level.INFO, "going to add category to book/quote");
            CategoryList categories = (CategoryList) ListManager.getList(ListManager.CATEGORY_LIST);
            addCategoryToBookOrQuote(categories, ui);
            break;
        case TAG_RATING:
            addLogger.log(Level.INFO, "going to add rating to book");
            RatingList ratings = (RatingList) ListManager.getList(ListManager.RATING_LIST);
            addRating(ratings, ui);
            addLogger.log(Level.INFO, "rating of book has completed");
            break;
        case TAG_TODO:
            ToDoList toDos = (ToDoList) ListManager.getList(ListManager.TODO_LIST);
            ToDo newToDo = addToDo(toDos, ui);
            ui.printAddToDo(newToDo);
            break;
        default:
            ui.printListOfAddCommands();
            break;
        }
        storage.save();
    }

    private void addQuoteReflection(QuoteList quoteList, TextUi ui) {
        try {
            int quoteNum = QuoteParser.parseQuoteNumber(information, quoteList, Command.FLAG_REFLECT);
            String reflection = QuoteParser.getReflectionToAdd(information);
            if (!reflection.isEmpty()) {
                Quote quoteWithReflection = quoteList.addReflection(reflection, quoteNum);
                ui.printAddReflection(quoteWithReflection, quoteWithReflection.getReflection());
            } else {
                throw new QuotesifyException(ERROR_MISSING_REFLECTION);
            }
        } catch (QuotesifyException e) {
            ui.printErrorMessage(e.getMessage());
            addLogger.log(Level.INFO, "add reflection to quote failed");
        }
    }

    private void addBook(BookList books, TextUi ui) {
        try {
            String[] titleAndAuthor = information.split(Command.FLAG_AUTHOR, 2);
            // if user did not provide author, let titleAndAuthor[1] be empty string
            if (titleAndAuthor.length == 1) {
                titleAndAuthor = new String[]{titleAndAuthor[0], ""};
            }

            String title = titleAndAuthor[0].trim();
            String authorName = titleAndAuthor[1].trim();

            checkMissingInformation(title, authorName);
            Book newBook = createNewBook(books, title, authorName);

            books.add(newBook);
            books.sort();
            ui.printAddBook(newBook);

        } catch (QuotesifyException e) {
            ui.printErrorMessage(e.getMessage());
            addLogger.log(Level.INFO, "add book to booklist failed");
        }
    }

    private Book createNewBook(BookList books, String title, String authorName) throws QuotesifyException {
        Book newBook;
        Author existingAuthor = books.findExistingAuthor(authorName);

        if (existingAuthor == null) {
            // Book is definitely unique
            newBook = new Book(new Author(authorName), title);
        } else {
            books.ensureNoSimilarBooks(title, existingAuthor.getName());
            newBook = new Book(existingAuthor, title);
        }

        return newBook;
    }

    private void checkMissingInformation(String title, String authorName) throws QuotesifyException {
        if (title.isEmpty()) {
            throw new QuotesifyException(ERROR_BOOK_TITLE_MISSING);
        }
        if (authorName.isEmpty()) {
            throw new QuotesifyException(ERROR_NO_AUTHOR_NAME);
        }
    }

    private void addQuote(QuoteList quotes, TextUi ui) {
        try {
            Quote quote = QuoteParser.parseAddParameters(information);
            quotes.add(quote);
            ui.printAddQuote(quote);
            addLogger.log(Level.INFO, "add quote to quote list success");
        } catch (QuotesifyException e) {
            System.out.println(e.getMessage());
            addLogger.log(Level.INFO, "add quote to quote list failed");
            addLogger.log(Level.WARNING, e.getMessage());
        }
    }

    private void addCategoryToBookOrQuote(CategoryList categories, TextUi ui) {
        String[] tokens = information.split(" ");
        String[] parameters = CategoryParser.getRequiredParameters(tokens);
        int result = CategoryParser.validateParametersResult(parameters);
        if (result == 1) {
            executeParameters(categories, parameters, ui);
        } else if (result == 0) {
            ui.printErrorMessage(ERROR_MISSING_BOOK_OR_QUOTE);
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
                addCategoryToList(categoryList, categoryName);
                Category category = categoryList.getCategoryByName(categoryName);

                String bookNum = parameters[1];
                String quoteNum = parameters[2];

                addCategoryToBook(category, bookNum, ui);
                addCategoryToQuote(category, quoteNum, ui);
                categoryList.updateListInCategory(category);
            }
        } catch (QuotesifyException e) {
            addLogger.log(Level.WARNING, e.getMessage());
            ui.printErrorMessage(e.getMessage());
        }
    }

    private void addCategoryToList(CategoryList categories, String categoryName) {
        if (!categories.isExistingCategory(categoryName)) {
            categories.add(new Category(categoryName));
        }
    }

    private void addCategoryToBook(Category category, String bookNum, TextUi ui) {
        // ignore this action if user did not provide book title
        if (bookNum.isEmpty()) {
            return;
        }

        BookList bookList = (BookList) ListManager.getList(ListManager.BOOK_LIST);
        try {
            int bookIndex = Integer.parseInt(bookNum) - 1;
            Book book = bookList.getBook(bookIndex);

            if (book.getCategories().contains(category.getCategoryName())) {
                String errorMessage = String.format(ERROR_CATEGORY_ALREADY_EXISTS,
                        category.getCategoryName(), book.getTitle());
                throw new QuotesifyException(errorMessage);
            }

            book.getCategories().add(category.getCategoryName());
            ui.printAddCategoryToBook(book.getTitle(), category.getCategoryName());
            addLogger.log(Level.INFO, "add category to book success");
        } catch (IndexOutOfBoundsException e) {
            addLogger.log(Level.WARNING, ERROR_NO_BOOK_FOUND);
            ui.printErrorMessage(ERROR_NO_BOOK_FOUND);
        } catch (NumberFormatException e) {
            addLogger.log(Level.WARNING, ERROR_INVALID_BOOK_NUM);
            ui.printErrorMessage(ERROR_INVALID_BOOK_NUM);
        } catch (QuotesifyException e) {
            addLogger.log(Level.WARNING, e.getMessage());
            ui.printErrorMessage(e.getMessage());
        }
    }

    private void addCategoryToQuote(Category category, String index, TextUi ui) {
        // ignore this action if user did not provide quote number
        if (index.isEmpty()) {
            return;
        }

        QuoteList quoteList = (QuoteList) ListManager.getList(ListManager.QUOTE_LIST);
        ArrayList<Quote> quotes = quoteList.getList();
        try {
            int quoteNum = Integer.parseInt(index) - 1;
            Quote quote = quotes.get(quoteNum);

            if (quote.getCategories().contains(category.getCategoryName())) {
                String errorMessage = String.format(ERROR_CATEGORY_ALREADY_EXISTS,
                        category.getCategoryName(), quote.getQuote());
                throw new QuotesifyException(errorMessage);
            }

            quote.getCategories().add(category.getCategoryName());
            ui.printAddCategoryToQuote(quote.getQuote(), category.getCategoryName());
            addLogger.log(Level.INFO, "add category to quote success");
        } catch (IndexOutOfBoundsException e) {
            addLogger.log(Level.WARNING, ERROR_NO_QUOTE_FOUND);
            ui.printErrorMessage(ERROR_NO_QUOTE_FOUND);
        } catch (NumberFormatException e) {
            addLogger.log(Level.WARNING, ERROR_INVALID_QUOTE_NUM);
            ui.printErrorMessage(ERROR_INVALID_QUOTE_NUM);
        } catch (QuotesifyException e) {
            addLogger.log(Level.WARNING, e.getMessage());
            ui.printErrorMessage(e.getMessage());
        }
    }

    private void addRating(RatingList ratings, TextUi ui) {
        if (information.isEmpty()) {
            System.out.println(ERROR_RATING_MISSING_INPUTS);
            return;
        }

        String[] ratingDetails;
        String title;
        String author;
        try {
            ratingDetails = information.split(" ", 2);
            String[] titleAndAuthor = ratingDetails[1].split(Command.FLAG_AUTHOR, 2);
            title = titleAndAuthor[0].trim();
            author = titleAndAuthor[1].trim();
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(RatingParser.ERROR_INVALID_FORMAT_RATING);
            return;
        }

        int ratingScore = RatingParser.checkValidityOfRatingScore(ratingDetails[0]);
        Book bookToRate = checkBookExists(title, author);
        boolean isRated = isRated(bookToRate);
        boolean isValid = (ratingScore != 0) && (bookToRate != null) && (!isRated);
        if (isValid) {
            bookToRate.setRating(ratingScore);
            ratings.add(new Rating(bookToRate, ratingScore));
            ui.printAddRatingToBook(ratingScore, title, author);
        }
    }

    private boolean isRated(Book bookToRate) {
        if (bookToRate != null && bookToRate.getRating() != 0) {
            addLogger.log(Level.INFO, "book has been rated");
            System.out.println(ERROR_RATING_EXIST);
            return true;
        }
        return false;
    }

    private Book checkBookExists(String titleOfBookToRate, String authorOfBookToRate) {
        BookList bookList = (BookList) ListManager.getList(ListManager.BOOK_LIST);
        ArrayList<Book> existingBooks = bookList.getList();
        Book bookToRate = null;
        String author;
        for (Book book : existingBooks) {
            author = book.getAuthor().getName();
            if (book.getTitle().equals(titleOfBookToRate) && author.equals(authorOfBookToRate)) {
                bookToRate = book;
            }
        }
        if (bookToRate == null) {
            addLogger.log(Level.INFO, "book does not exist");
            System.out.println(ERROR_BOOK_TO_RATE_NOT_FOUND);
        }
        return bookToRate;
    }

    private ToDo addToDo(ToDoList toDos, TextUi ui) {
        String[] taskNameAndDeadline = information.split("/by", 2);
        ToDo newToDo = null;

        try {
            // if user did not provide deadline, let titleAndAuthor[1] be "not specified"
            if (taskNameAndDeadline.length == 1) {
                taskNameAndDeadline = new String[]{taskNameAndDeadline[0], "not specified"};
            }
            if (taskNameAndDeadline[0].isEmpty()) {
                throw new QuotesifyException(ERROR_NO_TASK_NAME);
            }

            String taskName = taskNameAndDeadline[0].trim();
            assert !taskName.isEmpty() : "task name should not be empty";
            String deadline = taskNameAndDeadline[1].trim();
            assert !deadline.isEmpty() : "deadline should not be empty";
            newToDo = new ToDo(taskName, deadline);
            newToDo.updateDateFormat();
            toDos.add(newToDo);
        } catch (QuotesifyException e) {
            ui.printErrorMessage(e.getMessage());
            addLogger.log(Level.INFO, "add toDo to toDoList failed");
        }

        return newToDo;
    }


    public boolean isExit() {
        return false;
    }
}
