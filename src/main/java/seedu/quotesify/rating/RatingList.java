package seedu.quotesify.rating;

import org.json.simple.JSONArray;

import seedu.quotesify.lists.QuotesifyList;

import java.util.ArrayList;

public class RatingList extends QuotesifyList<Rating> {
    private ArrayList<Rating> ratings = super.getList();

    public RatingList() {
        super(new ArrayList<>());
    }

    public RatingList(ArrayList<Rating> ratings) {
        super(ratings);
    }

    @Override
    public void add(Rating newRating) {
        ratings.add(newRating);
    }

    @Override
    public void delete(int index) {
        ratings.remove(index);
    }

    @Override
    public String toString() {
        String ratingsToReturn = "";
        for (Rating rating : ratings) {
            ratingsToReturn += rating.toString() + System.lineSeparator();
        }
        return ratingsToReturn;
    }

    @Override
    public JSONArray toJsonArray() {
        JSONArray list = new JSONArray();
        for (Rating rating : ratings) {
            list.add(rating.toJson());
        }
        return list;
    }
}
