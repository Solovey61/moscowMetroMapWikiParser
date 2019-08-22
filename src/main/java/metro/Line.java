package metro;

import com.google.gson.annotations.Expose;

public class Line implements Comparable<Line> {
    @Expose
    private String number;
    @Expose
    private String name;

    public Line(String number, String name) {
        this.number = number;
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Line)
            return this.name.toLowerCase().equals(((Line) obj).name.toLowerCase()) && this.number.equals(((Line) obj).number);
        return false;
    }

    @Override
    public int compareTo(Line o) {
        return this.number.compareTo(o.number) + this.name.toLowerCase().compareTo(o.name.toLowerCase());
    }

    @Override
    public String toString() {
        return number;
    }
}
