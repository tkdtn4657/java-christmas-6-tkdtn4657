package christmas;

import christmas.model.menu.Menu;

import java.util.EnumMap;
import java.util.List;

public class Bill {

    private final EnumMap<Menu, Integer> orderSheet;
    private final List<Menu> pickedMenus;
    public Bill(EnumMap<Menu, Integer> orderSheet, List<Menu> pickedMenus){
        this.orderSheet = orderSheet;
        this.pickedMenus = pickedMenus;
    }

    public List<Menu> getPickedMenus() {
        return pickedMenus;
    }

    public EnumMap<Menu, Integer> getOrderSheet() {
        return orderSheet;
    }
}
