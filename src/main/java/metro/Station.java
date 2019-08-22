package metro;

import com.google.gson.annotations.Expose;

public class Station implements Comparable<Station> {
    @Expose
    private String line;
    @Expose
    private String station;

    public Station(String line, String station) {
        this.station = station;
        this.line = line;
    }

    public String getStation() {
        return station;
    }

    public String getLine() {
        return line;
    }

    @Override
    public int compareTo(Station o) {
        return this.line.compareTo(o.line) + this.station.toLowerCase().compareTo(o.station.toLowerCase());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Station) {
            if (this.line.equals(((Station) obj).line) && this.station.equals(((Station) obj).station)) return true;
        }
        return false;
    }
}
