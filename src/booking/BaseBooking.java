package booking;



public class BaseBooking implements Booking {
    private String origin;
    private String destination;
    private String dateTo;
    private String dateBack;
    private double price;

    public BaseBooking() {
        this.price = 0;
    }

    public BaseBooking(String origin, String destination, String dateTo, String dateBack, double basePrice) {
        this.origin = origin;
        this.destination = destination;
        this.dateTo = dateTo;
        this.dateBack = dateBack;
        this.price = basePrice;
    }

    @Override
    public String getDescription() {
        return String.format("Booking from %s to %s (%s - %s)", origin, destination, dateTo, dateBack);
    }

    @Override
    public double getPrice() {
        return price;
    }

    public void setPrice(double p) {
        this.price = p;
    }
}
