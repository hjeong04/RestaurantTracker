package test.persistence;

// referenced https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

import model.Restaurant;
import model.RestaurantList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkRestaurant(String name, String type, String location,
                                   boolean visited, int rate, Restaurant r) {
        assertEquals(name, r.getName());
        assertEquals(type, r.getType());
        assertEquals(location, r.getLocation());
        assertEquals(visited, r.hasVisited());
        assertEquals(rate, r.getRating());
    }
}
