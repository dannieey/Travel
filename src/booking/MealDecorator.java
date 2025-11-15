package booking;

public class MealDecorator extends BookingDecorator {
    private static final double MEAL_COST = 30.0;

    public MealDecorator(Booking wrapped) {
        super(wrapped);
    }

    @Override
    public String getDescription() {
        return wrapped.getDescription() + " + Meal";
    }

    @Override
    public double getPrice() {
        return wrapped.getPrice() + MEAL_COST;
    }
}
