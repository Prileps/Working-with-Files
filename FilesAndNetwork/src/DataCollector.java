import Metro.Station;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataCollector {

    Map<String, Station> listStation = new HashMap<>();

    String data;


    public Map<String, Station> getListStation() {
        return listStation;
    }

    public Map<String, Station> fileReader(String path) throws Exception, org.json.simple.parser.ParseException {
        File doc = new File(path);
        if (doc.isFile()) {
            data = doc.getAbsolutePath();
            if (doc.getName().endsWith(".json")) {
                getDataFromJson(doc);
            }
            if (doc.getName().endsWith(".csv")) {
                getDataFromCsv(doc);
            }
        } else {
            File[] files = doc.listFiles();
            for (File file : files) {
                fileReader(file.getAbsolutePath());
            }
        }
        return listStation;
    }

    private void getDataFromJson(File doc) throws Exception, org.json.simple.parser.ParseException {
        JSONParser parser = new JSONParser();
        JSONArray data = (JSONArray) parser.parse(getJsonFile());
        for (Object obj : data) {
            JSONObject stationJsonObj = (JSONObject) obj;
            String stationName = (String) stationJsonObj.get("station_name");
            if (!listStation.containsKey(stationName)) {
                listStation.put(stationName, new Station(stationName));
            }
            if (doc.getName().startsWith("depths")) {
                String depths = String.valueOf(stationJsonObj.get("depth"));
//                Station station = listStation.get(stationName);
//                String currStationDepth = station.getDepth();
//                if (depths == null || Long.parseLong(depths) > Long.parseLong(currStationDepth)) {
//                    station.setDepth(depths);
//                }
                listStation.get(stationName).setDepth(depths);
            }
        }
    }


    private void getDataFromCsv(File doc) throws FileNotFoundException {
        String path = doc.getAbsolutePath();
        BufferedReader reader = new BufferedReader(new FileReader(path));
        try {
            String split = ",";
            String line;
            while ((line = reader.readLine()) != null) {
                String[] lines = line.split(split, 2);
                for (int i = 0; i < lines.length; i++) {
                    if (i % 2 == 0) {
                        String stationName = lines[i];
                        if (!listStation.containsKey(stationName)) {
                            listStation.put(stationName, new Station(stationName));
                        }
                        if (doc.getName().startsWith("dates")) {
                            listStation.get(stationName).setDate(lines[i+1]);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private String getJsonFile() {
        StringBuilder builder = new StringBuilder();
        try {
            List<String> lines = Files.readAllLines(Paths.get(data));
            lines.forEach(builder::append);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return builder.toString();
    }


    public Map<String, Station> getListStations() {
        return listStation;
    }
}
