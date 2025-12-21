// Represents a single customization option for a burger
// (e.g., extra cheese, bacon)

public class Customization {
    private String name;
    private double extraCost;

    public Customization(String name, double extraCost) {
        this.name = name;
        this.extraCost = extraCost;
    }

    public String getName() {
        return name;
    }

    public double getExtraCost() {
        return extraCost;
    }

    @Override
    public String toString() {
        if (extraCost == 0.0) {
            return name;
        }
        return name + " (+" + String.format("%.2f", extraCost) + ")";
    }
}
