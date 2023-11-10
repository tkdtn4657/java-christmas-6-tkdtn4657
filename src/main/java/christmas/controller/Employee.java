package christmas.controller;

import christmas.model.Restaurant;
import christmas.view.RestaurantInputView;
import christmas.view.RestaurantOutputView;

public class Employee {
    private final RestaurantInputView restaurantInputView;
    private final RestaurantOutputView restaurantOutputView;
    private final Restaurant restaurant = new Restaurant();

    public Employee(RestaurantInputView restaurantInputView,
                    RestaurantOutputView restaurantOutputView) {
        this.restaurantOutputView = restaurantOutputView;
        this.restaurantInputView = restaurantInputView;

    }

    public void run() {
        restaurantOutputView.startPrint();
        restaurantInputView.visitInput();
        restaurantInputView.orderInput();
    }


}
