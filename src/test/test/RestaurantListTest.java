package test;

import model.Restaurant;
import model.RestaurantList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantListTest {
    private RestaurantList rList;

    @BeforeEach
    public void testConstructors() {
        rList = new RestaurantList();
    }

    @Test
    public void testViewRestaurantList(){
        Restaurant r1 = new Restaurant("Restaurant1", "Chinese", "UBC");
        Restaurant r2 = new Restaurant("Restaurant2", "Italian", "Downtown");
        Restaurant r3 = new Restaurant("Restaurant3", "Bubble Tea", "Richmond");

        rList.addRestaurant(r1);
        rList.addRestaurant(r2);
        rList.addRestaurant(r3);

        List<Restaurant> rList1 = rList.viewRestaurantList();
        assertEquals(3, rList1.size());
    }

    @Test
    public void testAddRestaurantList(){
        assertEquals(0, rList.getSize());

        Restaurant r1 = new Restaurant("Restaurant1", "Chinese", "UBC");
        Restaurant r2 = new Restaurant("Restaurant2", "Italian", "Downtown");
        Restaurant r3 = new Restaurant("Restaurant3", "Bubble Tea", "Richmond");

        rList.addRestaurant(r1);
        rList.addRestaurant(r2);
        rList.addRestaurant(r3);

        assertEquals(3, rList.getSize());
    }

    @Test
    public void testRemoveRestaurant(){
        Restaurant r1 = new Restaurant("Restaurant1", "Chinese", "UBC");
        Restaurant r2 = new Restaurant("Restaurant2", "Italian", "Downtown");
        Restaurant r3 = new Restaurant("Restaurant3", "Bubble Tea", "Richmond");

        rList.addRestaurant(r1);
        rList.addRestaurant(r2);
        rList.addRestaurant(r3);

        rList.removeRestaurant(r2);
        rList.removeRestaurant(r3);
        assertEquals(1, rList.getSize());
    }

    @Test
    public void testSearchByNameNone(){
        assertEquals(0, rList.searchByName("Restaurant1").size());
    }

    @Test
    public void testSearchByNameMultiple(){
        Restaurant r1 = new Restaurant("Restaurant1", "Chinese", "UBC");
        Restaurant r2 = new Restaurant("Restaurant2", "Italian", "Downtown");
        Restaurant r3 = new Restaurant("Restaurant3", "Chinese", "Richmond");

        rList.addRestaurant(r1);
        rList.addRestaurant(r2);
        rList.addRestaurant(r3);

        assertEquals(1, rList.searchByName("restaurant1").size());
    }
    @Test
    public void testSearchByTypeNone(){
        assertEquals(0, rList.searchByType("Chinese").size());
    }

    @Test
    public void testSearchByTypeMultiple(){
        Restaurant r1 = new Restaurant("Restaurant1", "Chinese", "UBC");
        Restaurant r2 = new Restaurant("Restaurant2", "Italian", "Downtown");
        Restaurant r3 = new Restaurant("Restaurant3", "Chinese", "Richmond");

        rList.addRestaurant(r1);
        rList.addRestaurant(r2);
        rList.addRestaurant(r3);

        assertEquals(2, rList.searchByType("chinese").size());
    }

    @Test
    public void testSearchByLocationNone(){
        assertEquals(0, rList.searchByLocation("UBC").size());
    }

    @Test
    public void testSearchByLocationMultiple(){
        Restaurant r1 = new Restaurant("Restaurant1", "Chinese", "UBC");
        Restaurant r2 = new Restaurant("Restaurant2", "Italian", "UBC");
        Restaurant r3 = new Restaurant("Restaurant3", "Bubble Tea", "Richmond");
        Restaurant r4 = new Restaurant("Restaurant4", "Ice Cream", "UBC");

        rList.addRestaurant(r1);
        rList.addRestaurant(r2);
        rList.addRestaurant(r3);
        rList.addRestaurant(r4);

        assertEquals(3, rList.searchByLocation("ubc").size());
    }

    @Test
    public void testGetSize(){
        assertEquals(0, rList.getSize());

        Restaurant r1 = new Restaurant("Restaurant1", "Chinese", "UBC");
        Restaurant r2 = new Restaurant("Restaurant2", "Italian", "Downtown");
        Restaurant r3 = new Restaurant("Restaurant3", "Chinese", "Richmond");

        rList.addRestaurant(r1);
        rList.addRestaurant(r2);
        rList.addRestaurant(r3);

        assertEquals(3, rList.getSize());
    }


}