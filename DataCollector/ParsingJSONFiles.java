package DataCollector;

import DataCollector.core.StationsDepths;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ParsingJSONFiles {
    private final String path;

    public ParsingJSONFiles(String path) throws IOException {
        this.path = path;
    }

    public List<StationsDepths> getObjects() throws IOException {
        String jsonFile = Files.readString(Paths.get(path));
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonData = objectMapper.readTree(jsonFile);

        List<StationsDepths> stationsDepthsList = new ArrayList<>();

        for (JsonNode object : jsonData) {
            String name = object.get("station_name").asText();
            String depth = object.get("depth").asText().replace(',', '.');

            String regDepth = "-?[\\d.]+";
            boolean okDepth = Pattern.matches(regDepth, depth);

            if (okDepth) {
                StationsDepths stationsDepths = new StationsDepths(name, depth);
                stationsDepthsList.add(stationsDepths);
            }
        }
        return stationsDepthsList;
    }
}

