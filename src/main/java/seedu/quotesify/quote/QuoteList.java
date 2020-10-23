package seedu.quotesify.quote;

import org.json.simple.JSONArray;
import seedu.quotesify.exception.QuotesifyException;
import seedu.quotesify.lists.QuotesifyList;

import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Collectors;

public class QuoteList extends QuotesifyList<Quote> {
    private ArrayList<Quote> quotes = super.getList();

    public QuoteList() {
        super(new ArrayList<>());
    }

    public QuoteList(ArrayList<Quote> quotes) {
        super(quotes);
    }

    public Quote getQuote(int index) {
        return quotes.get(index);
    }

    public int getSize() {
        return quotes.size();
    }

    public int getIndex(Quote quote) {
        return quotes.indexOf(quote);
    }

    public void editQuote(Quote quote, int quoteNumber) {
        quotes.set(quoteNumber, quote);
    }

    public Quote addReflection(String reflection, int quoteNumber) {
        Quote quote = quotes.get(quoteNumber);
        quote.setReflection(reflection);
        quotes.set(quoteNumber, quote);
        return quote;
    }

    public void deleteReflection(int index) {
        quotes.get(index).setReflectionNull();
    }

    @Override
    public void add(Quote newQuote) {
        quotes.add(newQuote);
    }

    @Override
    public void delete(int index) {
        quotes.remove(index);
    }

    @Override
    public String toString() {
        String quotesToReturn = "";
        for (int i = 0; i < getSize(); i++) {
            quotesToReturn += (i + 1 + ". " + quotes.get(i).toString()) + System.lineSeparator();
        }
        return quotesToReturn;
    }

    public QuoteList filterByCategory(String categoryName) {
        ArrayList<Quote> filteredQuotes = (ArrayList<Quote>) quotes.stream()
                .filter(quote -> {
                    ArrayList<String> categories = quote.getCategories();
                    return categories.contains(categoryName);
                }).collect(Collectors.toList());
        return new QuoteList(filteredQuotes);
    }

    public String getRandomQuote() {
        try {
            Random rand = new Random();
            int randomQuoteNumber = rand.nextInt(getSize() - 1);
            Quote quoteToPrint = getQuote(randomQuoteNumber);
            return quoteToPrint.toString();
        } catch (IllegalArgumentException e) {
            return "*Inserts inspirational quote here*";
        }
    }

    public String getAllQuotesByAuthor(QuoteList quoteList, String authorName) {
        String listToReturn = "";
        for (Quote quote : quoteList.getList()) {
            if (quote.hasAuthor() && quote.getAuthorName().equals(authorName)) {
                listToReturn += quote.toString() + System.lineSeparator();
            }
        }
        return listToReturn;
    }

    public String getAllQuotesByReference(QuoteList quoteList, String reference) {
        String listToReturn = "";
        for (Quote quote : quoteList.getList()) {
            if (quote.hasReference() && quote.getReference().equals(reference)) {
                listToReturn += quote.toString() + System.lineSeparator();
            }
        }
        return listToReturn;
    }

    public String getAllQuotesByReferenceAndAuthor(QuoteList quoteList, String reference, String authorName) {
        String listToReturn = "";
        for (Quote quote : quoteList.getList()) {
            if (quote.hasReference() && quote.getReference().equals(reference)) {
                if (quote.hasAuthor() && quote.getAuthorName().equals(authorName)) {
                    listToReturn += quote.toString() + System.lineSeparator();
                }
            }
        }
        return listToReturn;
    }

    public String findQuoteByKeyword(QuoteList quoteList, String keyword) {
        String listToReturn = "";
        for (Quote quote : quoteList.getList()) {
            if (quote.getQuote().contains(keyword)) {
                listToReturn += quote.toString() + System.lineSeparator();
            } else if (quote.hasReference() && quote.getReference().contains(keyword)) {
                listToReturn += quote.toString() + System.lineSeparator();
            } else if (quote.hasAuthor() && quote.getAuthorName().contains(keyword)) {
                listToReturn += quote.toString() + System.lineSeparator();
            }
        }
        return listToReturn;
    }

    @Override
    public JSONArray toJsonArray() {
        JSONArray list = new JSONArray();
        for (Quote quote : quotes) {
            list.add(quote.toJson());
        }
        return list;
    }
}
