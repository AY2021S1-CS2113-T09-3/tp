package seedu.duke.ui;

import seedu.duke.book.Book;
import seedu.duke.bookmark.Bookmark;
import seedu.duke.category.Category;
import seedu.duke.quote.QuoteList;

import java.util.Scanner;

public class TextUi {
    private static final String LOGO = "________                __                .__  _____       \n"
            + "\\_____  \\  __ __  _____/  |_  ____   _____|__|/ ____\\__.__.\n"
            + " /  / \\  \\|  |  \\/  _ \\   __\\/ __ \\ /  ___/  \\   __<   |  |\n"
            + "/   \\_/.  \\  |  (  <_> )  | \\  ___/ \\___ \\|  ||  |  \\___  |\n"
            + "\\_____\\ \\_/____/ \\____/|__|  \\___  >____  >__||__|  / ____|\n"
            + "       \\__>                      \\/     \\/          \\/    ";

    private static final String WELCOME_MESSAGE = "Welcome to Quotesify!";
    private static final String GOODBYE_MESSAGE = "Have a nice day!";
    private static final String PROMPT_MESSAGE = "\nWhat would you like to do with Quotesify?";
    private static final String ADD_BOOK = "This book has been added:";
    private static final String ADD_CATEGORY_MESSAGE = "I have tagged \"%s\" category to \"%s\"!";
    private static final String DELETE_CATEGORY_MESSAGE = "I have removed \"%s\" category from \"%s\"!";
    private static final String CATEGORY_SIZE_MESSAGE = "You have a total of %d item(s) tagged as \"%s\".";
    private static final String ADD_BOOKMARK = "This bookmark has been added:";

    private final Scanner in;

    public TextUi() {
        in = new Scanner(System.in);
    }

    public void showWelcomeMessage() {
        System.out.println(LOGO);
        System.out.println(WELCOME_MESSAGE);
    }

    public void showGoodbyeMessage() {
        System.out.println(GOODBYE_MESSAGE);
    }

    public String getUserCommand() {
        System.out.println(PROMPT_MESSAGE);
        return in.nextLine().trim();
    }

    public void printAddBook(Book book) {
        System.out.println(ADD_BOOK);
        System.out.println(book.toString());
    }

    public void printAllQuotes(QuoteList quotes) {
        System.out.println("Here are your quotes:");
        System.out.println(quotes);
    }

    public void printAddCategoryToBook(String bookTitle, String categoryName) {
        System.out.printf((ADD_CATEGORY_MESSAGE) + "\n", categoryName, bookTitle);
    }

    public void printAddCategoryToQuote(String quote, String categoryName) {
        System.out.printf((ADD_CATEGORY_MESSAGE) + "\n", categoryName, quote);
    }

    public void printRemoveCategoryFromBook(String bookTitle, String categoryName) {
        System.out.printf((DELETE_CATEGORY_MESSAGE) + "\n", categoryName, bookTitle);
    }

    public void printRemoveCategoryFromQuote(String quote, String categoryName) {
        System.out.printf((ADD_CATEGORY_MESSAGE) + "\n", categoryName, quote);
    }

    public void printCategorySize(Category category) {
        System.out.printf((CATEGORY_SIZE_MESSAGE) + "\n", category.getSize(), category.getCategoryName());
    }

    public void printBook(Book book) {
        System.out.println(book.toString());
    }

    public void printAddBookmark(Bookmark bookmark){
        System.out.println(ADD_BOOKMARK);
        System.out.println(bookmark.toString());
    }
}