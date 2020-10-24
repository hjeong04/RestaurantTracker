package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// represents a list of restaurant
public class RestaurantList implements Writable {
    private String name;
    private List<Restaurant> listr;

    // EFFECTS: creates an empty list of restaurant with a name
    public RestaurantList(String name) {
        this.name = name;
        listr = new ArrayList<>();
    }

    // EFFECTS returns the name
    public String getName() {
        return name;
    }

    // EFFECTS: returns the restaurant list
    public List<Restaurant> viewRestaurantList() {
        return listr;
    }

    // MODIFIES: this
    // EFFECTS: adds the restaurant to the list
    public void addRestaurant(Restaurant r) {
        listr.add(r);
    }

    // REQUIRES: the restaurant given is in the list
    // EFFECTS: remove the given restaurant from the list
    public void removeRestaurant(Restaurant r) {
        listr.remove(r);
    }

    // REQUIRES: the list is not empty
    // EFFECTS: returns the list of restaurants with the given name
    public List<Restaurant> searchByName(String name) {
        String lowercase = name.toLowerCase();
        List<Restaurant> list = new ArrayList<>();

        for (Restaurant r : listr) {
            String toLowercase = r.getName().toLowerCase();
            if (toLowercase.equals(lowercase)) {
                list.add(r);
            }
        }
        return list;
    }

    // REQUIRES: the list is not empty
    // EFFECTS: returns the list of restaurants with the given type
    public List<Restaurant> searchByType(String type) {
        String lowercase = type.toLowerCase();
        List<Restaurant> list = new ArrayList<>();

        for (Restaurant r : listr) {
            String toLowercase = r.getType().toLowerCase();
            if (toLowercase.equals(lowercase)) {
                list.add(r);
            }
        }
        return list;
    }

    // REQUIRES: the list is not empty
    // EFFECTS: returns the list of restaurants with the given location
    public List<Restaurant> searchByLocation(String location) {
        String lowercase = location.toLowerCase();
        List<Restaurant> list = new ArrayList<>();

        for (Restaurant r : listr) {
            String toLowercase = r.getLocation().toLowerCase();
            if (toLowercase.equals(lowercase)) {
                list.add(r);
            }
        }
        return list;
    }

    // EFFECTS: returns the number of restaurants in the list
    public int getSize() {
        return listr.size();
    }

    // referenced https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("restaurants", restaurantsToJson());
        return json;
    }

    // EFFECTS:returns restaurants in this list as a JSON array
    private JSONArray restaurantsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Restaurant r : listr) {
            jsonArray.put(r.toJson());
        }

        return jsonArray;
    }
}

