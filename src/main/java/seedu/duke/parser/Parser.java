package seedu.duke.parser;

import seedu.duke.bookmark.BookmarkCommand;
import seedu.duke.commands.AddCommand;
import seedu.duke.commands.Command;
import seedu.duke.commands.DeleteCommand;
import seedu.duke.commands.ExitCommand;

public class Parser {
    private static final String COMMAND_ADD = "add";
    private static final String COMMAND_EDIT = "edit";
    private static final String COMMAND_DELETE = "delete";
    private static final String COMMAND_LIST = "list";
    private static final String COMMAND_BOOKMARK = "bookmark";
    private static final String COMMAND_TODO = "todo";
    private static final String COMMAND_BYE = "bye";

    public Command parseUserCommand(String input) {
        String[] tokens = input.split(" ", 2);

        // if user did not provide arguments, let tokens[0] be empty string
        if (tokens.length == 1) {
            tokens = new String[]{tokens[0], ""};
        }

        String command = tokens[0];
        String arguments = tokens[1];

        switch (command) {
        case COMMAND_ADD:
            return new AddCommand(arguments);
        case COMMAND_BOOKMARK:
            return new BookmarkCommand(arguments);
        case COMMAND_DELETE:
            return new DeleteCommand(arguments);
        case COMMAND_EDIT:
        case COMMAND_LIST:
        case COMMAND_TODO:
        case COMMAND_BYE:
            return new ExitCommand();
        default:
            // can consider throwing an exception here for invalid command
        }
        return null;
    }
}
