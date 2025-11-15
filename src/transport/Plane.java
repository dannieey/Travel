package transport;

public class Plane implements Transport {
    private double basePrice;

    public Plane(double basePrice) {

        this.basePrice = basePrice;
    }

    @Override
    public String getName() {

        return "Plane";
    }

    @Override
    public double getBasePrice() {

        return basePrice;
    }


}
