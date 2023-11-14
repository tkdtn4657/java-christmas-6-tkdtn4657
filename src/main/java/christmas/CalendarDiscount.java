package christmas;

import java.util.ArrayList;
import java.util.List;

public class CalendarDiscount {
    private static final int MONTH_END = 31;
    private static final int ONE_WEEK = 7;
    private static final int DAY_START = 1;
    private static final int ONE_DAY = 1;
    private static final int START_DAY_START = 3;
    private static final int CHRISTMAS_DAY = 25;

    private final List<Integer> starDay;
    private final List<Integer> weekendDay;
    private final int nowDay;

    CalendarDiscount(int nowDay) {
        this.weekendDay = weekendAdder();
        this.starDay = starDayAdder();
        this.nowDay = nowDay;
    }

    private List<Integer> weekendAdder() {
        List<Integer> weekends = new ArrayList<>();
        for (int day = DAY_START; day <= MONTH_END; day += ONE_WEEK) {
            weekends.add(day);
            weekends.add(day + ONE_DAY);
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
}
