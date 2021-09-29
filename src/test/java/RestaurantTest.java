import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {

    private Restaurant restaurant;
    private LocalTime currentTime;

    @BeforeEach
    void setup() {
        currentTime = LocalTime.now();
        LocalTime openingTime = currentTime.minusHours(5);
        LocalTime closingTime = currentTime.plusHours(5);
        restaurant = new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        assertTrue(restaurant.isRestaurantOpen());
    }

    @Test
    void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        LocalTime openingTime = currentTime.plusMinutes(1);
        LocalTime closingTime = currentTime.plusHours(11);

        Restaurant restaurant = new Restaurant(
                "ABC",
                "Delhi",
                openingTime,
                closingTime);

        assertFalse(restaurant.isRestaurantOpen());
    }

    @Test
    void is_restaurant_open_should_return_false_if_time_is_after_closing_time(){
        LocalTime openingTime = currentTime.minusHours(11);
        LocalTime closingTime = currentTime.minusMinutes(1);

        Restaurant restaurant = new Restaurant(
                "ABC",
                "Delhi",
                openingTime,
                closingTime);

        assertFalse(restaurant.isRestaurantOpen());
    }
    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    void adding_item_to_menu_should_increase_menu_size_by_1(){
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    void removing_item_from_menu_should_decrease_menu_size_by_1() throws ItemNotFoundException {
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    void removing_item_that_does_not_exist_should_throw_exception() {
        assertThrows(ItemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    @Test
    void test_displayDetails() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream systemOut = new PrintStream(outputStream);
        System.setOut(systemOut);
        restaurant.displayDetails();

        assertTrue(outputStream.toString().contains(restaurant.getName()));
        assertTrue(outputStream.toString().contains(restaurant.getLocation()));
        assertTrue(outputStream.toString().contains(restaurant.getOpeningTime().toString()));
        assertTrue(outputStream.toString().contains(restaurant.getClosingTime().toString()));
    }

    @Test
    void empty_order_should_return_zero_total() {
        List<String> items = new ArrayList<>();

        int actual = restaurant.getOrderValue(items);

        assertEquals(0, actual);
    }

    @Test
    void non_empty_order_should_return_non_zero_total() {
        List<String> items = new ArrayList<>();
        items.add("Sweet corn soup");
        items.add("Vegetable lasagne");

        int expected = 388;

        int actual = restaurant.getOrderValue(items);

        assertEquals(expected, actual);
    }

    @Test
    void order_containing_items_not_listed_in_menu_should_return_total_value_for_existing_items() {
        List<String> items = new ArrayList<>();
        items.add("French fries");
        items.add("Vegetable lasagne");

        int expected = 269;

        int actual = restaurant.getOrderValue(items);

        assertEquals(expected, actual);
    }
}