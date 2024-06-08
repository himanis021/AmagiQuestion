import com.fasterxml.jackson.databind.JsonNode;
import com.octomix.josson.Josson;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author myname
 *//* Created by himani-sharma on 08/06/24 */
public class Solutions {
    public static void main(String[] args) throws Exception {
        // your code goes here
        URL url = new URL("https://ip-ranges.amazonaws.com/ip-ranges.json");
        JSONParser parser = new JSONParser();
        JSONObject jsonObj = (JSONObject) parser.parse(new InputStreamReader(url.openStream()));
        JSONArray prefixJSONArray = (JSONArray) jsonObj.get("prefixes");

// 1st question: to print all ip_prefix of all prefixes
        System.out.println("----------Solution of Ques 1-------------");
        for (int i = 0; i < prefixJSONArray.size(); i++) {
            JSONObject ipJSONObject = (JSONObject) prefixJSONArray.get(i);
            String ipPrefix = (String) ipJSONObject.get("ip_prefix");
            System.out.println("ip-prefix of index " + i + ": " + ipPrefix);
        }

//2nd question: seggregate ip_prefix as per the regions
        System.out.println("\n\n----------Solution of Ques 2-------------");

        //store all data in a local arrray.
        List<String> arr = new ArrayList<String>();

        for (int j = 0; j < prefixJSONArray.size(); j++) {
            //avoid to add duplicate entry. e.g. there are multiple Developer so avoid adding more than 1 times.
            boolean isDuplicate = false;
            String regionName = new String();
            for(int i=0; i<arr.size();i++){
                JSONObject jsonObject1 = (JSONObject) prefixJSONArray.get(j);
                regionName = (String) jsonObject1.get("region");
                if(arr.get(i).equalsIgnoreCase(regionName.trim())){
                    isDuplicate = true;
                }
            }
            if(isDuplicate==false){
                arr.add(regionName);
            }
        }
        arr.remove(0);

        //Now finally create your desired parsed JSON object.
        JSONObject result= new JSONObject(); //final Result JSON object.
        for(String regionname : arr){
            //first adding empty list.
            result.put(regionname, new ArrayList<JSONObject>());
            String regions = new String();
            JSONArray array = new JSONArray();
            for (int j = 0; j < prefixJSONArray.size(); j++) {
                JSONObject jsonObject1 = (JSONObject) prefixJSONArray.get(j);
                regions = (String) jsonObject1.get("region");
                if(regionname.equalsIgnoreCase(regions.trim())){
                    array.add(((JSONObject) prefixJSONArray.get(j)).get("ip_prefix"));
                }
            }
            result.put(regionname, array);
        }
        System.out.println("Final Result: \n"+ result.toJSONString());
    }
}
