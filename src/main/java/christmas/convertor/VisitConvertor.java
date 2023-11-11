package christmas.convertor;


import christmas.view.RestaurantInputView;

import java.util.List;
import java.util.function.Consumer;

public class VisitConvertor {
    private static final String IS_INTEGER_REGEX = "^\\d*$";
    private static final int MIN_RANGE_CHECK = 1;
    private static final int MAX_RANGE_CHECK = 31;




    private final List<Consumer<String>> visitFilters = List.of(
            this::isEmpty,
            this::isInteger,
            this::rangeCorrect
    );

    public void visitValidate(String visitLine) {
        visitFilters.forEach(
                filter -> filter.accept(visitLine));
    }

    private void isEmpty(String visitLine)throws IllegalArgumentException{
        if(visitLine.isBlank()){
            throw new IllegalArgumentException("[ERROR] 값이 입력되지 않았습니다.");
        }
    }

    private void isInteger(String visitLine) throws NumberFormatException{
        if(!visitLine.matches(IS_INTEGER_REGEX)){
            throw new NumberFormatException("[ERROR] 유효하지 않은 날짜입니다. 다시 입력해 주세요.");
        }
    }

    private void rangeCorrect(String visitLine) throws IllegalArgumentException{
        int validateLine = Integer.parseInt(visitLine);
        if(!(MIN_RANGE_CHECK <= validateLine && validateLine <= MAX_RANGE_CHECK)){
            throw new IllegalArgumentException("[ERROR] 유효하지 않은 날짜입니다. 다시 입력해 주세요.");
        }
    }



}