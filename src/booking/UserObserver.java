package booking;

public class UserObserver {
    private String contact; // email или телефон

    public UserObserver(String contact) {
        this.contact = contact;
    }

    public void update(double newPrice) {
        System.out.printf("Notification for %s: the price has decreased to %.2f\n", contact, newPrice);
    }
}
