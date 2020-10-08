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

        assertTrue(rList.removeRestaurant(r2));
        assertEquals(1, rList.getSize());

        assertFalse(rList.removeRestaurant(r3));
    }

    @Test
    public void testNameInList(){
        Restaurant r1 = new Restaurant("Restaurant1", "Chinese", "UBC");
        Restaurant r2 = new Restaurant("Restaurant2", "Italian", "Downtown");
        Restaurant r3 = new Restaurant("Restaurant3", "Bubble Tea", "Richmond");

        rList.addRestaurant(r1);
        rList.addRestaurant(r2);
        rList.addRestaurant(r3);

        assertTrue(rList.nameInList("Restaurant1"));
        assertTrue(rList.nameInList("Restaurant3"));
        assertFalse(rList.nameInList("Restaurant5"));
    }

    @Test
    public void testSearchByName(){
        Restaurant r1 = new Restaurant("Restaurant1", "Chinese", "UBC");
        Restaurant r2 = new Restaurant("Restaurant2", "Italian", "Downtown");
        Restaurant r3 = new Restaurant("Restaurant3", "Bubble Tea", "Richmond");

        rList.addRestaurant(r1);
        rList.addRestaurant(r2);
        rList.addRestaurant(r3);

        Restaurant searched = rList.searchByName("Restaurant3");
        String sType = searched.getType();
        String sLocation = searched.getLocation();

        assertTrue(r3.getType().equals(sType));
        assertTrue(r3.getLocation().equals(sLocation));
    }

    @Test
    public void testSearchTypeNone(){
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

        assertEquals(2, rList.searchByType("Chinese").size());
    }

    @Test
    public void testSearchLocationNone(){
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

        assertEquals(3, rList.searchByLocation("UBC").size());
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