package christmas.view;

public class RestaurantOutputView {
    private static final String START_RESTAURANT = "안녕하세요! 우테코 식당 12월 이벤트 플래너입니다.";
    public void startPrint() {
        System.out.println(START_RESTAURANT);
    }

    public void orderMenuPrint(String orderMenu) {
        System.out.println(orderMenu);
    }
}
