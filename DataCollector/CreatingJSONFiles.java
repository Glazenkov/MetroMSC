package DataCollector;

import DataCollector.core.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

public class CreatingJSONFiles {
    private final List<Line> lines;
    private final List<Station> stations;
    private final List<StationsDate> stationsDates;
    private final List<StationsDepths> stationsDepths;

    public CreatingJSONFiles(
            List<Line> lines,
            List<Station> stations,
            List<StationsDate> stationsDates,
            List<StationsDepths> stationsDepths
    ) throws ParseException {
        this.lines = lines;
        this.stations = stations;
        this.stationsDates = stationsDates;
        this.stationsDepths = stationsDepths;
        creatStationsList();
    }

    private void creatStationsList() throws ParseException {
        for (Station station : stations) {
            for (Line line : lines) {
                if (station.getLineNumber() == line.getNumber()) {
                    station.setLine(line.getName());
                }
            }
            for (StationsDate stationsDate : stationsDates) {
                if (station.getName().equals(stationsDate.getStationName())) {
                    String date = searchForOldestDate(stationsDate.getStationName());
                    station.setDate(date);
                }
            }
            for (StationsDepths stationsDepth : stationsDepths) {
                if (station.getName().equals(stationsDepth.getStationName())) {
                    String depth = searchForTheMoreDepth(station.getName());
                    station.setDepth(depth);
                }
            }
        }
    }

    public void creatJSONStations() {
        class JSONStations {
            public final JsonNode stations;

            public JSONStations(JsonNode stations) {
                this.stations = stations;
            }
        }
        Stations jsonStations = new Stations(stations);
        String path = "stations.json";
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(new File(path), jsonStations);
            String jsonFile = Files.readString(Paths.get(path));
            JsonNode jsonData = mapper.readTree(jsonFile);
            JsonNode stations = jsonData.get("stations");

            for (JsonNode station : stations) {
                ObjectNode stationNode = (ObjectNode) station;
                stationNode.remove("lineNumber");
                String date = stationNode.get("date").asText();
                String regDate = "[\\d.]+";
                boolean okDate = Pattern.matches(regDate, date);

                if (!okDate) {
                    stationNode.remove("date");
                }

                String depth = stationNode.get("depth").asText();
                String regDepth = "-?[\\d.]+";
                boolean okDepth = Pattern.matches(regDepth, depth);

                if (!okDepth) {
                    stationNode.remove("depth");
                }
            }
            JSONStations json = new JSONStations(stations);
            mapper.writeValue(new File(path), json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String searchForOldestDate(String name) throws ParseException {
        List<Date> dates = new ArrayList<>();
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        for (StationsDate stationsDate : stationsDates) {
            if (stationsDate.getStationName().equals(name)) {
                Date date = formatter.parse(stationsDate.getDate());
                dates.add(date);
            }
        }
        if (!dates.isEmpty()) {
            Date oldDate = dates.get(0);
            for (int i = 1; i < dates.size(); i++) {
                if (dates.get(i).compareTo(oldDate) < 0) {
                    oldDate = dates.get(i);
                }
            }
            return formatter.format(oldDate);
        }
        return "";
    }

    private String searchForTheMoreDepth(String name) {
        List<Double> depths = new ArrayList<>();

        for (StationsDepths depth : stationsDepths) {
            if (!depth.getDepth().equals("?") && depth.getStationName().equals(name)) {
                depths.add(Double.parseDouble(depth.getDepth()));
            }
        }
        if (!depths.isEmpty()) {
            double maxDepth = depths.get(0);
            for (int i = 1; i < depths.size(); i++) {
                if (depths.get(i) < maxDepth) {
                    maxDepth = depths.get(i);
                }
            }
            return Double.toString(maxDepth).split("\\.0")[0];
        }
        return "";
    }

    public void creatJSONMap() {
        class MapJSON {
            public final Map<Integer, List<String>> stations;
            public final List<Line> lines;

            public MapJSON(Map<Integer, List<String>> stations, List<Line> lines) {
                this.stations = stations;
                this.lines = lines;
            }
        }
        Map<Integer, List<String>> map = new TreeMap<>();
        List<String> stationsName = new ArrayList<>();
        for (int i = 1; i < stations.size(); i++) {
            stationsName.add(stations.get(i - 1).getName());
            if (i == stations.size() - 1) {
                stationsName.add(stations.get(i).getName());
            }
            map.put(stations.get(i - 1).getLineNumber(), stationsName);
            if (stations.get(i).getLineNumber() > stations.get(i - 1).getLineNumber()) {
                stationsName = new ArrayList<>();
            }
        }

        MapJSON jsonObject = new MapJSON(map, lines);
        String path = "map.json";
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(new File(path), jsonObject);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


