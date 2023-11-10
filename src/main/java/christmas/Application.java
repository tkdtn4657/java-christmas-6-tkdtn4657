package christmas;

import christmas.controller.Restaurant;
import christmas.view.RestaurantInputView;
import christmas.view.RestaurantOutputView;

public class Application {
    public static void main(String[] args) {
        Restaurant restaurant = new Restaurant(
                new RestaurantInputView(),
                new RestaurantOutputView()
        );

        restaurant.run();
    }
}
