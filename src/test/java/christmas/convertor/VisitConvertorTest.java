package christmas.convertor;

import camp.nextstep.edu.missionutils.test.NsTest;
import christmas.Application;
import org.junit.jupiter.api.Test;

import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;

class VisitConvertorTest extends NsTest {

    @Test
    void 날짜_예외_테스트() {
        assertSimpleTest(() -> {
            runException("33");
            assertThat(output()).contains("[ERROR] 유효하지 않은 날짜입니다. 다시 입력해 주세요.");
        });
    }

    @Test
    void 날짜_예외_테스트1() {
        assertSimpleTest(() -> {
            runException("0");
            assertThat(output()).contains("[ERROR] 유효하지 않은 날짜입니다. 다시 입력해 주세요.");
        });
    }

    @Test
    void 날짜_예외_테스트2() {
        assertSimpleTest(() -> {
            runException(" ");
            assertThat(output()).contains("[ERROR] 값이 입력되지 않았습니다.");
        });
    }



    @Override
    protected void runMain() {
        Application.main(new String[]{});
    }
}
