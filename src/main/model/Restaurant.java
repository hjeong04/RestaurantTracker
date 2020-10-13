package model;

// Represents a restaurant with a name, type, location, a rating (out of 10) and whether visited or not
public class Restaurant {

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

    // REQUIRES: hasVisited is true and 0<=i<=10
    // MODIFIES: this
    // EFFECTS: sets the rating as i
    public void setRating(int i) {
        rating = i;
    }

}
