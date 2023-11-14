package christmas;

import christmas.controller.Employee;
import christmas.convertor.VisitConvertor;
import christmas.model.Restaurant;
import christmas.model.menu.MenuConvertor;
import christmas.view.RestaurantInputView;
import christmas.view.RestaurantOutputView;

public class Application {
    public static void main(String[] args) {
        Employee employee = new Employee(
                new RestaurantInputView(new VisitConvertor()),
                new RestaurantOutputView(),
                new MenuConvertor(),
                new Restaurant()
        );

        employee.run();
    }
}
