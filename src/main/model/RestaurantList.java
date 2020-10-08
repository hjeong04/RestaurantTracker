package model;

import java.util.ArrayList;
import java.util.List;

public class RestaurantList {

    private List<Restaurant> listr;

    // EFFECTS: creates an empty list of restaurant
    public RestaurantList() {
        listr = new ArrayList<Restaurant>();
    }

    // MODIFIES: this
    // EFFECTS: adds the restaurant to the list
    public void addRestaurant(Restaurant r) {
        listr.add(r);
    }

    // MODIFIES: this
    // EFFECTS: if the given restaurant is found in the list, remove it and return true
    //          otherwise return false
    public boolean removeRestaurant(Restaurant r) {
        for (Restaurant r1 : listr) {
            if (r1.equals(r)) {
                listr.remove(r);
                return true;
            }
        }
        return false;
    }

    // REQUIRES: the list is not empty
    // EFFECTS: returns true if the restaurant with the given name is in the list, false otherwise
    public boolean nameInList(String name) {
        for (Restaurant r : listr) {
            if (r.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    // REQUIRES: the list is not empty
    // EFFECTS: returns the list of restaurants with the given name
    public Restaurant searchByName(String name) {
        List<Restaurant> list = new ArrayList<Restaurant>();
        for (Restaurant r : listr) {
            if (r.getName().equals(name)) {
                return r;
            }
        }

    }

    // REQUIRES: the list is not empty
    // EFFECTS: returns the list of restaurants with the given type
    public List<Restaurant> searchByType(String type) {
        // stub;
        return listr;
    }

    // REQUIRES: the list is not empty
    // EFFECTS: returns the list of restaurants with the given location
    public List<Restaurant> searchByLocation(String location) {
        // stub;
        return listr;
    }

    // EFFECTS: returns the number of restaurants in the list
    public int getSize() {
        // stub;
        return 0;
    }

}

