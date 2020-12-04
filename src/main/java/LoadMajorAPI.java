import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import org.json.simple.parser.ParseException;
public class LoadMajorAPI {
    ArrayList<String> majorSeqArr = new ArrayList<String>();
    public LoadMajorAPI(String subject){
        try {
            StringBuilder urlBuilder = new StringBuilder("http://www.career.go.kr/cnet/openapi/getOpenApi"); /*URL*/
            urlBuilder.append("?" + URLEncoder.encode("apiKey", "UTF-8") + "=" + URLEncoder.encode("327f658d87b31c1ea2c7d1dd130a05a7", "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("svcType", "UTF-8") + "=" + URLEncoder.encode("api", "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("svcCode", "UTF-8") + "=" + URLEncoder.encode("MAJOR", "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("contentType", "UTF-8") + "=" + URLEncoder.encode("json", "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("gubun", "UTF-8") + "=" + URLEncoder.encode("univ_list", "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("univSe", "UTF-8") + "=" + URLEncoder.encode("univ", "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("subject", "UTF-8") + "=" + URLEncoder.encode(subject, "UTF-8"));

            URL url = new URL(urlBuilder.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");
//            System.out.println("Response code: " + conn.getResponseCode());
            BufferedReader rd;
            if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null){
                sb.append(line);
            }
            rd.close();
            conn.disconnect();
            String result = sb.toString();
//            System.out.println(result); // Debug print line
            try {
                JSONParser parser = new JSONParser();
                JSONObject obj = (JSONObject) parser.parse(result);
                JSONObject dataSearchObj = (JSONObject) obj.get("dataSearch");
                JSONArray contentArray = (JSONArray) dataSearchObj.get("content");
                for(int i=0;i<contentArray.size(); i++){
                    JSONObject contentObj = (JSONObject) contentArray.get(i);
                    majorSeqArr.add((String) contentObj.get("majorSeq"));
                }

            }catch(ParseException e){
                e.printStackTrace();
            }
        }catch(IOException ex){
            System.out.println(ex);
        }
    }

    public ArrayList<String> getMajorSeqArr() {
        return majorSeqArr;
    }
}
