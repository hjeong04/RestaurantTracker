package test.persistence;

// referenced https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

import model.Restaurant;
import model.RestaurantList;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFail() {
        try {
            RestaurantList rl = new RestaurantList("My restaurant list");
            JsonWriter writer = new JsonWriter("./data/noSuchFile.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyRestaurantList() {
        try {
            RestaurantList rl = new RestaurantList("My restaurant list");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyRestaurantList.json");
            writer.open();
            writer.write(rl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyRestaurantList.json");
            rl = reader.read();
            assertEquals("My restaurant list", rl.getName());
            assertEquals(0, rl.getSize());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralRestaurantList() {
        try {
            RestaurantList rl = new RestaurantList("My restaurant list");
            rl.addRestaurant(new Restaurant("TacoMio", "Mexican", "UBC"));
            Restaurant r2 = new Restaurant("Bufala", "Pizza", "Kerrisdale");
            r2.visited();
            r2.setRating(8);
            rl.addRestaurant(r2);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralRestaurantList.json");
            writer.open();
            writer.write(rl);
            writer.close();

            JsonReader reader = new JsonReader("./data/test/WriterGeneralRestaurantList.json");
            rl = reader.read();
            assertEquals("My restaurant list", rl.getName());
            List<Restaurant> restaurants = rl.viewRestaurantList();
            assertEquals(2, restaurants.size());
            checkRestaurant("Bufala", "Pizza", "Kerrisdale", true, 8, restaurants.get(0));
            checkRestaurant("TacoMio", "Mexican", "UBC", false, 0, restaurants.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

}
