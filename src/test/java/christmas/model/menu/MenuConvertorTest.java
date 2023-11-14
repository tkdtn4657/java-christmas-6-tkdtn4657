package christmas.model.menu;

import camp.nextstep.edu.missionutils.test.NsTest;
import christmas.Application;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MenuConvertorTest extends NsTest {
    private static final String ERROR_MESSAGE = "[ERROR]";
    private static final String HYPHEN = "-";
    private final MenuConvertor menuConvertor = new MenuConvertor();
    private final List<String> menus = Arrays.stream(Menu.values())
            .map(String::valueOf)
            .toList();
    @Test
    void validate() {
        List<String> testLines = List.of("티본스테이크-1");

        assertTrue(menuConvertor.inputValidate(testLines));
    }
    @Test
    void 중복_메뉴_테스트() {
        assertSimpleTest(() -> {
            runException("1", "티본스테이크-1,티본스테이크-1");
            assertThat(output()).contains(ERROR_MESSAGE);
        });
    }

    @Test
    void 메뉴에_없는_음식() {
        assertSimpleTest(() -> {
            runException("1", "티본스테이크-1,파스타-1");
            assertThat(output()).contains(ERROR_MESSAGE);
        });
    }

    @Test
    void 메뉴_입력형태_검증_파스타1() {
        assertSimpleTest(() -> {
            runException("1", "파스타1");
            assertThat(output()).contains(ERROR_MESSAGE);
        });
    }

    @Test
    void 메뉴_입력형태_검증_티본() {
        assertSimpleTest(() -> {
            runException("1", "티본");
            assertThat(output()).contains(ERROR_MESSAGE);
        });
    }

    @Test
    void 메뉴_입력형태_검증_티본스테이크_111(){
        String MENU_REGEX = ".*-\\d{1,2}$";
        String tibon = "티본스테이크-111";

        assertFalse(tibon.matches(MENU_REGEX));
    }

    @Test
    void menuContain(){
        List<String> testLines = List.of("티본스테이크-1");
        List<String> menuSplitLine = testLines.stream()
                .map((line) -> line.split(HYPHEN)[0])
                .toList();

        assertTrue(menus.contains(menuSplitLine.get(0)));
    }

    @Override
    protected void runMain() {
        Application.main(new String[]{});
    }

}