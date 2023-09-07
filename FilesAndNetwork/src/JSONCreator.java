import org.json.simple.JSONObject;

import java.io.FileWriter;

public class JSONCreator {

    public JSONCreator() {
    }

    public void writeJsonFile(JSONObject obj, String path) throws Exception {
        FileWriter fileWriter = new FileWriter(path);
        fileWriter.write(obj.toJSONString());
        fileWriter.flush();
        fileWriter.close();
    }
}
