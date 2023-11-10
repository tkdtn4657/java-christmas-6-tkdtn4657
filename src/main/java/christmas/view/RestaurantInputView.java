package christmas.view;

import camp.nextstep.edu.missionutils.Console;
import christmas.validator.VisitConvertor;

public class RestaurantInputView {

    private static final String INPUT_VISIT_DAY = "12월 중 식당 예상 방문 날짜는 언제인가요? (숫자만 입력해 주세요!)";
    private static final String INPUT_ORDER = "주문하실 메뉴를 메뉴와 개수를 알려 주세요. (e.g. 해산물파스타-2,레드와인-1,초코케이크-1)";

    private final VisitConvertor visitConvertor;
    public RestaurantInputView(VisitConvertor restaurantValidator){
        this.visitConvertor = restaurantValidator;
    }

    public String visitInput() {
        System.out.println(INPUT_VISIT_DAY);
        String visitLine = Console.readLine();

//        if(visitConvertor.visitValidate(visitLine)){
//
//        }

        return visitLine;
    }

    public String orderInput() {
        System.out.println(INPUT_ORDER);
        String orderLine = Console.readLine();
        return orderLine;
    }
}
