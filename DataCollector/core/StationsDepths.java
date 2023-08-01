package DataCollector.core;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class StationsDepths {
    private String stationName;
    private String depth;

    @Override
    public String toString() {
        return "Станция: " + stationName + ", глубина: " + depth;
    }
}
