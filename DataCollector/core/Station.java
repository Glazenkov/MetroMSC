package DataCollector.core;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Station {
    private String name;
    private String line;
    private String date;
    private String depth;
    private boolean hasConnect;
    private int lineNumber;

    public Station(String name, boolean hasConnect, int lineNumber) {
        this.name = name;
        this.hasConnect = hasConnect;
        this.lineNumber = lineNumber;
    }

    @Override
    public String toString() {
        return "" + name +
                " на линии: " + lineNumber +
                " переход на другую линию:" + hasConnect + "\n";
    }
}
