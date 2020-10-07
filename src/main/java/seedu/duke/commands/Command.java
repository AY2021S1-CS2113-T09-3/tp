package seedu.duke.commands;

import seedu.duke.lists.ListManager;
import seedu.duke.ui.TextUi;

public abstract class Command {
    public static final int RATING_ONE = 1;
    public static final int RATING_FIVE = 5;
    public static final String TAG_BOOK = "-b";
    public static final String TAG_QUOTE = "-q";
    public static final String TAG_RATING = "-r";
    public static final String TAG_CATEGORY = "-c";
    public static final String KEY_BOOKMARK = "bookmark";

    public static final String ERROR_INVALID_QUOTE_NUM = "Invalid quote number specified!";

    public abstract void execute(TextUi ui, ListManager listManager);

    public abstract boolean isExit();
}
