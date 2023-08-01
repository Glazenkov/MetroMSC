package DataCollector;

import DataCollector.core.StationsDate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class ParsingCSVFiles {
    private final String path;

    public ParsingCSVFiles(String path) throws IOException {
        this.path = path;
    }

    public List<StationsDate> getObjects() throws IOException {
        List<String> lines = new ArrayList<>();
        try {
            lines = Files.readAllLines(Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<StationsDate> stationsDatesList = new ArrayList<>();
        String firstLine = null;
        for (String line : lines) {
            if (firstLine == null) {
                firstLine = line;
                continue;
            }
            String[] tokens = line.split(",");
            String name = tokens[0];
            String date = tokens[1];

            StationsDate stationsDate = new StationsDate(name, date);
            stationsDatesList.add(stationsDate);
        }
        return stationsDatesList;
    }
}

