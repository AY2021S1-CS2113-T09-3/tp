package seedu.quotesify.ui;

public class UiMessage {
    // I have no choice but to make all variables "static", else checkstyleMain will fail.
    // Quotesify messages
    public static final String LOGO = "________                __                .__  _____       \n"
            + "\\_____  \\  __ __  _____/  |_  ____   _____|__|/ ____\\__.__.\n"
            + " /  / \\  \\|  |  \\/  _ \\   __\\/ __ \\ /  ___/  \\   __<   |  |\n"
            + "/   \\_/.  \\  |  (  <_> )  | \\  ___/ \\___ \\|  ||  |  \\___  |\n"
            + "\\_____\\ \\_/____/ \\____/|__|  \\___  >____  >__||__|  / ____|\n"
            + "       \\__>                      \\/     \\/          \\/    ";

    public static final String WELCOME_MESSAGE = "Welcome to Quotesify v1.0!";
    public static final String GOODBYE_MESSAGE = "Alright, have a nice day!";
    public static final String PROMPT_MESSAGE = "\nWhat would you like to do with Quotesify?";
    public static final String INVALID_QUOTESIFY_COMMAND = "I don't understand you." + System.lineSeparator()
            + "Maybe type \"help\" for usage instructions?";

    // Command related messages
    public static final String ADD_BOOK_MESSAGE = "The book [%s] has been added!";
    public static final String DELETE_BOOK_MESSAGE = "The book [%s] has been deleted!";
    public static final String EDIT_BOOK_MESSAGE = "The book has been edited from [%s] to [%s]!";
    public static final String LIST_BOOKS_MESSAGE = "Here is a list of all books:";
    public static final String LIST_BOOK_DETAIL_MESSAGE = "Here are the details of your book:";
    public static final String LIST_DONE_BOOK_MESSAGE = "Here are the books you have completed: ";
    public static final String LIST_UNDONE_BOOK_MESSAGE = "Here are the books you have yet to complete: ";
    public static final String LIST_BOOKS_BY_AUTHOR_MESSAGE = "Here is a list of books by %s:";
    public static final String LIST_BOOKS_BY_KEYWORD_MESSAGE = "Here is a list of books with the keyword \"%s\":";
    public static final String DONE_BOOK_MESSAGE = "The book [%s] has been marked as done!";
    public static final String ADD_CATEGORY_MESSAGE = "I have tagged category [%s] to %s!";
    public static final String DELETE_CATEGORY_MESSAGE = "I have removed category [%s] from %s!";
    public static final String CATEGORY_SIZE_MESSAGE = "You have a total of %d item(s) tagged as [%s].";
    public static final String LIST_CATEGORIES_MESSAGE = "Here is the list of all categories:";
    public static final String LIST_ALL_IN_CATEGORIES_MESSAGE = "Here are the list of items tagged as [%s]:";
    public static final String EMPTY_CATEGORY_LIST_MESSAGE = "There are no categories created!";
    public static final String NO_ITEMS_IN_CATEGORY_MESSAGE = "There are no items tagged as [%s].";
    public static final String EDIT_CATEGORY_MESSAGE = "The category has been changed from [%s] to [%s]!";
    public static final String ADD_RATING_MESSAGE = "You have just rated [%s by %s] %d star!";
    public static final String DELETE_RATING_MESSAGE = "Rating for [%s by %s] has been deleted!";
    public static final String LIST_ALL_RATINGS_MESSAGE = "Planning to recommend some books?"
            + " Here are your rated books!";
    public static final String LIST_NO_RATINGS_FOUND_MESSAGE = "None of the books are rated yet!";
    public static final String LIST_SPECIFIED_RATING_MESSAGE = "Here are the books you rated as %d star!";
    public static final String EDIT_RATING_MESSAGE = "Ratings for [%s by %s] has been updated to %d star!";
    public static final String FIND_RATING_MESSAGE = "Here is your rating for [%s by %s]!";
    public static final String ADD_TODO_MESSAGE = "The task [%s] has been added!";
    public static final String TODO_SIZE_MESSAGE = "You have a total of %d task(s) recorded.";
    public static final String LIST_TODOS_MESSAGE = "Here is the list of all task(s) recorded:";
    public static final String DELETE_TODO_MESSAGE = "The Task [%s] has been deleted!";
    public static final String DONE_TODO_MESSAGE = "The Task [%s] has been marked as done!";
    public static final String EMPTY_TODO_LIST_MESSAGE = "There are no task created";
    public static final String ADD_BOOKMARK_MESSAGE = "The bookmark [%s] has been added!";
    public static final String UPDATE_BOOKMARK_MESSAGE = "The bookmark [%s] has been updated";
    public static final String BOOKMARK_SIZE_MESSAGE = "You have a total of %d bookmark(s) recorded.";
    public static final String LIST_BOOKMARKS_MESSAGE = "Here is the list of all bookmark(s) recorded:";
    public static final String EMPTY_BOOKMARK_LIST_MESSAGE = "There are no bookmark created";
    public static final String DELETE_BOOKMARK_MESSAGE = "The bookmark [%s] has been removed!";
    public static final String EMPTY_BOOKMARK_COMMAND = "There is no info related to bookmark provided!";
    public static final String ADD_QUOTE_MESSAGE = "The quote [%s] has been added!";
    public static final String LIST_ALL_QUOTES = "Here are all your quotes:";
    public static final String DELETE_QUOTE_MESSAGE = "The quote \"%s\" has been deleted!";
    public static final String LIST_NO_QUOTES_SAVED_MESSAGE = "You have no saved quotes!";
    public static final String LIST_NO_QUOTES_FOUND_MESSAGE = "I could not find any that matched your specifications.";
    public static final String LIST_QUOTES_BY_AUTHOR_MESSAGE = "Here is a list of quotes by %s:";
    public static final String LIST_QUOTES_BY_REFERENCE_MESSAGE = "Here is a list of quotes from %s:";
    public static final String LIST_QUOTES_BY_AUTHOR_AND_REFERENCE_MESSAGE = "Here is a list of quotes from %s by %s:";
    public static final String PRINT_RANDOM_QUOTE = "Before you continue, here's something:";
    public static final String EDIT_QUOTE_MESSAGE = "The quote has been edited from: [%s] to [%s]!";
    public static final String FIND_QUOTE_SUCCESS = "Here are the results of my search:";
    public static final String FIND_QUOTE_FAIL = "None of your quotes matches the keyword";
    public static final String ADD_QUOTE_REFLECTION = "Reflection has been to quote: [%s]" + System.lineSeparator()
            + "Reflection: [%s]";
    public static final String LIST_QUOTE_REFLECTION = "Here is the reflection you are looking for!"
            + System.lineSeparator() + "Quote: [%s]" + System.lineSeparator() + "Reflection: [%s]";
    public static final String LIST_QUOTE_NO_REFLECTION = "That quote does not have any reflection attached!";

