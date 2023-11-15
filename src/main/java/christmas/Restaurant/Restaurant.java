package christmas.Restaurant;

import christmas.bill.Bill;
import christmas.menu.Menu;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.StringTokenizer;

public class Restaurant {
    private static final String ERROR_MESSAGE = "[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.";
    private static final String HYPHEN = "-";
    private static final int DEFAULT_VALUE = 0;
    private static final int AVAILABLE_VALUE = 1;
    private static final int MENU_EACH_MIN = 1;
    private static final int MENU_EACH_MAX = 20;
    private static final int ZERO = 0;
    private static final int ONE = 1;
    private EnumMap<Menu, Integer> orderSheet;
    private List<Menu> pickedMenus;

    public void menuReceive(List<String> splitLine) {
        orderSheet = new EnumMap<Menu, Integer>(Menu.class);
        orderSheetAvailableMenus(splitLine);
        pickedMenus = pickedMenu();
    }


    public Bill menuBill() {
        return new Bill(orderSheet, pickedMenus);
    }

    private void orderSheetAvailableMenus(List<String> splitLine) {
        for (String menu : splitLine) {
            List<String> menuForm = splitMenu(menu);
            orderSheet.put(
                    Menu.valueOf(menuForm.get(ZERO)),
                    Integer.parseInt(menuForm.get(ONE))
            );
        }
    }

    private List<Menu> pickedMenu() {
        return Arrays.stream(Menu.values())
                .filter(menu -> orderSheet.getOrDefault(menu, DEFAULT_VALUE) >= AVAILABLE_VALUE)
                .toList();
    }

    private List<String> splitMenu(String orders) {
        StringTokenizer order = new StringTokenizer(orders, HYPHEN);
        return List.of(order.nextToken(), order.nextToken());
    }

    public boolean orderValidate() {
        try {
            menuValidate();
            isOnlyDrinks();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public void menuValidate() throws IllegalArgumentException {
        int orderEachCount = Arrays.stream(Menu.values())
                .mapToInt(menu -> orderSheet.getOrDefault(menu, ZERO))
                .reduce(0, Integer::sum);

        if (orderEachCount < MENU_EACH_MIN || orderEachCount > MENU_EACH_MAX) {
            throw new IllegalArgumentException(ERROR_MESSAGE);
        }
    }

    public void isOnlyDrinks() throws IllegalArgumentException {
        if (Menu.isOnlyDrinks(pickedMenus, orderSheet)) {
            throw new IllegalArgumentException(ERROR_MESSAGE);
        }
    }
}
