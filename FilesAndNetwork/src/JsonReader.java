import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;

public class JsonReader {

    public void getTotalStations(String path) throws Exception {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(path));
        JSONObject metroJsonObj = (JSONObject) obj;
        JSONObject stationObj = (JSONObject) metroJsonObj.get("stations");
        stationObj.keySet().forEach(key -> {
            JSONArray stationArray = (JSONArray) stationObj.get(key);
            System.out.println("Название линии: " + key + ". Количество станций: " + stationArray.size() + ".");
        });
    }
}
