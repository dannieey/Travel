package transport;

public class Car implements Transport {
    private double basePrice;

    public Car(double basePrice) {
        this.basePrice = basePrice;
    }

    @Override
    public String getName() {
        return "Car";
    }

    @Override
    public double getBasePrice() {
        return basePrice;
    }


}
