package christmas;

import java.util.*;

public class CalendarDiscount {
    private static final int MONTH_END = 31;
    private static final int ONE_WEEK = 7;
    private static final int DAY_START = 1;
    private static final int ONE = 1;
    private static final int START_DAY_START = 3;
    private static final int CHRISTMAS_DAY = 25;
    private static final int FIRST_DISCOUNT_MONEY = 1000;
    private static final int INCREASE_DISCOUNT_PER_DAYS = 100;
    private static final boolean DISCOUNT_YES = true;
    private static final boolean DISCOUNT_NO = false;
    private static final boolean DISCOUNT_WEEKEND = true;
    private static final boolean DISCOUNT_WEEKDAY = false;
    private final List<Integer> starDay;
    private final List<Integer> weekendDay;
    private final int nowDay;

    public CalendarDiscount(int nowDay) {
        this.weekendDay = weekendAdder();
        this.starDay = starDayAdder();
        this.nowDay = nowDay;
    }

    private List<Integer> weekendAdder() {
        List<Integer> weekends = new ArrayList<>();
        for (int day = DAY_START; day <= MONTH_END; day += ONE_WEEK) {
            weekends.add(day);
            weekends.add(day + ONE);
        }
        return weekends;
    }

    private List<Integer> starDayAdder() {
        List<Integer> starDays = new ArrayList<>();
        for (int day = START_DAY_START; day <= MONTH_END; day += ONE_WEEK) {
            starDays.add(day);
        }
        starDays.add(CHRISTMAS_DAY);
        return starDays;
    }


    /**
     * List<Map<할인혜택 종류, 허용>>
     * 주말 : true
     * 평일 : false
     */
    public Map<DiscountNames, Boolean> createDiscountPermits() {
        Map<DiscountNames, Boolean> discountPermits = new LinkedHashMap<>();
        if (isChristmasPrevDays()) {
            discountPermits.put(DiscountNames.CHRISTMAS, DISCOUNT_YES);
        }
        if (isWeekend()) {
            discountPermits.put(DiscountNames.DAYS, DISCOUNT_WEEKEND);
        }
        if(!isWeekend()){
            discountPermits.put(DiscountNames.DAYS, DISCOUNT_WEEKDAY);
        }
        if (isStaredDay()) {
            discountPermits.put(DiscountNames.STAR, DISCOUNT_YES);
        }
        return discountPermits;
    }

    public int christmasDDayDiscount() {
        return FIRST_DISCOUNT_MONEY + (nowDay - ONE) * INCREASE_DISCOUNT_PER_DAYS;
    }

    private boolean isStaredDay() {
        return starDay.contains(nowDay);
    }

    private boolean isWeekend() {
        return weekendDay.contains(nowDay);
    }

    private boolean isChristmasPrevDays() {
        return nowDay <= CHRISTMAS_DAY;
    }

    public int getDay() {
        return nowDay;
    }
}
