package seedu.quotesify.book;

import org.json.simple.JSONObject;
import seedu.quotesify.author.Author;
import seedu.quotesify.parser.JsonSerializer;
import seedu.quotesify.ui.UiMessage;

import java.util.ArrayList;

public class Book implements JsonSerializer {
    private Author author;
    private String title;
    private boolean isDone;
    private ArrayList<String> categories = new ArrayList<>();
    private int rating;

    public Book(Author author, String title) {
        this.author = author;
        this.title = title;
        this.isDone = false;
        this.rating = 0;
    }

    public Book(Author author, String title, ArrayList<String> category) {
        this.author = author;
        this.title = title;
        this.isDone = false;
        this.categories = category;
        this.rating = 0;
    }

    public Book(Author author, String title, ArrayList<String> category, int rating) {
        this.author = author;
        this.title = title;
        this.isDone = false;
        this.categories = category;
        this.rating = rating;
    }

    public Book(Author author, String title, boolean isDone, ArrayList<String> category, int rating) {
        this.author = author;
        this.title = title;
        this.isDone = isDone;
        this.categories = category;
        this.rating = rating;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public String getStatusIcon() {
        return isDone ? "[v] " : "[x] ";
    }

    public ArrayList<String> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<String> categories) {
        this.categories = categories;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int ratingScore) {
        this.rating = ratingScore;
    }

    public String getBookDetailString() {
        String stringToReturn = "";

        if (isDone) {
            stringToReturn += "[Finished Reading]" + System.lineSeparator();
        }

        stringToReturn += "Title: " + title + System.lineSeparator();
        stringToReturn += "Author: " + author.getName() + System.lineSeparator();
        stringToReturn += "Categories: " + System.lineSeparator();

        int index = 1;
        if (categories.size() > 0) {
            for (String category : categories) {
                stringToReturn += index + ". " + category + System.lineSeparator();
                index++;
            }
        } else {
            stringToReturn += UiMessage.EMPTY_CATEGORY_LIST_MESSAGE + System.lineSeparator();
        }

        if (rating != 0) {
            stringToReturn += "Rating: " + rating + System.lineSeparator();
        }

        return stringToReturn;
    }

    @Override
    public String toString() {
        if (categories != null) {
            return title + " by " + author.getName(); // removed display of category
        }

        return title + " by " + author.getName();
    }

    @Override
    public JSONObject toJson() {
        JSONObject details = new JSONObject();
        details.put("author", this.getAuthor().toJson());
        details.put("title", this.getTitle());
        details.put("isDone", this.isDone());
        details.put("categories", this.getCategories());
        details.put("rating", this.getRating());
        return details;
    }
}
