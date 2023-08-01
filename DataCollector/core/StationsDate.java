package DataCollector.core;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class StationsDate {
    private String stationName;
    private String date;

    @Override
    public String toString() {
        return "Станция: " + stationName + ", дата открытия: " + date;
    }
}

