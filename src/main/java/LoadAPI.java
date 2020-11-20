import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.IOException;

public class LoadAPI {
    public static void main(String[] args) throws IOException {
        StringBuilder urlBuilder = new StringBuilder("http://www.career.go.kr/cnet/openapi/getOpenApi"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("apiKey","UTF-8") + "=" + URLEncoder.encode("327f658d87b31c1ea2c7d1dd130a05a7", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("svcType","UTF-8") + "=" + URLEncoder.encode("api", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("svcCode","UTF-8") + "=" + URLEncoder.encode("MAJOR_VIEW", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("contentType","UTF-8") + "=" + URLEncoder.encode("xml", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("gubun","UTF-8") + "=" + URLEncoder.encode("univ_list", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("univSe","UTF-8") + "=" + URLEncoder.encode("univ", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("subject","UTF-8") + "=" + URLEncoder.encode("100394", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("majorSeq","UTF-8") + "=" + URLEncoder.encode("290", "UTF-8"));

        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
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
        System.out.println(sb.toString());
    }
}