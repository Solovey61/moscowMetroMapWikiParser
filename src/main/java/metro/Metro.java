package metro;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Objects;

public class Metro {
    private LinkedHashMap<String, ArrayList<String>> stations;
    private ArrayList<Line> lines;
    private ArrayList<ArrayList<Station>> connections;

    public Metro() {
        stations = new LinkedHashMap<>();
        lines = new ArrayList<>();
        connections = new ArrayList<>();
    }

    public void addLine(Line line) {
        if (!lines.contains(line)) {
            lines.add(line);
            stations.put(line.getNumber(), new ArrayList<>());
        }
    }

    public Line getLine(Line line) {
        for (Line currentLine : lines) {
            if (currentLine.equals(line))
                return currentLine;
        }
        return null;
    }

    public void addStation(Station station) {
        stations.get(station.getLine()).add(station.getStation());
    }

    public LinkedHashMap<String, ArrayList<String>> getStations() {
        return stations;
    }

    public ArrayList<ArrayList<Station>> getConnections() {
        return connections;
    }

    public void addConnection(ArrayList<Station> connection) {
        connections.add(connection);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Metro metro = (Metro) o;
        return Objects.equals(stations, metro.stations) &&
                Objects.equals(lines, metro.lines) &&
                Objects.equals(connections, metro.connections);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stations, lines, connections);
    }
}
