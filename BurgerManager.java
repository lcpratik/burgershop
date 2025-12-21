import java.util.ArrayList;
import java.util.List;

// Manages the burger menu for the shop

public class BurgerManager {
    private ArrayList<Burger> menu;

    public BurgerManager() {
        menu = new ArrayList<>();
        seedDefaultMenu();
    }

    // Initial burgers on the menu
    private void seedDefaultMenu() {
        addBurger(new Burger("Classic Burger", 6.99,
                "Beef patty, lettuce, tomato, onion, pickles"));
        addBurger(new Burger("Cheeseburger", 7.99,
                "Classic burger with cheddar cheese"));
        addBurger(new Burger("Veggie Burger", 7.49,
                "Grilled veggie patty with fresh veggies"));
        addBurger(new Burger("Bacon Burger", 8.49,
                "Beef patty with crispy bacon and cheese"));
    }

    public void addBurger(Burger burger) {
        menu.add(burger);
    }

    public List<Burger> getMenu() {
        return menu;
    }

    public Burger getBurger(int index) {
        if (index >= 0 && index < menu.size()) {
            return menu.get(index);
        }
        return null;
    }
}
