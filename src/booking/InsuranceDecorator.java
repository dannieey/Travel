package booking;

public class InsuranceDecorator extends BookingDecorator {
    private static final double INSURANCE_COST = 40.0;

    public InsuranceDecorator(Booking wrapped) {
        super(wrapped);
    }

    @Override
    public String getDescription() {
        return wrapped.getDescription() + " + Insurance";
    }

    @Override
    public double getPrice() {
        return wrapped.getPrice() + INSURANCE_COST;
    }
}
