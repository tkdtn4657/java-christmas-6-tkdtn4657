package christmas.controller;

import christmas.model.Restaurant;
import christmas.model.menu.MenuConvertor;
import christmas.view.RestaurantInputView;
import christmas.view.RestaurantOutputView;

import java.util.List;
import java.util.Optional;

public class Employee {
    private final RestaurantInputView restaurantInputView;
    private final RestaurantOutputView restaurantOutputView;
    private final MenuConvertor menuConvertor;
    private final Restaurant restaurant;

    int n;

    public Employee(RestaurantInputView restaurantInputView,
                    RestaurantOutputView restaurantOutputView,
                    MenuConvertor menuConvertor,
                    Restaurant restaurant) {
        this.restaurantOutputView = restaurantOutputView;
        this.restaurantInputView = restaurantInputView;
        this.menuConvertor = menuConvertor;
        this.restaurant = restaurant;
    }

    public void run() {
        restaurantOutputView.startPrint();
        visitInput();
        menuInput();
        restaurantOutputView.orderMenuPrint(restaurant.menus());
        System.out.println();
    }

    private void visitInput(){
        Optional<Integer> inputNumber = restaurantInputView.visitInput();
        if(!inputNumber.isPresent()){
            visitInput();
            return;
        }
        n = inputNumber.get();
        //값이 있는거니까 사용
    }

    private void menuInput() {
        List<String> splitLine = restaurantInputView.orderInput();

        if (!menuConvertor.inputValidate(splitLine)) {
            menuInput();
            return;
        }

        restaurant.menuReceive(splitLine);
        if (!restaurant.orderValidate()) {
            menuInput();
        }
    }

}
