package christmas;

import christmas.model.menu.Menu;

import java.util.EnumMap;
import java.util.List;

public record Bill(EnumMap<Menu, Integer> orderSheet, List<Menu> pickedMenus) {

}
