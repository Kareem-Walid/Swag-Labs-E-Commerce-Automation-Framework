package Utilities;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public final class DataUtils {

    private static final String TEST_DATA_PATH = "src/test/resources/TestData/";


    public static String getJsonData(String fileName, String field) throws FileNotFoundException {

        FileReader reader = new FileReader(TEST_DATA_PATH + fileName + ".json");
        JsonElement jsonElement = JsonParser.parseReader(reader);
        return jsonElement.getAsJsonObject().get(field).getAsString();
    }

    public static String getPropertyData(String fileName, String key) throws IOException {

        Properties properties = new Properties();
        properties.load(new FileInputStream(TEST_DATA_PATH + fileName + ".properties"));
        return properties.getProperty(key);
    }


    public static Object[][] getJsonArrayData(String fileName) {
        Path path = Path.of(TEST_DATA_PATH).resolve(fileName + ".json");
        try (Reader reader = Files.newBufferedReader(path)) {
            JsonArray array = JsonParser.parseReader(reader).getAsJsonArray();
            Object[][] data = new Object[array.size()][2];
            for (int i = 0; i < array.size(); i++) {
                JsonObject row = array.get(i).getAsJsonObject();
                data[i][0] = row.get("username").getAsString();
                data[i][1] = row.get("password").getAsString();
            }
            return data;
        } catch (IOException e) {
            throw new RuntimeException("Cannot read test data file: " + path, e);
        }
    }

}
