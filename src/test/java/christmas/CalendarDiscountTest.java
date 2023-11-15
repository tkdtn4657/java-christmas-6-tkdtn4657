package christmas;

import christmas.calendarDiscount.CalendarDiscount;
import christmas.calendarDiscount.DiscountNames;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Map;

class CalendarDiscountTest {
    CalendarDiscount calendarDiscount = new CalendarDiscount(25);

    /**
     * 25일 기준 할인혜택
     * 크리스마스, 평일, 별
     */
    @Test
    void 날짜에_맞는_할인혜택_검증() {
        StringBuilder discountNamesBuild = new StringBuilder();
        Map<DiscountNames, Boolean> discountPermits = calendarDiscount.createDiscountPermits();
        for (DiscountNames discountNames : discountPermits.keySet()) {
            if (discountPermits.containsKey(discountNames)) {
                discountNamesBuild.append(discountNames.name());
            }
        }
        List<String> names = List.of(
                String.valueOf(DiscountNames.CHRISTMAS),
                String.valueOf(DiscountNames.DAYS),
                String.valueOf(DiscountNames.STAR));
        Assertions.assertThat(discountNamesBuild.toString()).contains(names);
    }
}