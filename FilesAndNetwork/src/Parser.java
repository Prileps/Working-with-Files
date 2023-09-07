//import org.apache.commons.lang3.StringEscapeUtils;
import Metro.Connections;
import Metro.ContainerStations;
import Metro.Line;
import Metro.Station;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class Parser {

    private Document document;
    private List<Line> lines;
    private ContainerStations containerStations;
    private DataCollector collector;

    public Parser(String url) throws Exception {
        document = Jsoup.connect(url).maxBodySize(0).get();
        lines = new ArrayList<>();
        containerStations = new ContainerStations();
    }

    public List<Line> getLines() {
        return lines;
    }

    public ContainerStations getContainerStations() {
        return containerStations;
    }

    public DataCollector getDataCollector() { return collector; }

    public JSONArray parseLine() {
        Elements linesList = document.getElementsByAttributeValueStarting("class", "js-metro-line t-metrostation-list-header t-icon-metroln ln");
        JSONArray linesObjectArray = new JSONArray();
        for(Element element : linesList) {
            Line line = new Line(element.attr("data-line"), element.ownText());
            linesObjectArray.add(line.getJsonLine());
            lines.add(line);
        }
        return linesObjectArray;
    }

    public JSONObject parseStation() {
        Elements dataList = document.getElementsByClass("js-metro-stations t-metrostation-list-table");
        JSONObject stationsObject = new JSONObject();
        for(Element element : dataList) {
            JSONArray stationsArray = new JSONArray();
            Elements stationsList = element.getElementsByClass("name");
            for(Element stationElement : stationsList) {
                stationsArray.add(stationElement.text());
                stationsObject.put(element.attr("data-line"), stationsArray);
                containerStations.addStation(new Station(stationElement.text(), element.attr("data-line")));
            }
        }
        return stationsObject;
    }

    public void parseConnection() {
        Elements dataList = document.getElementsByClass("js-metro-stations t-metrostation-list-table");
        for(Element element : dataList) {
            Elements connectionsList = element.select("p:has(span[title])");
            for(Element connectionElement : connectionsList) {
                String station = connectionElement.text();
                int indexSpace = station.lastIndexOf(";");
                String stationName = station.substring(indexSpace + 1).trim();

                Connections stationsConnection = new Connections();
                stationsConnection.addStation(new Station(stationName, element.attr("data-line")));

                Elements connectionsSpanList = connectionElement.select("span[title]");
                for(Element conSpanElement : connectionsSpanList) {
                    String line = conSpanElement.attr("class");
                    int indexDash = line.lastIndexOf("-");
                    String numberLine = line.substring(indexDash + 1);

                    String text = conSpanElement.attr("title");
                    int indexQuote = text.indexOf("«");
                    int lastIndexQuote = text.lastIndexOf("»");
                    String station1 = text.substring(indexQuote + 1, lastIndexQuote);

                    stationsConnection.addStation(new Station(station1, numberLine));
                }
                containerStations.addConnection(stationsConnection);
            }
        }
    }

    public JSONArray writeConnectionsInJSON(TreeSet<Connections> connections) {
        JSONArray connectionsArray = new JSONArray();
        for(Connections stationsCon : connections) {
            JSONArray connectionObjectArray = new JSONArray();
            for(Station station : stationsCon.getConnectionStations()) {
                JSONObject connectionObject = new JSONObject();
                connectionObject.put("line", station.getNumberLine());
                connectionObject.put("station", station.getName());
                connectionObjectArray.add(connectionObject);
            }
            connectionsArray.add(connectionObjectArray);
        }
        return connectionsArray;
    }
}