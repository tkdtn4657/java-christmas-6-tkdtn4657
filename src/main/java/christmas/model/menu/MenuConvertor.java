package christmas.model.menu;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class MenuConvertor {

    private static final String MENU_REGEX = "^.*-[0-9]";

    private final List<String> menus = Arrays.stream(Menu.values())
            .map(String::valueOf)
            .toList();

    private final List<Consumer<List<String>>> visitFilters = List.of(
            this::menuFormValidate,
            this::availableMenu,
            this::duplicateMenu
    );

    public boolean validate(List<String> splitLine){
        try {
            visitFilters.forEach(
                    filter -> filter.accept(splitLine));
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    private void menuFormValidate(List<String> splitLine){
        if(splitLine.stream()
                .filter((line)-> line.matches(MENU_REGEX))
                .toList()
                .size() != splitLine.size()){
            throw new IllegalArgumentException();
        }
    }

    private void availableMenu(List<String> splitLine){
        List<String> menuSplitLine = splitLine.stream()
                .map((line) -> line.split("-")[0])
                .toList();

        if(!menuSplitLine.stream()
                .allMatch(menus::contains)){
            throw new IllegalArgumentException();
        }
    }

    private void duplicateMenu(List<String> splitLine){
        List<String> menuSplitLine = splitLine.stream()
                .map((line) -> line.split("-")[0])
                .toList();

        if(menuSplitLine.stream()
                .distinct()
                .toList()
                .size() != splitLine.size()){
            throw new IllegalArgumentException();
        }
    }

}
