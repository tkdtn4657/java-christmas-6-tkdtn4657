package christmas.model;

import christmas.model.menu.Menu;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.StringTokenizer;

public class Restaurant {
    private static final String HYPHEN = "-";
    private static final int MENU_EACH_MIN = 1;
    private static final int MENU_EACH_MAX = 20;
    private final EnumMap<Menu, Integer> orderSheet = new EnumMap(Menu.class);
    private List<Menu> pickedMenus;

    public void menuReceive(List<String> splitLine) {
        for(String menu : splitLine){
            List<String> menuForm = splitMenu(menu);
            orderSheet.put(
                    Menu.valueOf(menuForm.get(0)),
                    Integer.parseInt(menuForm.get(1))
            );
        }

        pickedMenus = Arrays.stream(Menu.values())
                .filter(menu -> orderSheet.getOrDefault(menu, 0) >= 1)
                .toList();
    }

    private List<String> splitMenu(String orders){
        StringTokenizer order = new StringTokenizer(orders, HYPHEN);
        return List.of(order.nextToken(), order.nextToken());
    }

    public boolean orderValidate(){
        try{
            menuValidate();
            isOnlyDrinks();
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }


    public void menuValidate() throws IllegalArgumentException{
        int orderEachCount = Arrays.stream(Menu.values())
            .mapToInt(menu -> orderSheet.getOrDefault(menu, 0))
            .reduce(0, Integer::sum);

        if(orderEachCount < MENU_EACH_MIN || orderEachCount > MENU_EACH_MAX){
            throw new IllegalArgumentException("[ERROR] 메뉴문제");
        }
    }

    public void isOnlyDrinks() throws IllegalArgumentException{
        if(Menu.isOnlyDrinks(pickedMenus, orderSheet)){
            throw new IllegalArgumentException("[ERROR] 음료만있음");
        }
    }
}
