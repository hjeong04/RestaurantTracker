package test;

import model.Restaurant;
import model.RestaurantList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// test for restaurant list class
class RestaurantListTest {
    private RestaurantList rlist;

    @BeforeEach
    public void testConstructors() {
        rlist = new RestaurantList();
    }

    @Test
    public void testViewRestaurantList(){
        Restaurant r1 = new Restaurant("Restaurant1", "Chinese", "UBC");
        Restaurant r2 = new Restaurant("Restaurant2", "Italian", "Downtown");
        Restaurant r3 = new Restaurant("Restaurant3", "Bubble Tea", "Richmond");

        rlist.addRestaurant(r1);
        rlist.addRestaurant(r2);
        rlist.addRestaurant(r3);

        List<Restaurant> rList1 = rlist.viewRestaurantList();
        assertEquals(3, rList1.size());
    }

    @Test
    public void testAddRestaurantList(){
        assertEquals(0, rlist.getSize());

        Restaurant r1 = new Restaurant("Restaurant1", "Chinese", "UBC");
        Restaurant r2 = new Restaurant("Restaurant2", "Italian", "Downtown");
        Restaurant r3 = new Restaurant("Restaurant3", "Bubble Tea", "Richmond");

        rlist.addRestaurant(r1);
        rlist.addRestaurant(r2);
        rlist.addRestaurant(r3);

        assertEquals(3, rlist.getSize());
    }

    @Test
    public void testRemoveRestaurant(){
        Restaurant r1 = new Restaurant("Restaurant1", "Chinese", "UBC");
        Restaurant r2 = new Restaurant("Restaurant2", "Italian", "Downtown");
        Restaurant r3 = new Restaurant("Restaurant3", "Bubble Tea", "Richmond");

        rlist.addRestaurant(r1);
        rlist.addRestaurant(r2);
        rlist.addRestaurant(r3);

        rlist.removeRestaurant(r2);
        rlist.removeRestaurant(r3);
        assertEquals(1, rlist.getSize());
    }

    @Test
    public void testSearchByNameNone(){
        assertEquals(0, rlist.searchByName("Restaurant1").size());
    }

    @Test
    public void testSearchByNameMultiple(){
        Restaurant r1 = new Restaurant("Restaurant1", "Chinese", "UBC");
        Restaurant r2 = new Restaurant("Restaurant2", "Italian", "Downtown");
        Restaurant r3 = new Restaurant("Restaurant3", "Chinese", "Richmond");

        rlist.addRestaurant(r1);
        rlist.addRestaurant(r2);
        rlist.addRestaurant(r3);

        assertEquals(1, rlist.searchByName("restaurant1").size());
    }
    @Test
    public void testSearchByTypeNone(){
        assertEquals(0, rlist.searchByType("Chinese").size());
    }

    @Test
    public void testSearchByTypeMultiple(){
        Restaurant r1 = new Restaurant("Restaurant1", "Chinese", "UBC");
        Restaurant r2 = new Restaurant("Restaurant2", "Italian", "Downtown");
        Restaurant r3 = new Restaurant("Restaurant3", "Chinese", "Richmond");

        rlist.addRestaurant(r1);
        rlist.addRestaurant(r2);
        rlist.addRestaurant(r3);

        assertEquals(2, rlist.searchByType("chinese").size());
    }

    @Test
    public void testSearchByLocationNone(){
        assertEquals(0, rlist.searchByLocation("UBC").size());
    }

    @Test
    public void testSearchByLocationMultiple(){
        Restaurant r1 = new Restaurant("Restaurant1", "Chinese", "UBC");
        Restaurant r2 = new Restaurant("Restaurant2", "Italian", "UBC");
        Restaurant r3 = new Restaurant("Restaurant3", "Bubble Tea", "Richmond");
        Restaurant r4 = new Restaurant("Restaurant4", "Ice Cream", "UBC");

        rlist.addRestaurant(r1);
        rlist.addRestaurant(r2);
        rlist.addRestaurant(r3);
        rlist.addRestaurant(r4);

        assertEquals(3, rlist.searchByLocation("ubc").size());
    }

    @Test
    public void testGetSize(){
        assertEquals(0, rlist.getSize());

        Restaurant r1 = new Restaurant("Restaurant1", "Chinese", "UBC");
        Restaurant r2 = new Restaurant("Restaurant2", "Italian", "Downtown");
        Restaurant r3 = new Restaurant("Restaurant3", "Chinese", "Richmond");

        rlist.addRestaurant(r1);
        rlist.addRestaurant(r2);
        rlist.addRestaurant(r3);

        assertEquals(3, rlist.getSize());
    }


}