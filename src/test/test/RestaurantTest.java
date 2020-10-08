package test;

import model.Restaurant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RestaurantTest {

    private Restaurant r1;

    @BeforeEach
    public void setUp(){
        r1 = new Restaurant("First Restaurant", "Chinese", "Broadway");
    }

    @Test
    public void testConstructor(){
        assertTrue(r1.getName().equals("First Restaurant"));
        assertTrue(r1.getLocation().equals("Broadway"));
        assertTrue(r1.getType().equals("Chinese"));
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
    public void testSetRating(){
        assertEquals(0, r1.getRating());
        r1.setRating(5);
        assertEquals(5, r1.getRating());
    }
}
