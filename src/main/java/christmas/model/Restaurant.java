package christmas.model;

import christmas.model.menu.Menu;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.StringTokenizer;

public class Restaurant {
    private static final String ERROR_MESSAGE = "[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.";
    private static final String HYPHEN = "-";
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final String EACH_PRINT = "개";
    private static final int DEFAULT_VALUE = 0;
    private static final int AVAILABLE_VALUE = 1;
    private static final int MENU_EACH_MIN = 1;
    private static final int MENU_EACH_MAX = 20;
    private static final String BLANK = " ";
    private EnumMap<Menu, Integer> orderSheet;
    private List<Menu> pickedMenus;

    public void menuReceive(List<String> splitLine) {
        orderSheet = new EnumMap<Menu, Integer>(Menu.class);
        orderSheetAvailableMenus(splitLine);
        pickedMenus = pickedMenu();
        menus();
    }

    public String menus() {
        StringBuilder orderMenuBuilder = new StringBuilder();
        pickedMenus.forEach(menu ->
                orderMenuBuilder.append(menu)
                        .append(BLANK)
                        .append(orderSheet.get(menu))
                        .append(EACH_PRINT)
                        .append(LINE_SEPARATOR)
        );
        return orderMenuBuilder.toString();
    }

    private void orderSheetAvailableMenus(List<String> splitLine) {
        for (String menu : splitLine) {
            List<String> menuForm = splitMenu(menu);
            orderSheet.put(
                    Menu.valueOf(menuForm.get(0)),
                    Integer.parseInt(menuForm.get(1))
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
                .mapToInt(menu -> orderSheet.getOrDefault(menu, 0))
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
