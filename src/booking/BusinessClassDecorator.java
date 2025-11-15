package booking;

public class BusinessClassDecorator extends BookingDecorator {

    private static final double BUSINESS_SURCHARGE = 300.0;

    public BusinessClassDecorator(Booking wrapped) {
        super(wrapped);
    }

    @Override
    public String getDescription() {
        return wrapped.getDescription() + " + Business Class";
    }

    @Override
    public double getPrice() {
        return wrapped.getPrice() + BUSINESS_SURCHARGE;
    }
}
