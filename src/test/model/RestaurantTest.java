package model;

import model.Restaurant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// test for restaurant class
public class RestaurantTest {

    private Restaurant r1;

    @BeforeEach
    public void setUp(){
        r1 = new Restaurant("First Restaurant", "Chinese", "Broadway");
    }

    @Test
    public void testConstructor(){
        assertEquals("First Restaurant", r1.getName());
        assertEquals("Broadway", r1.getLocation());
        assertEquals("Chinese", r1.getType());
        assertEquals(0, r1.getRating());
        assertFalse(r1.hasVisited());
    }

    @Test
    public void testVisited(){
        assertFalse(r1.hasVisited());
        r1.visited();
        assertTrue(r1.hasVisited());
    }

    @Test
    public void testNotVisited(){
        assertFalse(r1.hasVisited());
        r1.visited();
        assertTrue(r1.hasVisited());
        r1.notVisited();
        assertFalse(r1.hasVisited());
    }


    @Test
    public void testSetRating(){
        assertEquals(0, r1.getRating());
        r1.setRating(5);
        assertEquals(5, r1.getRating());
    }
}
