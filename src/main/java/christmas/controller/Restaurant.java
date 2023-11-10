package christmas.controller;

import christmas.view.RestaurantInputView;
import christmas.view.RestaurantOutputView;

public class Restaurant {
    private final RestaurantInputView restaurantInputView;
    private final RestaurantOutputView restaurantOutputView;

    public Restaurant(RestaurantInputView restaurantInputView,
                      RestaurantOutputView restaurantOutputView){
       this.restaurantOutputView = restaurantOutputView;
        this.restaurantInputView = restaurantInputView;
    }

    public void run(){

    }



}
