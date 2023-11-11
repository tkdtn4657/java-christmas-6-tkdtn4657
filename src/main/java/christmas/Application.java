package christmas;

import christmas.controller.Employee;
import christmas.convertor.VisitConvertor;
import christmas.view.RestaurantInputView;
import christmas.view.RestaurantOutputView;

public class Application {
    public static void main(String[] args) {
        Employee restaurant = new Employee(
                new RestaurantInputView(new VisitConvertor()),
                new RestaurantOutputView()
        );

        restaurant.run();
    }
}
