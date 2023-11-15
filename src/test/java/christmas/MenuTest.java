package christmas;

import christmas.menu.Menu;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.EnumMap;
import java.util.List;

class MenuTest {

    @Test
    void isOnlyDrinks() {
        List<Menu> pickedMenus = List.of(Menu.샴페인, Menu.제로콜라, Menu.레드와인);

        Assertions.assertThat(Menu.isOnlyDrinks(
                        pickedMenus,
                        onlyDrinkEnumMapCreator()
                ))
                .isTrue();
    }

    @Test
    void isNotOnlyDrinks() {
        List<Menu> pickedMenus = List.of(Menu.샴페인, Menu.바비큐립, Menu.크리스마스파스타);

        Assertions.assertThat(Menu.isOnlyDrinks(
                        pickedMenus,
                        notOnlyDrinkEnumMapCreator()
                ))
                .isFalse();
    }

    EnumMap<Menu, Integer> onlyDrinkEnumMapCreator() {
        EnumMap<Menu, Integer> orderSheet = new EnumMap<Menu, Integer>(Menu.class);
        orderSheet.put(Menu.샴페인, 1);
        orderSheet.put(Menu.제로콜라, 1);
        orderSheet.put(Menu.레드와인, 1);

        return orderSheet;
    }

    EnumMap<Menu, Integer> notOnlyDrinkEnumMapCreator() {
        EnumMap<Menu, Integer> orderSheet = new EnumMap<Menu, Integer>(Menu.class);
        orderSheet.put(Menu.샴페인, 1);
        orderSheet.put(Menu.바비큐립, 1);
        orderSheet.put(Menu.크리스마스파스타, 1);

        return orderSheet;
    }


}