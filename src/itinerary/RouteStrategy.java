package itinerary;

import java.util.List;

public interface RouteStrategy {
    List<String> generateStops(String destination);
    double estimateAttractionsCost(String destination);
}
