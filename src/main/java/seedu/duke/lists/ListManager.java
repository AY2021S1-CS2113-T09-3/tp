package seedu.duke.lists;

import java.util.HashMap;

public class ListManager {
    public static final int BOOK_LIST = 0;
    public static final int BOOKMARK_LIST = 1;
    public static final int CATEGORY_LIST = 2;
    public static final int QUOTE_LIST = 3;
    public static final int RATING_LIST = 4;
    public static final int TODO_LIST = 5;

    private HashMap<Integer, QuotesifyList<?>> listManager;

    public ListManager() {
        listManager = new HashMap<>();
    }

    public void addToList(int type, QuotesifyList<?> list) {
        listManager.put(type, list);
    }

    public QuotesifyList<?> getList(int type) {
        return listManager.get(type);
    }
}
