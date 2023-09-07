package Metro;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class ContainerStations {
    private final List<Station> stations;
    private final TreeSet<Connections> connections;

    public ContainerStations() {
        stations = new ArrayList<>();
        connections = new TreeSet<>();
    }

    public List<Station> getStations() {
        return stations;
    }

    public TreeSet<Connections> getConnections() {
        return connections;
    }

    public void addStation(Station station) {
        stations.add(station);
    }

    public void addConnection(Connections stationsCon) {
        if(!containsStation(stationsCon)) {
            connections.add(stationsCon);
        }
    }

    private boolean containsStation(Connections stationsCon) {
        for(Connections connection : connections) {
            for(Station station : connection.getConnectionStations()) {
                for(Station stationInner : stationsCon.getConnectionStations()) {
                    if(station.getName().equals(stationInner.getName()) && station.getNumberLine().equals(stationInner.getNumberLine())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}