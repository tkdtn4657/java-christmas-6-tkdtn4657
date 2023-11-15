package christmas.model.menu;

import java.util.List;

public enum MenuType {
    애피타이저(List.of("양송이수프", "타파스", "시저샐러드")),
    메인(List.of("티본스테이크", "바비큐립", "해산물파스타", "크리스마스파스타")),
    디저트(List.of("초코케이크", "아이스크림")),
    음료(List.of("제로콜라", "레드와인", "샴페인"));

    private final List<String> menus;

    MenuType(List<String> menus) {
        this.menus = menus;
    }

    public boolean menusContain(String name) {
        return menus.contains(name);
    }
}
