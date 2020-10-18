package seedu.quotesify.store;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import seedu.quotesify.author.Author;
import seedu.quotesify.book.Book;
import seedu.quotesify.book.BookList;
import seedu.quotesify.bookmark.Bookmark;
import seedu.quotesify.bookmark.BookmarkList;
import seedu.quotesify.category.Category;
import seedu.quotesify.category.CategoryList;
import seedu.quotesify.lists.ListManager;
import seedu.quotesify.quote.Quote;
import seedu.quotesify.quote.QuoteList;
import seedu.quotesify.rating.Rating;
import seedu.quotesify.rating.RatingList;
import seedu.quotesify.todo.ToDo;
import seedu.quotesify.todo.ToDoList;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Storage {
    private static final String CURRENT_DIR = System.getProperty("user.dir");
    private static final String SEPARATOR = File.separator;

    private static final String BOOKS = "books";
    private static final String QUOTES = "quotes";
    private static final String CATEGORIES = "categories";
    private static final String RATINGS = "ratings";
    private static final String BOOKMARKS = "bookmarks";
    private static final String TODOS = "todos";
    private static final String DATA_CORRUPT_MESSAGE = "[%s] is corrupted in save file. Creating an empty list.\n";

    private static File saveFile;

    public static void initialiseSaveFile(String filePath) {
        try {
            filePath = CURRENT_DIR + filePath.replace("/", SEPARATOR).replace("\\", SEPARATOR);
            saveFile = new File(filePath);

            if (!saveFile.getParentFile().exists()) {
                saveFile.getParentFile().mkdirs();
            }

            if (!saveFile.exists()) {
                saveFile.createNewFile();
            }
        } catch (IOException e) {
            System.out.println(filePath + " creation failed.");
        }
    }

    public static void save() {
        try {
            JSONObject json = new JSONObject();
            json.put("books", ((BookList) ListManager.getList(ListManager.BOOK_LIST)).toJsonArray());
            json.put("quotes", ((QuoteList) ListManager.getList(ListManager.QUOTE_LIST)).toJsonArray());
            json.put("categories", ((CategoryList) ListManager.getList(ListManager.CATEGORY_LIST)).toJsonArray());
            json.put("bookmarks", ((BookmarkList) ListManager.getList(ListManager.BOOKMARK_LIST)).toJsonArray());
            json.put("ratings", ((RatingList) ListManager.getList(ListManager.RATING_LIST)).toJsonArray());
            json.put("todos", ((ToDoList) ListManager.getList(ListManager.TODO_LIST)).toJsonArray());

            FileWriter fileWriter = new FileWriter(saveFile);
            fileWriter.write(json.toJSONString());
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Saving to file failed!");
            e.printStackTrace();
        }
    }

    public static void load() {
        try {
            FileReader fileReader = new FileReader(saveFile);
            JSONParser jsonParser = new JSONParser();
            JSONObject json = (JSONObject) jsonParser.parse(fileReader);
            updateListManager(json);
            fileReader.close();
        } catch (ParseException | IOException e) {
            // e.printStackTrace();
        }
    }

    private static void updateListManager(JSONObject json) {
        try {
            ListManager.addToList(ListManager.BOOK_LIST, parseToBookList(json));
            ListManager.addToList(ListManager.QUOTE_LIST, parseToQuoteList(json));
            ListManager.addToList(ListManager.CATEGORY_LIST, parseToCategoryList(json));
            ListManager.addToList(ListManager.RATING_LIST, parseToRatingList(json));
            ListManager.addToList(ListManager.BOOKMARK_LIST, parseToBookmarkList(json));
            ListManager.addToList(ListManager.TODO_LIST, parseToTodoList(json));
        } catch (NullPointerException e) {
            // e.printStackTrace();
        }
    }

    private static BookList parseToBookList(JSONObject json) {
        try {
            JSONArray books = (JSONArray) json.get(BOOKS);
            ArrayList<Book> bookList = new ArrayList<>();
            for (Object book : books) {
                bookList.add(parseToBook((JSONObject) book));
            }
            return new BookList(bookList);
        } catch (NullPointerException e) {
            System.out.printf(DATA_CORRUPT_MESSAGE, BOOKS);
        }
        return new BookList();
    }

    private static QuoteList parseToQuoteList(JSONObject json) {
        try {
            JSONArray quotes = (JSONArray) json.get(QUOTES);
            ArrayList<Quote> quoteList = new ArrayList<>();
            for (Object quote : quotes) {
                quoteList.add(parseToQuote((JSONObject) quote));
            }
            return new QuoteList(quoteList);
        } catch (NullPointerException e) {
            System.out.printf(DATA_CORRUPT_MESSAGE, QUOTES);
        }
        return new QuoteList();
    }

    private static CategoryList parseToCategoryList(JSONObject json) {
        try {
            JSONArray categories = (JSONArray) json.get(CATEGORIES);
            ArrayList<Category> categoryList = new ArrayList<>();
            for (Object category : categories) {
                categoryList.add(parseToCategory((JSONObject) category));
            }
            return new CategoryList(categoryList);
        } catch (NullPointerException e) {
            System.out.printf(DATA_CORRUPT_MESSAGE, CATEGORIES);
        }
        return new CategoryList();
    }

    private static RatingList parseToRatingList(JSONObject json) {
        try {
            JSONArray ratings = (JSONArray) json.get(RATINGS);
            ArrayList<Rating> ratingList = new ArrayList<>();
            for (Object rating : ratings) {
                ratingList.add(parseToRating((JSONObject) rating));
            }
            return new RatingList(ratingList);
        } catch (NullPointerException e) {
            System.out.printf(DATA_CORRUPT_MESSAGE, RATINGS);
        }
        return new RatingList();
    }

    private static BookmarkList parseToBookmarkList(JSONObject json) {
        try {
            JSONArray bookmarks = (JSONArray) json.get(BOOKMARKS);
            ArrayList<Bookmark> bookmarkList = new ArrayList<>();
            for (Object bookmark : bookmarks) {
                bookmarkList.add(parseToBookmark((JSONObject) bookmark));
            }
            return new BookmarkList(bookmarkList);
        } catch (NullPointerException e) {
            System.out.printf(DATA_CORRUPT_MESSAGE, BOOKMARKS);
        }
        return new BookmarkList();
    }

    private static ToDoList parseToTodoList(JSONObject json) {
        try {
            JSONArray todos = (JSONArray) json.get(TODOS);
            ArrayList<ToDo> todoList = new ArrayList<>();
            for (Object todo : todos) {
                todoList.add(parseToTodo((JSONObject) todo));
            }
            return new ToDoList(todoList);
        } catch (NullPointerException e) {
            System.out.printf(DATA_CORRUPT_MESSAGE, TODOS);
        }
        return new ToDoList();
    }

    private static Book parseToBook(JSONObject json) throws NullPointerException {
        JSONObject authorObj = (JSONObject) json.get("author");
        Author author = parseToAuthor(authorObj);
        String title = (String) json.get("title");
        JSONArray array = (JSONArray) json.get("categories");
        ArrayList<String> categories = (ArrayList<String>) array.stream()
                .collect(Collectors.toList());
        return new Book(author, title, categories);
    }

    private static Quote parseToQuote(JSONObject json) throws NullPointerException {
        JSONObject authorObj = (JSONObject) json.get("author");
        Author author = parseToAuthor(authorObj);
        String quote = (String) json.get("quote");
        String reference = (String) json.get("reference");
        JSONArray array = (JSONArray) json.get("categories");
        ArrayList<String> categories = (ArrayList<String>) array.stream()
                .collect(Collectors.toList());
        return new Quote(author, quote, categories, reference);
    }

    private static Category parseToCategory(JSONObject json) throws NullPointerException {
        String name = (String) json.get("category");
        BookList bookList = (BookList) ListManager.getList(ListManager.BOOK_LIST);
        QuoteList quoteList = (QuoteList) ListManager.getList(ListManager.QUOTE_LIST);

        Category category = new Category(name);
        category.setBookList(bookList.filterByCategory(name));
        category.setQuoteList(quoteList.filterByCategory(name));
        return category;
    }

    private static Rating parseToRating(JSONObject json) throws NullPointerException {
        String title = (String) json.get("titleOfRatedBook");
        long rating = (long) json.get("rating");
        return new Rating((int) rating, title);
    }

    private static Bookmark parseToBookmark(JSONObject json) throws NullPointerException {
        JSONObject bookObj = (JSONObject) json.get("book");
        Book book = parseToBook(bookObj);
        long pageNum = (long) json.get("pageNum");
        return new Bookmark(book, (int) pageNum);
    }

    private static ToDo parseToTodo(JSONObject json) throws NullPointerException {
        String name = (String) json.get("name");
        String deadline = (String) json.get("deadline");
        boolean isDone = (boolean) json.get("isDone");
        return new ToDo(name, deadline, isDone);
    }

    private static Author parseToAuthor(JSONObject json) {
        try {
            String authorName = (String) json.get("name");
            return new Author(authorName);
        } catch (NullPointerException e) {
            return null;
        }
    }
}
