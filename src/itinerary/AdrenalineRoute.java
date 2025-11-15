package itinerary;

import java.util.Arrays;
import java.util.List;

public class AdrenalineRoute implements RouteStrategy {
    @Override
    public List<String> generateStops(String destination) {
        return Arrays.asList("Zipline", "Cliff diving", "ATV tour", "Bungee near " + destination);
    }

    @Override
    public double estimateAttractionsCost(String destination) {
        return 150.0;
    }
}