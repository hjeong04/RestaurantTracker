package persistence;

// referenced https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

import model.Restaurant;
import model.RestaurantList;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads restaurantlist from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads restaurantlist from file and returns it
    // throws IOException if an error occurs reading data from file
    public RestaurantList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseRestaurantList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses restaurantlist from JSON object and returns it
    private RestaurantList parseRestaurantList(JSONObject jsonObJect) {
        String name = jsonObJect.getString("name");
        RestaurantList rl = new RestaurantList(name);
        addRestaurants(rl, jsonObJect);
        return rl;
    }

    // MODIFIES: rl
    // EFFECTS: parses restaurants from JSON object and adds them to restaurantlist
    private void addRestaurants(RestaurantList rl, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("restaurants");
        for (Object json : jsonArray) {
            JSONObject nextRestaurant = (JSONObject) json;
            addRestaurant(rl, nextRestaurant);
        }
    }

    // MODIFIES: rl
    // EFFECTS: parses restaurant from JSON object and adds it to restaurantlist
    private void addRestaurant(RestaurantList rl, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String type = jsonObject.getString("type");
        String location = jsonObject.getString("location");
        Restaurant restaurant = new Restaurant(name, type, location);
        if (jsonObject.getBoolean("visited?")) {
            restaurant.visited();
            restaurant.setRating(jsonObject.getInt("rating"));
        } else {
            restaurant.notVisited();
            restaurant.setRating(0);
        }
        rl.addRestaurant(restaurant);
    }
}
