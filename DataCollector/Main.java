package DataCollector;

import DataCollector.core.StationsDate;
import DataCollector.core.StationsDepths;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws ParseException {
        String url = "https://skillbox-java.github.io/";
        WebPageParsing moscowMetro = new WebPageParsing(url);

        String pathData = "data";
        SearchFilesInFolders search = new SearchFilesInFolders();
        search.bypassFolders(pathData);

        List<StationsDepths> depthsList = new ArrayList<>();
        String pathJSON = "data/2/4/depths-1.json";
        try {
            ParsingJSONFiles depths = new ParsingJSONFiles(pathJSON);
            depthsList = depths.getObjects();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        List<StationsDate> datesList = new ArrayList<>();
        String pathCSV = "data/0/5/dates-2.csv";
        try {
            ParsingCSVFiles dates = new ParsingCSVFiles(pathCSV);
            datesList = dates.getObjects();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        CreatingJSONFiles jsonFile = new CreatingJSONFiles(
                moscowMetro.parseLine(),
                moscowMetro.parseStations(),
                datesList,
                depthsList
        );
        jsonFile.creatJSONStations();
        jsonFile.creatJSONMap();
    }
}
