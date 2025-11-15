package transport;

public class Cruise implements Transport {
    private double basePrice;

    public Cruise(double basePrice) {

        this.basePrice = basePrice;
    }

    @Override
    public String getName() {

        return "Cruise";
    }

    @Override
    public double getBasePrice() {

        return basePrice;
    }


}
