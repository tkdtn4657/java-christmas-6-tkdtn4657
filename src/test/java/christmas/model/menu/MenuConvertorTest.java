package christmas.model.menu;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MenuConvertorTest {

    private final MenuConvertor menuConvertor = new MenuConvertor();
    private final List<String> menus = Arrays.stream(Menu.values()).map(String::valueOf).toList();
    @Test
    void validate() {
        List<String> testLines = List.of("티본스테이크-1");

        assertTrue(menuConvertor.validate(testLines));
    }

    @Test
    void regexTest(){
        String MENU_REGEX = "^.*-[0-9]+$";
        String tibon = "티본스테이크--11";

        assertTrue(tibon.matches(MENU_REGEX));
    }

    @Test
    void menuContain(){
        List<String> testLines = List.of("티본스테이크-1");
        List<String> menuSplitLine = testLines.stream()
                .map((line) -> line.split("-")[0])
                .toList();

        assertTrue(menus.contains(menuSplitLine.get(0)));
    }

}