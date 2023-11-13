package christmas.controller;

import christmas.model.Restaurant;
import christmas.model.menu.MenuConvertor;
import christmas.view.RestaurantInputView;
import christmas.view.RestaurantOutputView;

import java.util.List;

public class Employee {
    private final RestaurantInputView restaurantInputView;
    private final RestaurantOutputView restaurantOutputView;
    private final MenuConvertor menuConvertor;
    private final Restaurant restaurant = new Restaurant();

    public Employee(RestaurantInputView restaurantInputView,
                    RestaurantOutputView restaurantOutputView,
                    MenuConvertor menuConvertor) {
        this.restaurantOutputView = restaurantOutputView;
        this.restaurantInputView = restaurantInputView;
        this.menuConvertor = menuConvertor;
    }

    public void run() {
        restaurantOutputView.startPrint();
        restaurantInputView.visitInput();
        menuInput();

    }

    public void menuInput(){

        boolean passInputValidate, passMenuValidate;

        do {
            List<String> splitLine = restaurantInputView.orderInput();
            passInputValidate = menuConvertor.inputValidate(splitLine);
            restaurant.menuReceive(splitLine);
            passMenuValidate = restaurant.menuValidate();
        } while (!passInputValidate || !passMenuValidate);

    }


}
