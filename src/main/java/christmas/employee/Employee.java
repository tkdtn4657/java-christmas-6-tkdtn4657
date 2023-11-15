package christmas.employee;

import christmas.calendarDiscount.CalendarDiscount;
import christmas.Restaurant.Restaurant;
import christmas.menu.MenuConvertor;
import christmas.Restaurant.view.RestaurantInputView;
import christmas.Restaurant.view.RestaurantOutputView;
import java.util.List;
import java.util.Optional;

public class Employee {
    private final RestaurantInputView restaurantInputView;
    private final RestaurantOutputView restaurantOutputView;
    private final MenuConvertor menuConvertor;
    private final Restaurant restaurant;

    private CalendarDiscount calendarDiscount;

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
        restaurantOutputView.benefitsPreviewPrint(calendarDiscount.getDay());
        restaurantOutputView.orderMenuPrint(restaurant.menuBill(), calendarDiscount);
    }

    private void visitInput() {
        Optional<Integer> inputNumber = restaurantInputView.visitInput();
        if (!inputNumber.isPresent()) {
            visitInput();
            return;
        }
        calendarDiscount = new CalendarDiscount(inputNumber.get());
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
