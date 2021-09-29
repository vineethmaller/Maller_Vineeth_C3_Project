public class RestaurantNotFoundException extends Exception {

    public RestaurantNotFoundException(String restaurantName) {
        super(restaurantName);
    }
}
