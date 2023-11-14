package christmas.view;

import christmas.Bill;
import christmas.model.menu.Menu;

import java.text.DecimalFormat;
import java.util.EnumMap;
import java.util.List;

public class RestaurantOutputView {
    private static final String BLANK = " ";
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final String EACH_PRINT = "개";
    private static final String WON_PRINT = "원";
    private static final String START_RESTAURANT = "안녕하세요! 우테코 식당 12월 이벤트 플래너입니다.";
    private static final String BENEFITS_PREVIEW = "12월 26일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!" + LINE_SEPARATOR;
    private static final String ORDER_MENU = "<주문 메뉴>";
    private static final String NO_DISCOUNT_PRINT = "<할인 전 총주문 금액>";
    private final DecimalFormat decimalFormat = new DecimalFormat("###,###");
    private Bill arrivedBill;

    public void startPrint() {
        System.out.println(START_RESTAURANT);
    }

    public void benefitsPreviewPrint() {
        System.out.println(BENEFITS_PREVIEW);
    }

    public void orderMenuPrint(Bill arrivedBill) {
        this.arrivedBill = arrivedBill;
        System.out.println(ORDER_MENU);
        System.out.println(menus());
        System.out.println(NO_DISCOUNT_PRINT);
        System.out.println(decimalFormat.format(noDisCountMoney()) + WON_PRINT);
        System.out.println();
    }

    private int noDisCountMoney() {
        List<Menu> pickedMenus = arrivedBill.getPickedMenus();
        EnumMap<Menu, Integer> orderSheet = arrivedBill.getOrderSheet();

        int noDiscountMoney = pickedMenus.stream()
                .mapToInt(menu ->
                        menu.menuPrice() * orderSheet.get(menu)
                )
                .sum();

        return noDiscountMoney;
    }

    private String menus() {
        StringBuilder orderMenuBuilder = new StringBuilder();

        arrivedBill
                .getPickedMenus().forEach(menu ->
                        orderMenuBuilder
                                .append(menu)
                                .append(BLANK)
                                .append(arrivedBill.getOrderSheet().get(menu))
                                .append(EACH_PRINT)
                                .append(LINE_SEPARATOR)
                );
        return orderMenuBuilder.toString();
    }
}
