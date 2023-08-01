package DataCollector.core;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Line {
    private int number;
    private String name;

    @Override
    public String toString() {
        return "Линия номер " + number + " : " + name;
    }
}
