package facade;

import transport.*;
import itinerary.*;
import booking.*;

import java.util.List;

public class TravelFacade {
    private ItineraryBuilder itineraryBuilder = new ItineraryBuilder();
    private RouteStrategy strategy;
    private Transport selectedTransport;
    private Booking booking = new BaseBooking();
    private PriceSubject priceSubject = new PriceSubject();
    private String seatClass = "economy";
    private String destination = "";
    private String mood = "";

    public PriceSubject getPriceSubject() {
        return priceSubject;
    }

    // Выбор стратегии маршрута по настроению (не custom)
    public void selectRoute(String mood, String destination) {
        this.mood = mood.toLowerCase();
        this.destination = destination;

        switch (this.mood) {
            case "relaxation":
                strategy = new RelaxationRoute();
                break;
            case "adrenaline":
                strategy = new AdrenalineRoute();
                break;
            case "romance":
                strategy = new RomanceRoute();
                break;
            default:
                strategy = new RelaxationRoute();
        }

        itineraryBuilder.reset();
        itineraryBuilder.setDestination(destination);
        itineraryBuilder.setStrategy(strategy);
        itineraryBuilder.build();
        System.out.println("The route has been created: " + itineraryBuilder.getItinerary());
    }

    // Кастомный маршрут
    public void selectCustomRoute(List<String> customStops, String destination) {
        this.mood = "custom";
        this.destination = destination;

        itineraryBuilder.reset();
        itineraryBuilder.buildCustomRoute(customStops, destination);

        System.out.println("Custom route created based on your preferences.");
        System.out.println("The route has been created: " + itineraryBuilder.getItinerary());
    }

    // Создание транспорта через абстрактную фабрику
    public void selectTransport(String type) {
        TransportFactory factory = new TransportFactory();
        selectedTransport = factory.createTransport(type);
        if (selectedTransport != null) {
            priceSubject.setPrice(selectedTransport.getBasePrice());
            System.out.println("Transport selected: " + selectedTransport.getName() +
                    ", base price: " + selectedTransport.getBasePrice());
        } else {
            System.out.println("Unknown type of transport: " + type);
        }
    }

    public void bookTransport(String origin, String destination, String dateTo, String dateBack) {
        if (selectedTransport == null) {
            System.out.println("First, select a transport.");
            return;
        }
        booking = new BaseBooking(origin, destination, dateTo, dateBack, selectedTransport.getBasePrice());
        System.out.println("Reservation created: " + booking.getDescription());
    }

    // Декораторы — добавление опций (без вывода)
    public void addMealOption() {
        booking = new MealDecorator(booking);
    }

    public void addInsuranceOption() {
        booking = new InsuranceDecorator(booking);
    }

    // Подсчет общей стоимости (если понадобится)
    public double calculateTotalCost() {
        double transportCost = booking.getPrice();
        double lodging = itineraryBuilder.estimateLodgingCost();
        double attractions = itineraryBuilder.estimateAttractionsCost();
        return transportCost + lodging + attractions;
    }

    public Booking getBooking() {
        return this.booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public String getSeatClass() {
        return seatClass;
    }

    public void applySeatClassUpgrade(String cls) {
        this.seatClass = cls.toLowerCase();
        if ("business".equalsIgnoreCase(cls)) {
            booking = new BusinessClassDecorator(booking);
        }
        // Economy — без декоратора
    }

    public boolean isMealAdded() {
        return booking.getDescription().contains("Meal");
    }

    public boolean isInsuranceAdded() {
        return booking.getDescription().contains("Insurance");
    }

    public String getDestination() {
        return destination;
    }

    public String getMood() {
        return mood;
    }

    public String[] getPlannedStops() {
        return itineraryBuilder.getItinerary().toArray(new String[0]);
    }

    public Transport getSelectedTransport() {
        return selectedTransport;
    }

    // Изменение маршрута
    public void removeStop(int index) {
        itineraryBuilder.removeStop(index);
    }

    public void addStop(String stop) {
        itineraryBuilder.addStop(stop);
    }
}