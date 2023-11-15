package christmas.view;

import christmas.Bill;
import christmas.CalendarDiscount;
import christmas.DiscountNames;
import christmas.model.menu.Menu;
import christmas.model.menu.MenuType;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.List;
import java.util.EnumMap;

public class RestaurantOutputView {
    private static final String BLANK = " ";
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final String EACH_PRINT = "개";
    private static final String WON_PRINT = "원";
    private static final String STAR = "별";
    private static final String TREE = "트리";
    private static final String SANTA = "산타";
    private static final String NOTTING = "없음";
    private static final String START_RESTAURANT = "안녕하세요! 우테코 식당 12월 이벤트 플래너입니다.";
    private static final String BENEFITS_PREVIEW = "12월 %d일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!" + LINE_SEPARATOR;
    private static final String ORDER_MENU = "<주문 메뉴>";
    private static final String NO_DISCOUNT_PRINT = "<할인 전 총주문 금액>";
    private static final String PRINT_PERMITS = LINE_SEPARATOR + "<혜택 내역>";
    private static final String GIVE_MENU_NOTHING = "<증정 메뉴>" + LINE_SEPARATOR + "없음";
    private static final String BENEFITS_NOTHING = "<혜택 내역>" + LINE_SEPARATOR + "없음";
    private static final String TOTAL_BENEFITS_NOTHING = "<총혜택 금액>" + LINE_SEPARATOR + "0원";
    private static final String EVENT_BADGE_NOTHING = "<12월 이벤트 배지>" + LINE_SEPARATOR + "없음";
    private static final String TEN_THOUSAND_UNDER_PRINT = """

            %s

            %s

            %s

            <할인 후 예상 결제 금액>
            %s%s

            %s
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
    private static final String BENEFIT_PRICE = LINE_SEPARATOR + LINE_SEPARATOR + """
            <총혜택 금액>
            -%s원
            """;
    private static final String DISCOUNT_TOTAL_PRICE = """
            <할인 후 예상 결제 금액>
            %s원
            """;
    private static final String BADGE_PRINT = """
            <12월 이벤트 배지>
            %s
            """;
    private static final String EMPTY = "";
    private static final int WEEK_DISCOUNT = 2023;
    private static final int TEN_THOUSAND = 10000;
    private static final int THOUSAND = 1000;
    private static final int CHAMPAGNE = 120000;
    private static final int ZERO = 0;
    private static final int CHAMPAGNE_DISCOUNT = 25000;
    private static final int BENEFIT_FIVE_THOUSAND = 5000;
    private static final int BENEFIT_TEN_THOUSAND = 10000;
    private static final int BENEFIT_TWENTY_THOUSAND = 20000;
    private final DecimalFormat decimalFormat = new DecimalFormat("###,###");
    private Bill arrivedBill;
    private CalendarDiscount calendarDiscount;
    private boolean tenThousandOver;
    private boolean isChampagne;
    private int prevDiscountMoney;
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
            finalPrint();
            return;
        }
        noDiscountPrint();
    }

    private void finalPrint() {
        System.out.println(String.format(BENEFIT_PRICE, decimalFormat.format(discountMoney)));
        totalDiscountPrint();
        badgePrint();
    }

    private void totalDiscountPrint() {
        if (isChampagne) {
            System.out.println(String.format(DISCOUNT_TOTAL_PRICE, decimalFormat.format(prevDiscountMoney - discountMoney + CHAMPAGNE_DISCOUNT)));
            return;
        }
        System.out.println(String.format(DISCOUNT_TOTAL_PRICE, decimalFormat.format(prevDiscountMoney - discountMoney)));
    }

    private void badgePrint() {
        if (discountMoney >= BENEFIT_TWENTY_THOUSAND) {
            System.out.println(String.format(BADGE_PRINT, SANTA));
            return;
        }
        if (discountMoney >= BENEFIT_TEN_THOUSAND) {
            System.out.println(String.format(BADGE_PRINT, TREE));
            return;
        }
        if (discountMoney >= BENEFIT_FIVE_THOUSAND) {
            System.out.println(String.format(BADGE_PRINT, STAR));
            return;
        }

        System.out.println(String.format(BADGE_PRINT, NOTTING));
    }

    private void discountPrevPricePrint() {
        System.out.println(ORDER_MENU);
        System.out.println(menus());
        System.out.println(NO_DISCOUNT_PRINT);
        System.out.println(decimalFormat.format(prevDiscountMoney()) + WON_PRINT);
    }

    private void noDiscountPrint() {
        System.out.println(
                String.format(
                        TEN_THOUSAND_UNDER_PRINT,
                        GIVE_MENU_NOTHING,
                        TOTAL_BENEFITS_NOTHING,
                        BENEFITS_NOTHING,
                        decimalFormat.format(prevDiscountMoney), WON_PRINT,
                        EVENT_BADGE_NOTHING)
        );
    }

    private void discountPrint() {
        Queue<String> discountPermitPrinter = new LinkedList<>();
        discountPermitPrinter.add(PRINT_PERMITS);
        Map<DiscountNames, Boolean> discountPermits = calendarDiscount.createDiscountPermits();
        Arrays.stream(DiscountNames.values()).filter(discountPermits::containsKey).forEach(discountName -> discountPermitPrinter.add(discountMode(discountName, discountPermits.get(discountName))));
        if (isChampagne) {
            discountPermitPrinter.add(GIVE_CHAMPAGNE);
        }
        discountPermitPrinter.forEach(System.out::print);
    }

    /**
     * true : 주말
     * false : 평일
     */
    private String discountMode(DiscountNames discountName, boolean mode) {
        if (discountName.equals(DiscountNames.STAR) && mode) {
            discountMoney += THOUSAND;
            return String.format(STAR_DISCOUNT, decimalFormat.format(THOUSAND));
        }
        if (discountName.equals(DiscountNames.DAYS)) {
            return weekDiscountPrint(mode);
        }
        if (discountName.equals(DiscountNames.CHRISTMAS) && mode) {
            int christmasDiscountMoney = calendarDiscount.christmasDDayDiscount();
            discountMoney += christmasDiscountMoney;
            return String.format(CHRISTMAS_DISCOUNT, decimalFormat.format(christmasDiscountMoney));
        }
        return EMPTY;
    }

    private String weekDiscountPrint(boolean isWeekend) {
        MenuType menuType;
        if (isWeekend) {
            menuType = MenuType.메인;
            int weekDiscountMoney = plusDiscountMoney(weekDiscountMoney(menuType));
            return emptyDiscountMoney(weekDiscountMoney, menuType);
        }
        menuType = MenuType.디저트;
        int weekDiscountMoney = plusDiscountMoney(weekDiscountMoney(menuType));
        return emptyDiscountMoney(weekDiscountMoney, menuType);
    }

    private String emptyDiscountMoney(int weekDiscountMoney, MenuType menuType) {
        if (weekDiscountMoney == ZERO) {
            return EMPTY;
        }
        return String.format(WEEKEND_DISCOUNT, decimalFormat.format(weekDiscountMoney(menuType)));
    }

    private int plusDiscountMoney(int weekDiscountMoney) {
        discountMoney += weekDiscountMoney;
        return weekDiscountMoney;
    }

    private int weekDiscountMoney(MenuType menuType) {
        EnumMap<Menu, Integer> orderSheet = arrivedBill.getOrderSheet();
        int discountMoney = arrivedBill.getPickedMenus().stream()
                .filter(menu ->
                        menuType.menusContain(String.valueOf(menu))
                )
                .mapToInt(menu -> orderSheet.get(menu) * WEEK_DISCOUNT)
                .sum();
        return discountMoney;
    }

    public void isChampagne(int noDiscountMoney) {
        if (noDiscountMoney >= CHAMPAGNE) {
            this.isChampagne = true;
            this.discountMoney += CHAMPAGNE_DISCOUNT;
            return;
        }
        this.isChampagne = false;
    }

    private int prevDiscountMoney() {
        List<Menu> pickedMenus = arrivedBill.getPickedMenus();
        EnumMap<Menu, Integer> orderSheet = arrivedBill.getOrderSheet();

        int prevDiscountMoney = pickedMenus.stream()
                .mapToInt(menu -> menu.menuPrice() * orderSheet.get(menu)).sum();
        this.prevDiscountMoney = prevDiscountMoney;
        isAvailableDiscount(prevDiscountMoney);
        isChampagne(prevDiscountMoney);
        return prevDiscountMoney;
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

        arrivedBill.getPickedMenus().forEach(menu -> orderMenuBuilder.append(menu)
                .append(BLANK)
                .append(arrivedBill.getOrderSheet().get(menu))
                .append(EACH_PRINT)
                .append(LINE_SEPARATOR));
        return orderMenuBuilder.toString();
    }
}
