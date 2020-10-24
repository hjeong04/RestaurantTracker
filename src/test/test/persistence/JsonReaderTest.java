package test.persistence;

// referenced https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

import model.Restaurant;
import model.RestaurantList;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest extends JsonTest{

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            RestaurantList rl = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyRestaurantList() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyRestaurantList.json");
        try {
            RestaurantList rl = reader.read();
            assertEquals("My restaurant list", rl.getName());
            assertEquals(0, rl.getSize());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralRestaurantList() {
        JsonReader reader = new JsonReader( "./data/testReaderGeneralRestaurantList.json");
        try {
            RestaurantList rl = reader.read();
            assertEquals("My restaurant list", rl.getName());
            List<Restaurant> restaurants = rl.viewRestaurantList();
            assertEquals(2, restaurants.size());
            checkRestaurant("TacoMio", "Mexican", "UBC", false, 0, restaurants.get(0));
            checkRestaurant("Bufala", "Pizza", "Kerrisdale", true, 8, restaurants.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }

    }

}
