package itinerary;

import java.util.Arrays;
import java.util.List;

public class RelaxationRoute implements RouteStrategy {
    @Override
    public List<String> generateStops(String destination) {
        return Arrays.asList("Beach", "Spa", "Park", "Café near " + destination);
    }

    @Override
    public double estimateAttractionsCost(String destination) {
        return 50.0;
    }
}
