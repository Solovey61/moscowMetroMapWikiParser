package wikiHTMLParser;

import metro.Line;
import metro.Metro;
import metro.Station;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

public class WikiHTMLParser {
    public static Optional<Elements> parse(URL url) {
        try {
            Document document = Jsoup
                    .connect(url.toString())
                    .maxBodySize(0)
                    .get();
            Optional<Elements> elements = Optional.of(document.select(".standard.sortable tbody tr"));
            elements.ifPresent(elements1 -> elements1.forEach(e -> e.select("small").remove()));
            return elements;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Metro getMetro(Optional<Elements> elements) {
        Metro metro = new Metro();
        elements.ifPresent(value -> value.forEach(element -> {
            String stationName = element.select("td:nth-child(2)").text();
            Elements linesNames = element.select("td:nth-child(1) img");
            Elements linesNumbers = element.select("td:nth-child(1) span.sortkey:not(:last-child)");
            for (int i = 0; i < linesNames.size(); i++) {
                String lineName = linesNames.get(i).attr("alt");
                String lineNumber = linesNumbers.get(i).text();
                if (linesNames.size() == linesNumbers.size() && !lineName.equals("") && !stationName.equals("")) {
                    Line line = new Line(lineNumber, lineName);
                    if (metro.getLine(line) == null)
                        metro.addLine(line);
                    else
                        line = metro.getLine(line);
                    Station station = new Station(line.getNumber(), stationName);
                    metro.addStation(station);
                }
            }
        }));
        parseConnections(elements, metro);
        return metro;
    }

    private static void parseConnections(Optional<Elements> elements, Metro metro) {
        elements.ifPresent(value -> value.forEach(element -> {
            Elements linesNumbers = element.select("td:nth-child(4) span.sortkey");
            Elements stationNames = element.select("td:nth-child(4) a[title~=Переход на станцию");
            if (linesNumbers.size() > 0 && linesNumbers.size() == stationNames.size()) {
                ArrayList<Station> connections = new ArrayList<>();
                for (int i = 0; i < linesNumbers.size(); i++) {
                    String lineNumber = linesNumbers.get(i).text();
                    String stationName = stationNames.get(i).attr("href");
                    if (!stationName.equals("") && !lineNumber.equals("")) {
//                        Extract station name from URL
                        stationName = stationName.substring(stationName.lastIndexOf("/") + 1).replaceAll("_", " ");
//                        Remove brackets and text in brackets if name of station contains it
                        if (stationName.contains("("))
                            stationName = stationName.substring(0, stationName.indexOf("(")).trim();
//                        Decode URL
                        stationName = URLDecoder.decode(stationName, Charset.defaultCharset());
                    }
                    Station station = new Station(lineNumber, stationName);
                    connections.add(station);
                }
                String currentStationName = element.select("td:nth-child(2)").text();
                Elements currentLinesNumbers = element.select("td:nth-child(1) span.sortkey:not(:last-child)");
                for (int i = 0; i < currentLinesNumbers.size(); i++) {
                    String currentLineNumber = currentLinesNumbers.get(i).text();
                    Station currentStation = new Station(currentLineNumber, currentStationName);
                    connections.add(currentStation);
                }
                Collections.sort(connections);
                if (!metro.getConnections().contains(connections)) {
                    metro.addConnection(connections);
                }
            }
        }));
    }
}
