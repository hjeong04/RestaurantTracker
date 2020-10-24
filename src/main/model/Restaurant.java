package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents a restaurant with a name, type, location, a rating (out of 10) and whether visited or not
public class Restaurant implements Writable {

    private String name;
    private String type;
    private String location;
    private int rating;
    private boolean hasVisited;

    // EFFECTS: Restaurant with the given name, type and location,
    //          has not visited yet and the rating is 0 for now
    public Restaurant(String name, String type, String location) {
        this.name = name;
        this.type = type;
        this.location = location;
        rating = 0;
        hasVisited = false;
    }

    //getter
    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getLocation() {
        return location;
    }

    public int getRating() {
        return rating;
    }

    public boolean hasVisited() {
        return hasVisited;
    }

    // MODIFIES: this
    // EFFECTS: marks the restaurant as visited
    public void visited() {
        hasVisited = true;
    }

    // MODIFIES: this
    // EFFECTS; marks the restaurant as not visited
    public void notVisited() {
        hasVisited = false;
    }

    // REQUIRES: hasVisited is true and 0<=i<=10
    // MODIFIES: this
    // EFFECTS: sets the rating as i
    public void setRating(int i) {
        rating = i;
    }

    // referenced https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("type", type);
        json.put("location", location);
        json.put("visited?", hasVisited);
        json.put("rating", rating);
        return json;
    }
}
