package christmas.view;

import christmas.Bill;
import christmas.CalendarDiscount;
import christmas.DiscountNames;
import christmas.model.menu.Menu;
import christmas.model.menu.MenuType;

import java.text.DecimalFormat;
import java.util.*;

public class RestaurantOutputView {
    private static final String BLANK = " ";
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final String EACH_PRINT = "개";
    private static final String WON_PRINT = "원";
    private static final String START_RESTAURANT = "안녕하세요! 우테코 식당 12월 이벤트 플래너입니다.";
    private static final String BENEFITS_PREVIEW = "12월 %d일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!" + LINE_SEPARATOR;
    private static final String ORDER_MENU = "<주문 메뉴>";
    private static final String NO_DISCOUNT_PRINT = "<할인 전 총주문 금액>";
    private static final String PRINT_PERMITS = LINE_SEPARATOR + "<혜택 내역>";
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
    private static final String CHRISTMAS_DISCOUNT = LINE_SEPARATOR + "크리스마스 디데이 할인: -%s원";
    private static final String WEEKDAY_DISCOUNT = LINE_SEPARATOR + "평일 할인: -%s원";
    private static final String WEEKEND_DISCOUNT = LINE_SEPARATOR + "주말 할인: -%s원";
    private static final String STAR_DISCOUNT = LINE_SEPARATOR + "특별 할인: -%s원";
    private static final String GIVE_CHAMPAGNE = LINE_SEPARATOR + "증정 이벤트: -25,000원";
    private static final String PRINT_CHAMPAGNE = """
            <증정 메뉴>
            샴페인 1개
            """;
    private static final String EMPTY = "";
    private static final int WEEK_DISCOUNT = 2023;
    private static final int TEN_THOUSAND = 10000;
    private static final int THOUSAND = 1000;
    private static final int CHAMPAGNE = 120000;
    private static final int ZERO = 0;
    private final DecimalFormat decimalFormat = new DecimalFormat("###,###");
    private Bill arrivedBill;
    private CalendarDiscount calendarDiscount;
    private boolean tenThousandOver;
    private boolean isChampagne;
    private int noDiscountMoney;
    private int discountMoney;

    public void startPrint() {
        System.out.println(START_RESTAURANT);
    }

    public void benefitsPreviewPrint(int nowDay) {
        System.out.println(String.format(BENEFITS_PREVIEW, nowDay));
    }

    public void orderMenuPrint(Bill arrivedBill, CalendarDiscount calendarDiscount) {
        discountMoney = 0;
        this.arrivedBill = arrivedBill;
        discountPrevPricePrint();

        if (isChampagne) {
            System.out.print(LINE_SEPARATOR + PRINT_CHAMPAGNE);
        }

        if (tenThousandOver) {
            this.calendarDiscount = calendarDiscount;
            discountPrint();
            return;
        }
        noDiscountPrint();
    }

    private void discountPrevPricePrint() {
        System.out.println(ORDER_MENU);
        System.out.println(menus());
        System.out.println(NO_DISCOUNT_PRINT);
        System.out.println(decimalFormat.format(noDiscountMoney()) + WON_PRINT);
    }

    private void noDiscountPrint() {
        System.out.println(
                String.format(
                        TEN_THOUSAND_UNDER_PRINT,
                        decimalFormat.format(noDiscountMoney),
                        WON_PRINT
                ));
    }

    private void discountPrint() {
        Queue<String> discountPermitPrinter = new LinkedList<>();
        discountPermitPrinter.add(PRINT_PERMITS);
        Map<DiscountNames, Boolean> discountPermits = calendarDiscount.createDiscountPermits();
        Arrays.stream(DiscountNames.values())
                .filter(discountPermits::containsKey)
                .forEach(discountName ->
                        discountPermitPrinter.add(discountMode(discountName, discountPermits.get(discountName)))
                );
        if (isChampagne) {
            discountPermitPrinter.add(GIVE_CHAMPAGNE);
        }
        discountPermitPrinter.forEach(System.out::print);
    }

    /**
     * true면 주말
     */
    private String discountMode(DiscountNames discountName, boolean mode) {

        if (discountName.equals(DiscountNames.STAR) && mode) {
            return String.format(STAR_DISCOUNT, decimalFormat.format(THOUSAND));
        }
        if (discountName.equals(DiscountNames.DAYS)) {
            return weekDiscountPrint(mode);
        }
        if (discountName.equals(DiscountNames.CHRISTMAS) && mode) {
            return String.format(CHRISTMAS_DISCOUNT, decimalFormat.format(calendarDiscount.christmasDDayDiscount()));
        }
        return EMPTY;
    }

    private String weekDiscountPrint(boolean isWeekend) {
        MenuType menuType;
        if (isWeekend) {
            menuType = MenuType.메인;
            int discountMoney = weekDiscountMoney(menuType);
            if (discountMoney == ZERO) {
                return EMPTY;
            }
            return String.format(
                    WEEKEND_DISCOUNT,
                    decimalFormat.format(weekDiscountMoney(menuType)));
        }
        menuType = MenuType.디저트;
        int discountMoney = weekDiscountMoney(menuType);
        if (discountMoney == ZERO) {
            return EMPTY;
        }
        return String.format(
                WEEKDAY_DISCOUNT,
                decimalFormat.format(discountMoney));
    }

    private int weekDiscountMoney(MenuType menuType) {
        EnumMap orderSheet = arrivedBill.getOrderSheet();
        int discountMoney = arrivedBill.getPickedMenus()
                .stream()
                .filter(menu -> menuType.menusContain(String.valueOf(menu)))
                .mapToInt(menu -> (int) orderSheet.get(menu) * WEEK_DISCOUNT)
                .sum();
        return discountMoney;
    }

    public void isChampagne(int noDiscountMoney) {
        if (noDiscountMoney >= CHAMPAGNE) {
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
