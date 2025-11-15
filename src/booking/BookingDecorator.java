package booking;

public abstract class BookingDecorator implements Booking {
    protected booking.Booking wrapped;

    public BookingDecorator(Booking wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public String getDescription() {
        return wrapped.getDescription();
    }

    @Override
    public double getPrice() {
        return wrapped.getPrice();
    }
}
