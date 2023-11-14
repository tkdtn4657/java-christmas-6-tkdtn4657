package christmas.view;

import christmas.Bill;

public class RestaurantOutputView {
    private static final String BLANK = " ";
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final String EACH_PRINT = "개";
    private static final String START_RESTAURANT = "안녕하세요! 우테코 식당 12월 이벤트 플래너입니다.";
    private static final String BENEFITS_PREVIEW = "12월 26일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!";
    private static final String ORDER_MENU = "<주문 메뉴>";
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
    }

    public String menus() {
        StringBuilder orderMenuBuilder = new StringBuilder();

        arrivedBill.getPickedMenus().forEach(menu ->
                orderMenuBuilder.append(menu)
                        .append(BLANK)
                        .append(arrivedBill.getOrderSheet().get(menu))
                        .append(EACH_PRINT)
                        .append(LINE_SEPARATOR)
        );
        return orderMenuBuilder.toString();
    }
}
