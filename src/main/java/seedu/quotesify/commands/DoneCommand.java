package seedu.quotesify.commands;

import seedu.quotesify.book.Book;
import seedu.quotesify.book.BookList;
import seedu.quotesify.lists.ListManager;
import seedu.quotesify.store.Storage;
import seedu.quotesify.todo.ToDo;
import seedu.quotesify.todo.ToDoList;
import seedu.quotesify.ui.TextUi;

public class DoneCommand extends Command {
    private String type;
    private String information;

    public DoneCommand(String arguments) {
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
        case TAG_TODO:
            ToDoList toDos = (ToDoList) ListManager.getList(ListManager.TODO_LIST);
            int index = computeToDoIndex(information.trim());
            doneToDo(toDos,index,ui);
            break;
        case TAG_BOOK:
            BookList books = (BookList) ListManager.getList(ListManager.BOOK_LIST);
            markBookAsDone(books,ui);
            break;
        default:
            ui.printDoneCommandUsage();
            break;
        }
        storage.save();
    }

    private void markBookAsDone(BookList books, TextUi ui) {
        try {
            int bookIndex = Integer.parseInt(information.trim()) - 1;
            Book book = books.getBook(bookIndex);
            book.setDone(true);
            ui.printDoneBook(book);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            ui.printErrorMessage(ERROR_INVALID_BOOK_NUM);
        }
    }

    private void doneToDo(ToDoList toDos, int index, TextUi ui) {
        ToDo targetTask = toDos.find(index);
        if (targetTask != null) {
            targetTask.setDone(true);
            ui.printDoneToDo(targetTask);
        } else {
            System.out.println(ERROR_TODO_NOT_FOUND);
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

    @Override
    public boolean isExit() {
        return false;
    }
}
