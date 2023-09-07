package Metro;

public class Station implements Comparable<Station>{
    private String numberLine;
    private String name;
    private String lineName;
    private String date;
    private String depth;
    private boolean hasConnection;

    public Station(String name) {
        this.name = name;
    }

    public Station(String name, String lineName, String date, String depth, boolean hasConnection) {
        this.name = name;
        this.lineName = lineName;
        this.date = date;
        this.depth = depth;
        this.hasConnection = hasConnection;
    }

    public Station(String name, String numberLine) {
        this.name = name;
        this.numberLine = numberLine;
    }

    public String getLineName() {
        return lineName;
    }

    public String getDate() {
        return date;
    }

    public String getDepth() {
        return depth;
    }

    public boolean isHasConnection() {
        return hasConnection;
    }

    public String getNumberLine() {
        return numberLine;
    }

    public String getName() {
        return name;
    }

    public void setNumberLine(String numberLine) {
        this.numberLine = numberLine;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDepth(String depth) {
        this.depth = depth;
    }

    public void setHasConnection(boolean hasConnection) {
        this.hasConnection = hasConnection;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int compareTo(Station station) {
        int lineComparison = numberLine.compareToIgnoreCase(station.getNumberLine());
        if(lineComparison != 0) {
            return lineComparison;
        }
        return name.compareToIgnoreCase(station.getName());
    }

    @Override
    public boolean equals(Object obj) {
        return compareTo((Station) obj) == 0;
    }
}