    // Help page commands
    public static final String ADD_BOOK_COMMAND = "Add book: " + "add -b BOOK_TITLE /by AUTHOR";
    public static final String ADD_BOOKMARK_COMMAND = "Add bookmark: " + "bookmark -b BOOK_TITLE /pg PAGE_NUMBER";
    public static final String ADD_QUOTE_COMMAND = "Add quote: " + "add -q QUOTE [/from BOOK_TITLE] [/by AUTHOR]";
    public static final String ADD_QUOTE_REFLECTION_COMMAND = "Add reflection to quote: "
            + "add -qr QUOTE_NUM /reflect REFLECTION";
    public static final String ADD_CATEGORY_COMMAND = "Add category: "
            + "add -c CATEGORY {[-b BOOK_TITLE] | [-q QUOTE_NUMBER]}";
    public static final String ADD_RATING_COMMAND = "Add rating: " + "add -r RATING_SCORE BOOK_TITLE /by AUTHOR";
    public static final String ADD_TODO_COMMAND = "Add task: " + "add -t TASK /by DEADLINE";

    public static final String DELETE_BOOK_COMMAND = "Delete book: " + "delete -b BOOK_TITLE /by AUTHOR";
    public static final String DELETE_BOOKMARK_COMMAND = "Delete bookmark: " + "delete -bm BOOK_TITLE";
    public static final String DELETE_QUOTE_COMMAND = "Delete quote:" + "delete -q QUOTE_NUMBER";
    public static final String DELETE_CATEGORY_COMMAND = "Delete category: "
            + "delete -c CATEGORY {[-b BOOK_TITLE] | [-q QUOTE_NUMBER]}";
    public static final String DELETE_RATING_COMMAND = "Delete rating: " + "delete -r BOOK_TITLE /by AUTHOR";
    public static final String DELETE_TODO_COMMAND = "Delete task: " + "delete -t TASK_NUMBER";

    public static final String EDIT_BOOK_COMMAND = "";
    public static final String EDIT_BOOKMARK_COMMAND = "Update bookmark: " + "bookmark -b BOOK_TITLE /pg PAGE_NUMBER";
    public static final String EDIT_QUOTE_COMMAND = "";
    public static final String EDIT_CATEGORY_COMMAND = "Edit category: " + "edit -c OLD_CATEGORY NEW_CATEGORY";
    public static final String EDIT_RATING_COMMAND = "Edit rating: " + "edit -r RATING_SCORE BOOK_TITLE /by AUTHOR";
    public static final String EDIT_TODO_COMMAND = "";

    public static final String FIND_BOOK_COMMAND = "";
    public static final String FIND_BOOKMARK_COMMAND = "";
    public static final String FIND_QUOTE_COMMAND = "Find quote: \" + \"find -q QUOTE_NUMBER";
    public static final String FIND_CATEGORY_COMMAND = "";
    public static final String FIND_RATING_COMMAND = "Find rating: " + "find -r BOOK_TITLE /by AUTHOR";
    public static final String FIND_TODO_COMMAND = "";

    public static final String LIST_BOOK_COMMAND = "List books: " + "list -b [/by AUTHOR]";
    public static final String LIST_BOOKMARK_COMMAND = "List bookmarks: " + "list -bm";
    public static final String LIST_QUOTE_COMMAND = "List quotes: " + "list -q [/by AUTHOR] [/from BOOK_TITLE]";
    public static final String LIST_CATEGORY_COMMAND = "List categories: " + "list -c [CATEGORY]";
    public static final String LIST_RATING_COMMAND = "List ratings: " + "list -r [RATING_SCORE]";
    public static final String LIST_TODO_COMMAND = "List tasks: " + "list -t";

    public static final String DONE_COMMAND = "Mark task as done: " + "done -t TASK_NUMBER";
}
