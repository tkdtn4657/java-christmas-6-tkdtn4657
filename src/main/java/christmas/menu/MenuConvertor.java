package christmas.menu;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.function.Consumer;

public class MenuConvertor {

    private static final String ERROR_MESSAGE = "[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.";
    private static final String MENU_REGEX = ".*-\\d{1,2}$";
    private static final String HYPHEN = "-";

    private EnumMap<Menu, Integer> orderSheet = new EnumMap(Menu.class);

    private final List<String> menus = Arrays.stream(Menu.values())
            .map(String::valueOf)
            .toList();

    private final List<Consumer<List<String>>> visitFilters = List.of(
            this::menuFormValidate,
            this::availableMenu,
            this::duplicateMenu
    );

    public boolean inputValidate(List<String> splitLine) {
        try {
            visitFilters.forEach(
                    filter -> filter.accept(splitLine));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    private void menuFormValidate(List<String> splitLine) throws IllegalArgumentException {
        if (splitLine.stream()
                .filter((line) -> line.matches(MENU_REGEX))
                .toList()
                .size() != splitLine.size()) {
            throw new IllegalArgumentException(ERROR_MESSAGE);
        }
    }

    private void availableMenu(List<String> splitLine) throws IllegalArgumentException {
        List<String> menuSplitLine = splitLine.stream()
                .map((line) -> line.split(HYPHEN)[0])
                .toList();

        if (!menuSplitLine.stream()
                .allMatch(menus::contains)) {
            throw new IllegalArgumentException(ERROR_MESSAGE);
        }
    }

    private void duplicateMenu(List<String> splitLine) throws IllegalArgumentException {
        List<String> menuSplitLine = splitLine.stream()
                .map((line) -> line.split(HYPHEN)[0])
                .toList();

        if (menuSplitLine.stream()
                .distinct()
                .toList()
                .size() != splitLine.size()) {
            throw new IllegalArgumentException(ERROR_MESSAGE);
        }
    }
}
