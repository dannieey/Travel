package itinerary;

import java.util.Arrays;
import java.util.List;

public class RomanceRoute implements RouteStrategy {
    @Override
    public List<String> generateStops(String destination) {
        return Arrays.asList("Sunset viewpoint", "Romantic dinner", "Boat ride", "Local winery");
    }

    @Override
    public double estimateAttractionsCost(String destination) {
        return 100.0;
    }
}
