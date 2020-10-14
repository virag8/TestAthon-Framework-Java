package test.java.testathon.utils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class IO {

    public static JSONObject loadJSON(String JSONFile) {
        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();
        JSONObject testList = null;
        try (FileReader reader = new FileReader(JSONFile)) {
            //Read JSON file
            testList = (JSONObject) jsonParser.parse(reader);
            JSONArray arr = (JSONArray) testList.get("testWordPressLogin");
            System.out.println(testList);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return testList;
    }
}
