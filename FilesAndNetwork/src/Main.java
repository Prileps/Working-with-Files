import Metro.Connections;
import Metro.MapaOfMetro;
import Metro.Station;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

public class Main {
    public static void main(String[] args) throws Exception {

        Parser parser = new Parser("https://skillbox-java.github.io");

        JSONArray linesArray = parser.parseLine();

        JSONObject stationsObject = parser.parseStation();

        parser.parseConnection();
        List<Station> stations = parser.getContainerStations().getStations();
        TreeSet<Connections> connections = parser.getContainerStations().getConnections();
        JSONArray connectionsArray = parser.writeConnectionsInJSON(connections);
        MapaOfMetro metro = new MapaOfMetro(stationsObject, linesArray, connectionsArray);

        JSONCreator jsonWriter = new JSONCreator();
        jsonWriter.writeJsonFile(metro.getMetroObject(), "data/metro.json");
        JsonReader jsonReader = new JsonReader();
        jsonReader.getTotalStations("data/metro.json");

        DataCollector collector = new DataCollector();
        collector.fileReader("data/data");
        Map<String, Station> listStations = collector.getListStations();
        setParameterHasConnection(connections, listStations);
        setParamLineName(stations, linesArray, listStations);
        JSONObject stationObject = new JSONObject();
        JSONArray stationsArray = new JSONArray();
        for (Map.Entry<String, Station> entry: listStations.entrySet()) {
            JSONObject stationObj = new JSONObject();
            if(entry.getValue().getName()!=null) {
                stationObj.put("name", entry.getValue().getName());
            }
            if (entry.getValue().getLineName()!=null) {
                stationObj.put("line", entry.getValue().getLineName());
            }
            if (entry.getValue().getDate()!=null) {
                stationObj.put("date", entry.getValue().getDate());
            }
            if (entry.getValue().getDepth()!=null) {
                stationObj.put("depth", entry.getValue().getDepth());
            }
            stationObj.put("hasConnection", entry.getValue().isHasConnection());
            stationsArray.add(stationObj);
        }
        stationObject.put("stations", stationsArray);
        JSONCreator jsonWriter2 = new JSONCreator();
        jsonWriter2.writeJsonFile(stationObject, "data/stations.json");
    }

    private static void setParameterHasConnection(TreeSet<Connections> connections, Map<String, Station> listStations) {
        listStations.keySet().forEach(k -> {
            for (Connections connection : connections) {
                for (Station station : connection.getConnectionStations()) {
                    if (station.getName().equals(k)) {
                        listStations.get(k).setHasConnection(true);
                    }
                }
            }
        });
    }

    private static void setParamLineName(List<Station> stations, JSONArray linesArray, Map<String, Station> listStations) {
        listStations.keySet().forEach(key -> {
            for (Station station : stations) {
                if(station.getName().equals(key)) {
                    linesArray.forEach(lineObject -> {
                        JSONObject lineJsonObject = (JSONObject) lineObject;
                        if (lineJsonObject.get("number").equals(station.getNumberLine())) {
                            listStations.get(key).setLineName((String) lineJsonObject.get("name"));
                        }
                    });
                }
            }
        });
    }
}