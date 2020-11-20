import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class LoadAPI {
    public LoadAPI(){
        try {
            StringBuilder urlBuilder = new StringBuilder("http://www.career.go.kr/cnet/openapi/getOpenApi"); /*URL*/
            urlBuilder.append("?" + URLEncoder.encode("apiKey", "UTF-8") + "=" + URLEncoder.encode("327f658d87b31c1ea2c7d1dd130a05a7", "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("svcType", "UTF-8") + "=" + URLEncoder.encode("api", "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("svcCode", "UTF-8") + "=" + URLEncoder.encode("MAJOR_VIEW", "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("contentType", "UTF-8") + "=" + URLEncoder.encode("json", "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("gubun", "UTF-8") + "=" + URLEncoder.encode("univ_list", "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("univSe", "UTF-8") + "=" + URLEncoder.encode("univ", "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("subject", "UTF-8") + "=" + URLEncoder.encode("100394", "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("majorSeq", "UTF-8") + "=" + URLEncoder.encode("290", "UTF-8"));

            URL url = new URL(urlBuilder.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");
            System.out.println("Response code: " + conn.getResponseCode());
            BufferedReader rd;
            if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            rd.close();
            conn.disconnect();
            String result = sb.toString();
            System.out.println(result); // Debug print line

//            JSONParser parser = new JSONParser();
//            JSONObject obj = (JSONObject) parser.parse(result);
//
//            JSONObject parse_response = (JSONObject) obj.get("response");
//            JSONObject parse_body = (JSONObject) parse_response.get("body");
//            JSONObject parse_items = (JSONObject) parse_body.get("items");
//            JSONArray parse_item = (JSONArray) parse_items.get("item");
//            String category;
//            JSONObject weather;
//
//            for(int i = 0; i < parse_item.size(); i++) {
//
//            }


        }catch(IOException ex){
            System.out.println(ex);
        }
    }
}



