package DataCollector;

import DataCollector.core.Line;
import DataCollector.core.Station;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class WebPageParsing {
    private final String url;
    private Document doc;

    public WebPageParsing(String url) {
        this.url = url;
        this.doc = getHTMLCode();
    }

    public Document getHTMLCode() {
        try {
            doc = Jsoup.connect(url).get();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return doc;
    }

    public List<Line> parseLine() {
        Line line;
        List<Line> lines = new ArrayList<>();
        Elements linesElements = doc.select("[data-depend]");

        int numLine = 0;
        for (Element element : linesElements) {
            numLine++;
            line = new Line(numLine, element.text());
            lines.add(line);
        }
        return lines;
    }

    public List<Station> parseStations() {
        Station station;
        List<Station> stations = new ArrayList<>();
        Elements stationsElements = doc.select("p.single-station");

        int num = 0;
        for (Element e : stationsElements) {
            if (e.elementSiblingIndex() == 0) {
                num++;
            }
            boolean hasConnect = e.childrenSize() > 2;
            station = new Station(e.text().split("\\. ")[1], hasConnect, num);
            stations.add(station);
        }
        return stations;
    }
}
