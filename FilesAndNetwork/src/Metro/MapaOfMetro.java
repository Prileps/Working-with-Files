package Metro;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class MapaOfMetro {
    private final JSONObject metroObject;

    public MapaOfMetro(JSONObject stationsObject, JSONArray linesArray, JSONArray connectionsArray) {
        metroObject = new JSONObject();
        metroObject.put("stations", stationsObject);
        metroObject.put("lines", linesArray);
        metroObject.put("connections", connectionsArray);
    }

    public JSONObject getMetroObject() {
        return metroObject;
    }
}