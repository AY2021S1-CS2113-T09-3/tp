package seedu.quotesify.quote;

import org.json.simple.JSONObject;
import seedu.quotesify.author.Author;
import seedu.quotesify.parser.JsonSerializer;

import java.util.ArrayList;

public class Quote implements JsonSerializer {
    private Author author;
    private String quote;
    private ArrayList<String> categories = new ArrayList<>();
    private String reference;
    private String reflection;

    public Quote(String quote) {
        this.quote = quote;
    }

    public Quote(String quote, Author author) {
        this.quote = quote;
        this.author = author;
    }

    public Quote(String quote,String reference) {
        this.quote = quote;
        this.reference = reference;
    }

    public Quote(String quote, String reference, Author author) {
        this.quote = quote;
        this.author = author;
        this.reference = reference;
    }

    public Quote(Author author, String quote, ArrayList<String> categories, String reference) {
        this.author = author;
        this.quote = quote;
        this.categories = categories;
        this.reference = reference;
    }

    public Quote(Author author, String quote, ArrayList<String> categories, String reference, String reflection) {
        this.author = author;
        this.quote = quote;
        this.categories = categories;
        this.reference = reference;
        this.reflection = reflection;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getAuthorName() {
        return author.getName();
    }

    public boolean hasAuthor() {
        if (author != null) {
            return true;
        } else {
            return false;
        }
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public boolean hasReference() {
        if (reference != null) {
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<String> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<String> categories) {
        this.categories = categories;
    }

    public void setReflection(String reflection) {
        this.reflection = reflection;
    }

    public String getReflection() {
        return reflection;
    }

    public void setReflectionNull() {
        reflection = null;
    }

    @Override
    public String toString() {
        String quoteWithoutInformation = '\"' + quote + '\"';
        String quoteInformation = "";
        if (author != null && reference != null) {
            quoteInformation = " - " + author.getName() + ", (" + reference + ')';
        } else if (author != null) {
            quoteInformation = " - " + author.getName();
        } else if (reference != null) {
            quoteInformation = " - " + reference;
        }
        return quoteWithoutInformation + quoteInformation;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        if (hasAuthor()) {
            json.put("author", this.getAuthor().toJson());
        } else {
            json.put("author", null);
        }
        json.put("quote", this.getQuote());
        json.put("categories", this.getCategories());
        json.put("reference", this.getReference());
        json.put("reflection", this.getReflection());
        return json;
    }
}