package christmas.validator;


import christmas.view.RestaurantInputView;

import java.util.List;
import java.util.function.Consumer;

public class VisitConvertor {
    private static final String IS_INTEGER_REGEX = "^[\\d]*$";
    RestaurantInputView restaurantInputView;


    private final List<Consumer<String>> visitFilters = List.of(
            this::isInteger,
            this::rangeCorrect,
            String::isEmpty,
            String::isBlank
    );

    public int visitConvertor(String visitLine) {
        int convertedLine = 0;
        visitValidator(visitLine);
        return convertedLine;
    }

    private boolean visitValidator(String visitLine) {
        try {
            visitFilters.forEach(
                    filter -> filter.accept(visitLine));

            return true;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return false;
        }

    }

    private void isInteger(String visitLine) {
    }

    private void rangeCorrect(String visitLine) {
    }

}