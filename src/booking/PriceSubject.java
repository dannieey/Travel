package booking;

import java.util.ArrayList;
import java.util.List;

public class PriceSubject {
    private List<UserObserver> observers = new ArrayList<>();
    private double price = 0;

    public void registerObserver(UserObserver o) {
        observers.add(o);
    }



    public void notifyObservers() {
        for (UserObserver o : observers) {
            o.update(price);
        }
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double newPrice) {
        if (newPrice < this.price) {
            this.price = newPrice;
            notifyObservers();
        } else {
            this.price = newPrice;
        }
    }
}
