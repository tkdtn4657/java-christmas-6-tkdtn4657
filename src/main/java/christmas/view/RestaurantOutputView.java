package christmas.view;

import christmas.Bill;
import christmas.CalendarDiscount;
import christmas.DiscountNames;
import christmas.model.menu.Menu;

import java.text.DecimalFormat;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class RestaurantOutputView {
    private static final String BLANK = " ";
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final String EACH_PRINT = "개";
    private static final String WON_PRINT = "원";
    private static final String START_RESTAURANT = "안녕하세요! 우테코 식당 12월 이벤트 플래너입니다.";
    private static final String BENEFITS_PREVIEW = "12월 26일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!" + LINE_SEPARATOR;
    private static final String ORDER_MENU = "<주문 메뉴>";
    private static final String NO_DISCOUNT_PRINT = "<할인 전 총주문 금액>";
    private static final String TEN_THOUSAND_UNDER_PRINT = """
                
                <증정 메뉴>
                없음
                \s
                <혜택 내역>
                없음
                \s
                <총혜택 금액>
                0원
                \s
                <할인 후 예상 결제 금액>
                %s%s
                \s
                <12월 이벤트 배지>
                없음
                """;
    private static final int TEN_THOUSAND = 10000;
    private static final int CHAMPAGNE = 120000;
    private final DecimalFormat decimalFormat = new DecimalFormat("###,###");
    private Bill arrivedBill;
    private CalendarDiscount calendarDiscount;
    private boolean tenThousandOver;
    private boolean isChampagne;
    private int noDiscountMoney;

    public void startPrint() {
        System.out.println(START_RESTAURANT);
    }

    public void benefitsPreviewPrint() {
        System.out.println(BENEFITS_PREVIEW);
    }

    public void orderMenuPrint(Bill arrivedBill, CalendarDiscount calendarDiscount) {
        this.arrivedBill = arrivedBill;
        System.out.println(ORDER_MENU);
        System.out.println(menus());
        System.out.println(NO_DISCOUNT_PRINT);
        System.out.println(decimalFormat.format(noDiscountMoney()) + WON_PRINT);
        if (tenThousandOver) {
            this.calendarDiscount = calendarDiscount;
            discountPrint();
            return;
        }
        noDiscountPrint();
        //사실상여기가마지막
        System.out.println();
    }

    private void noDiscountPrint() {
        System.out.println(String.format(
                TEN_THOUSAND_UNDER_PRINT,
                decimalFormat.format(noDiscountMoney),
                WON_PRINT
        ));
    }

    private void discountPrint() {
        int discountMoney = 0;
        Map<DiscountNames, Boolean> discountPermits = calendarDiscount.createDiscountPermits();
        for(DiscountNames discountName : DiscountNames.values()){

        }
        System.out.println();
    }

    private void weekDiscount(boolean isWeekend){
        if(isWeekend){
//            "메인";
            return;
        }
//        "디저트";
    }

    public void isChampagne(int noDiscountMoney) {
        if(noDiscountMoney >= CHAMPAGNE) {
            this.isChampagne = true;
            return;
        }
        this.isChampagne = false;
    }

    private int noDiscountMoney() {
        List<Menu> pickedMenus = arrivedBill.getPickedMenus();
        EnumMap<Menu, Integer> orderSheet = arrivedBill.getOrderSheet();

        int noDiscountMoney = pickedMenus.stream()
                .mapToInt(menu ->
                        menu.menuPrice() * orderSheet.get(menu)
                )
                .sum();

        this.noDiscountMoney = noDiscountMoney;
        isAvailableDiscount(noDiscountMoney);
        isChampagne(noDiscountMoney);

        return noDiscountMoney;
    }

    private void isAvailableDiscount(int noDiscountMoney) {
        if (noDiscountMoney >= TEN_THOUSAND) {
            this.tenThousandOver = true;
            return;
        }
        this.tenThousandOver = false;
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
