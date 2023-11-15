package christmas;

import christmas.employee.Employee;
import christmas.convertor.VisitConvertor;
import christmas.Restaurant.Restaurant;
import christmas.menu.MenuConvertor;
import christmas.Restaurant.view.RestaurantInputView;
import christmas.Restaurant.view.RestaurantOutputView;

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
