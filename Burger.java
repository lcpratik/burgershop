// CSE 1325 Final Project - Burger Shop
// Simple burger model class

public class Burger {
    private String name;
    private double basePrice;
    private String description;

    public Burger(String name, double basePrice, String description) {
        this.name = name;
        this.basePrice = basePrice;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public String getDescription() {
        return description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return name + " - $" + String.format("%.2f", basePrice);
    }
}
