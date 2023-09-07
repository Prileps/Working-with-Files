package Metro;

import org.json.simple.JSONObject;

public class Line implements Comparable<Line> {

    private final JSONObject jsonLine;
    private final String number;
    private final String name;

    public Line(String number, String name) {
        jsonLine = new JSONObject();
        jsonLine.put("number", number);
        jsonLine.put("name", name);
        this.number = number;
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public JSONObject getJsonLine() {
        return jsonLine;
    }

    @Override
    public int compareTo(Line line) {
        return number.compareToIgnoreCase(line.getNumber());
    }

    @Override
    public boolean equals(Object obj) {
        return compareTo((Line) obj) == 0;
    }

    @Override
    public String toString() {
        return name;
    }
}