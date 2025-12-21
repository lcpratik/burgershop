import java.util.ArrayList;
import java.util.List;

// Represents a customer's order, which contains burgers, their
// customizations, and an optional discount.

public class Order {
    private ArrayList<Burger> burgers;
    private ArrayList<ArrayList<Customization>> burgerCustomizations;
    private Discount discount;

    public Order() {
        burgers = new ArrayList<>();
        burgerCustomizations = new ArrayList<>();
        discount = null;
    }

    // Adds a burger to the order and returns its index
    public int addBurger(Burger burger) {
        burgers.add(burger);
        burgerCustomizations.add(new ArrayList<Customization>());
        return burgers.size() - 1;
    }

    public void addCustomizationToBurger(int index, Customization customization) {
        if (index >= 0 && index < burgerCustomizations.size()) {
            burgerCustomizations.get(index).add(customization);
        }
    }

    public void removeBurger(int index) {
        if (index >= 0 && index < burgers.size()) {
            burgers.remove(index);
            burgerCustomizations.remove(index);
        }
    }

    public void clear() {
        burgers.clear();
        burgerCustomizations.clear();
        discount = null;
    }

    public List<Burger> getBurgers() {
        return burgers;
    }

    public List<Customization> getCustomizationsForBurger(int index) {
        if (index >= 0 && index < burgerCustomizations.size()) {
            return burgerCustomizations.get(index);
        }
        // return empty list if invalid index
        return new ArrayList<Customization>();
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    public Discount getDiscount() {
        return discount;
    }

    // Price of one burger + its customizations
    public double calculateBurgerTotal(int index) {
        if (index < 0 || index >= burgers.size()) {
            return 0.0;
        }
        double total = burgers.get(index).getBasePrice();
        for (Customization c : burgerCustomizations.get(index)) {
            total += c.getExtraCost();
        }
        return total;
    }

    // Sum of all burgers (with customizations), before discount
    public double calculateSubtotal() {
        double subtotal = 0.0;
        for (int i = 0; i < burgers.size(); i++) {
            subtotal += calculateBurgerTotal(i);
        }
        return subtotal;
    }

    // Final total after discount (if any)
    public double calculateTotal() {
        double subtotal = calculateSubtotal();
        if (discount != null) {
            return discount.apply(subtotal);
        }
        return subtotal;
    }
}
