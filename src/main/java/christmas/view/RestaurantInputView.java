package christmas.view;

import camp.nextstep.edu.missionutils.Console;
import christmas.convertor.VisitConvertor;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class RestaurantInputView {

    private static final String INPUT_VISIT_DAY = "12월 중 식당 예상 방문 날짜는 언제인가요? (숫자만 입력해 주세요!)";
    private static final String INPUT_ORDER = "주문하실 메뉴를 메뉴와 개수를 알려 주세요. (e.g. 해산물파스타-2,레드와인-1,초코케이크-1)";
    private static final String REGEX_COMMA = ",";

    private final VisitConvertor visitConvertor;

    public RestaurantInputView(VisitConvertor visitConvertor){
        this.visitConvertor = visitConvertor;
    }

    public Optional<Integer> visitInput() {
        System.out.println(INPUT_VISIT_DAY);
        String visitLine = Console.readLine();

        try {
            visitConvertor.visitValidate(visitLine);
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
            return Optional.empty();
        }
        return Optional.of(Integer.parseInt(visitLine));
    }

    public List<String> orderInput() {
        System.out.println(INPUT_ORDER);
        String inputLine = Console.readLine();
        return Arrays.stream(inputLine.split(REGEX_COMMA)).toList();
    }
}
