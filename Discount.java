// Represents a discount that can be applied to an order
// SAVE10 is dicount code

public class Discount {
    private String name;
    private double percentOff; // e.g. 10.0 = 10% off

    public Discount(String name, double percentOff) {
        this.name = name;
        this.percentOff = percentOff;
    }

    public String getName() {
        return name;
    }

    public double getPercentOff() {
        return percentOff;
    }

    // Returns the new total after discount is applied
    public double apply(double amount) {
        return amount - (amount * (percentOff / 100.0));
    }

    @Override
    public String toString() {
        return name + " (" + String.format("%.0f", percentOff) + "% off)";
    }
}
