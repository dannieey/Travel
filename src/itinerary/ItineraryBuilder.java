package itinerary;

import java.util.ArrayList;
import java.util.List;

public class ItineraryBuilder {
    private RouteStrategy strategy;
    private String destination;
    private List<String> stops = new ArrayList<>();

    private boolean customRoute = false;
    private double customAttractionsCost = 0.0;

    public void reset() {
        stops.clear();
        customRoute = false;
        customAttractionsCost = 0.0;
    }

    public void setStrategy(RouteStrategy strategy) {
        this.strategy = strategy;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void build() {
        if (strategy == null || destination == null) return;
        customRoute = false;
        customAttractionsCost = 0.0;
        stops = new ArrayList<>(strategy.generateStops(destination));
    }

    /**
     * Кастомный маршрут, основанный на списке остановок пользователя.
     */
    public void buildCustomRoute(List<String> userStops, String destination) {
        this.destination = destination;
        this.customRoute = true;
        this.customAttractionsCost = 80.0; // условная стоимость достопримечательностей

        stops = new ArrayList<>();
        if (userStops != null) {
            for (String s : userStops) {
                if (s != null) {
                    String trimmed = s.trim();
                    if (!trimmed.isEmpty()) {
                        stops.add(trimmed);
                    }
                }
            }
        }
        // добавим «Local café in …», как в примере
        stops.add("Local café in " + destination);
    }

    public List<String> getItinerary() {
        return stops;
    }

    public double estimateAttractionsCost() {
        if (customRoute) {
            return customAttractionsCost;
        }
        if (strategy == null || destination == null) return 0;
        return strategy.estimateAttractionsCost(destination);
    }

    // грубая оценка стоимости проживания (пример)
    public double estimateLodgingCost() {
        return 70.0 * 3; // 3 ночи по 70
    }

    public void removeStop(int index) {
        if (index >= 0 && index < stops.size()) {
            stops.remove(index);
        }
    }

    public void addStop(String stop) {
        if (stop != null) {
            String trimmed = stop.trim();
            if (!trimmed.isEmpty()) {
                stops.add(trimmed);
            }
        }
    }
}
