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

import org.json.simple.parser.ParseException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class LoadAPI {
    JSONObject jo = null;
    JSONArray ja = null;
    JSONArray univArray = null;
    JSONArray mainSubject = null;
    JSONArray chartdata = null;
    JSONArray gender = null;
    String summary = "";
    String job = "";
    String qualification = "";
    String majorSeq = "";
    String majorName ="";
    JSONArray avg_salary = null;
    JSONArray satisfaction = null;
    String employmentRate = "";
    float applicantRate = 0;
    int legend_id;
    JSONArray enter_field = null;
    ArrayList<String> popular = new ArrayList<String>();
    ArrayList<String> bookmark = new ArrayList<String>();
    public LoadAPI(String subject, String majorSeq){
        try {
            StringBuilder urlBuilder = new StringBuilder("http://www.career.go.kr/cnet/openapi/getOpenApi"); /*URL*/
            urlBuilder.append("?" + URLEncoder.encode("apiKey", "UTF-8") + "=" + URLEncoder.encode("327f658d87b31c1ea2c7d1dd130a05a7", "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("svcType", "UTF-8") + "=" + URLEncoder.encode("api", "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("svcCode", "UTF-8") + "=" + URLEncoder.encode("MAJOR_VIEW", "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("contentType", "UTF-8") + "=" + URLEncoder.encode("json", "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("gubun", "UTF-8") + "=" + URLEncoder.encode("univ_list", "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("univSe", "UTF-8") + "=" + URLEncoder.encode("univ", "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("subject", "UTF-8") + "=" + URLEncoder.encode(subject, "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("majorSeq", "UTF-8") + "=" + URLEncoder.encode(majorSeq, "UTF-8"));

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
                JSONObject contentObj = (JSONObject) contentArray.get(0);
                this.majorSeq = majorSeq;
                legend_id = Integer.parseInt(subject);
                // Chart table의 컬럼
                enter_field = (JSONArray) contentObj.get("enter_field");
                //jo = contentObj;
                chartdata = (JSONArray) contentObj.get("chartData");
                JSONObject ja2 =(JSONObject) chartdata.get(0);
                gender = (JSONArray) ja2.get("gender");

                avg_salary = (JSONArray) ja2.get("avg_salary");
                satisfaction = (JSONArray) ja2.get("satisfaction");
                JSONArray temp = (JSONArray)ja2.get("employment_rate");
                JSONArray tempApplicant = (JSONArray) ja2.get("applicant");
                float allapplicant = Float.parseFloat((String) ((JSONObject) tempApplicant.get(0)).get("data"));
                float applicant = Float.parseFloat((String) ((JSONObject) tempApplicant.get(1)).get("data"));
                applicantRate = applicant / allapplicant ;
                JSONObject temp2 = (JSONObject) temp.get(0);
                employmentRate = (String) temp2.get("data");
                //  gender = (JSONObject) ja2.get("gender").get(0) ;

                // Major table의 column -> name, summary, main_subject, job, legend_id, qualification
                majorName = (String) contentObj.get("major");
                summary = (String) contentObj.get("summary");
                job = (String) contentObj.get("job");
                qualification = (String) contentObj.get("qualifications");
                univArray = (JSONArray) contentObj.get("university");
                mainSubject = (JSONArray) contentObj.get("main_subject");
                System.out.println(mainSubject+"################################");
                temp = (JSONArray) contentObj.get("lstVals");
                JSONArray temp3 = (JSONArray) ((JSONObject) temp.get(0)).get("popular");
                JSONArray temp4 = (JSONArray) ((JSONObject) temp.get(1)).get("bookmark");
                popular.add((String) ((JSONObject) temp3.get(0)).get("CD_NM"));
                popular.add((String) ((JSONObject) temp3.get(1)).get("CD_NM"));
                popular.add((String) ((JSONObject) temp3.get(2)).get("CD_NM"));

                bookmark.add((String) ((JSONObject) temp4.get(0)).get("CD_NM"));
                bookmark.add((String) ((JSONObject) temp4.get(1)).get("CD_NM"));
                bookmark.add((String) ((JSONObject) temp4.get(2)).get("CD_NM"));

                System.out.println(bookmark+"################################");
                for(int a=0; a< univArray.size(); a++){
                    JSONObject univObject = (JSONObject) univArray.get(a);
//                    System.out.println("area "+univObject.get("area"));
//                    System.out.println("majorName " + univObject.get("majorName"));
//                    System.out.println("schoolName "+ univObject.get("schoolName"));
                }
            }catch(ParseException e){
                e.printStackTrace();
            }
        }catch(IOException ex){
            System.out.println(ex);
        }
    }
    public JSONObject getJo() {
        return jo;
    }
    public JSONArray getUnivArray() {
        return univArray;
    }
    public String getSummary(){
        return summary;
    }
    public JSONArray getMainSubject(){
        return mainSubject;
    }
    public String getJob() {
        return job;
    }
    public String getQualification() {
        return qualification;
    }
    public JSONArray getGender(){ return gender; }
    public float getMaleRatio(){
        return Float.parseFloat((String)((JSONObject) gender.get(0)).get("data"));
    }
    public float getFemaleRatio(){
        return Float.parseFloat((String)((JSONObject) gender.get(1)).get("data"));
    }
    public int getLegendId(){ return legend_id; }
    public String getMajorSeq() {
        return majorSeq;
    }
    public String getMajorName() { return majorName; }
    public JSONArray getAvg_salary(){ return avg_salary;}
    public JSONArray getSatisfaction(){ return satisfaction;}
    public String getEmploymentRate(){ return employmentRate;}
    public float getApplicantRate(){return applicantRate;}
    public JSONArray getEnter_field(){return enter_field;}

    public ArrayList<String> getPopular() {
        return popular;
    }
    public ArrayList<String> getBookmark() {
        return bookmark;
    }
}



