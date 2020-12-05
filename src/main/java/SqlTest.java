import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
public class SqlTest
{
    public static void main(String[] args) throws SQLException
    {
        home h = new home();
        LoadAPI api;
        LoadMajorAPI majorAPI;

        try {
            Connection conn = null;
            Statement st = null;
            ResultSet rs = null;
            PreparedStatement pstmt = null;

            Scanner scan = new Scanner(System.in);
            System.out.println("SQL Programming Test");
            System.out.println("Connecting PostgreSQL database");
            conn = DriverManager.getConnection(h.getUrl(), h.getUser(), h.getPassword());
            st = conn.createStatement();
            rs = st.executeQuery("SELECT VERSION()");

            if (rs.next()) System.out.println(rs.getString(1));

            System.out.println("Drop all relations!");

            String sql = "drop table university CASCADE;\n" + "drop table chart;\n" + "drop table legend CASCADE;\n" + "drop table major;\n" + "drop table university_major;";

            st.executeUpdate(sql);

        } catch(SQLException sqlEX) {
            System.out.println("DROP TABLE ERROR");
        }

        try {
            Connection conn = null;
            Statement st = null;
            ResultSet rs = null;
            PreparedStatement pstmt = null;

            Scanner scan = new Scanner(System.in);
            conn = DriverManager.getConnection(h.getUrl(), h.getUser(), h.getPassword());
            st = conn.createStatement();

            // create table
            System.out.println("Create relations");
            String sql = "create table chart(id serial primary key, major_id bigint, male_ratio decimal, female_ratio decimal, avg_salary decimal, satisfaction_data varchar(10)[], satisfaction_item varchar(30)[], employment_rate decimal, applicant_rate decimal, field_data varchar(10)[], field_item varchar(30)[]);\n" +
                    "create table legend(id bigint primary key, name text, majorSeq bigint[]);\n" +
                    "create table major(id bigint primary key, name text, summary text, main_subject varchar(50)[], job text, legend_id bigint, qualification text, bookmark varchar(10)[]);\n" +
                    "create table university(id bigint primary key, name text, area text);\n" +
                    "create table university_major(id bigint primary key, university_id bigint, major_id bigint);";

            st.executeUpdate(sql);

            // insert legend
            System.out.println("Inserting tuples to Legend");
            String[] subjectArr = new String[]{"100391", "100392", "100393", "100394", "100395", "100396", "100397"};
            String[] subjectName = new String[]{"인문계열", "사회계열", "교육계열", "공학계열", "자연계열", "의약계열","예체능계열"};
            for(int a = 0; a <subjectArr.length; a++){
                majorAPI = new LoadMajorAPI(subjectArr[a]);
                ArrayList<String> MajorSeqArr = majorAPI.getMajorSeqArr();
                String[] arr = MajorSeqArr.toArray(new String[MajorSeqArr.size()]);
                String seq = Arrays.toString(arr);
                seq = seq.substring(1, seq.length()-1);
                seq = "{"+seq+"}";
//                System.out.println(seq);
                sql = "insert into legend values('"+subjectArr[a]+"' , '"+subjectName[a]+"', '"+ seq +"');";
                st.executeUpdate(sql);
            }


            rs = st.executeQuery("select * from Legend;");

            int um_index = 1;
            int univ_index = 1;
            while(rs.next()) {
                String id = rs.getString(1);
                String name = rs.getString(2);
                String array = rs.getString(3);
                array = array.substring(1, array.length()-1);
                String[] majorArr = array.split(",");
                System.out.println("Subject: "+name+"("+id+") start!");
                for(String majorSeq : majorArr) {
                    System.out.println("Subject: "+ name+", majorSeq: "+majorSeq);
                    api = new LoadAPI(id, majorSeq);
                    if(api.isNull) {
                        System.out.println(majorSeq+" Skip!!");
                    } else {
                        // insert major
                        System.out.println("Inserting tuples to Major");
                        for(int a =0;a<api.getFields().size();a++){
                            System.out.println(api.getFields().get(a).getData() + " " + api.getFields().get(a).getItem());
                            System.out.println("#########################");
                        }
                        st.executeUpdate("insert into major (id, name, summary, main_subject, job, legend_id, qualification, bookmark) values('" +
                                Integer.parseInt(api.getMajorSeq()) + "', '" + api.getMajorName() + "', '" + api.getSummary() + "', '" + ArrayToString(ObjectToArray(api.getMainSubject())) +
                                "', '" + api.getJob() + "', " + api.getLegendId() + ", '"+ api.getQualification() + "', '" + ArrayToString(api.getBookmark()) +"');");
                        // insert chart
                        // "create table chart(id serial primary key, major_id bigint, male_ratio decimal, female_ratio decimal, avg_salary integer,
                        // satisfaction_data varchar(10)[], satisfaction_item varchar(30)[], employment_rate decimal, applicant_rate decimal,
                        // field_data varchar(10)[], field_item varchar(30)[]);\n" +
                        String insertChart = "insert into chart values(default,'"+Integer.parseInt(api.getMajorSeq()) + "' , '"+api.getMaleRatio() + "' , '" +api.getFemaleRatio()+"', '" + api.getAvg_salary() + "', '"
                                +ArrayToString(fieldToDataArray(api.getSatisfactions())) + "', '" + ArrayToString(fieldToItemArray(api.getSatisfactions()))+ "' , '"
                                +api.getEmploymentRate() + "', '"+api.getApplicantRate()+"', '"+ ArrayToString(fieldToDataArray(api.getFields())) + "', '"+ ArrayToString(fieldToItemArray(api.getFields()))+"'); ";
                        st.executeUpdate(insertChart);
                        System.out.println("Chart insert success!!!!!");
                        // university, university_major 넣는 부분
                        for (int index = 0; index < api.getUnivArray().size(); index++) {
                            JSONObject univObject = (JSONObject) api.getUnivArray().get(index);
                            ResultSet sub_rs = null;
                            sub_rs = st.executeQuery("SELECT name FROM university WHERE name='"+univObject.get("schoolName")+"';");

                            // case 1: university 테이블에 없는 대학교일 때
                            if (!sub_rs.next()) {
                                // insert university
                                st.executeUpdate("insert into university values('"+(univ_index)+"','"+univObject.get("schoolName")+"', '"+ univObject.get("area") +"');");
                                univ_index++;

                                // insert university_major
                                st.executeUpdate("insert into university_major values('"+(um_index)+"', '"+(univ_index)+"', '"+(api.getMajorSeq())+"')");
                                um_index++;
                            } else {
                                sub_rs = st.executeQuery("select id from university_major where university_id='"+(univ_index)+"' and major_id='"+(api.getMajorSeq())+"';");

                                // case 2: university 테이블에 대학은 있는데 중간테이블엔 major와 연결이 안되어 있을 때
                                if (!sub_rs.next()) {
                                    // insert university_major
                                    st.executeUpdate("insert into university_major values('"+(um_index)+"', '"+(univ_index)+"', '"+(api.getMajorSeq())+"')");
                                    um_index++;
                                }
                            }
                        }
                    }
                }
            }

            System.out.println("All done.");

        } catch (SQLException sqlEX) {
            System.out.println(sqlEX);
            System.out.println("ERROR!");
        }
    }
    public static ArrayList<String> fieldToDataArray(ArrayList<LoadAPI.field> al){
        ArrayList<String> returnal = new ArrayList<String>();
        for(int i=0;i<al.size();i++){
            returnal.add(al.get(i).getData());
        }
        return returnal;
    }

    public static ArrayList<String> fieldToItemArray(ArrayList<LoadAPI.field> al){
        ArrayList<String> returnal = new ArrayList<String>();
        for(int i=0;i<al.size();i++){
            returnal.add(al.get(i).getItem());
        }
        return returnal;
    }
    //JSONArray -> ArrayList로
    public static ArrayList<String> ObjectToArray(JSONArray ja){
        ArrayList<String> al = new ArrayList<String>();
        for(int i = 0 ;i <ja.size();i++){
            al.add((String)ja.get(i));
        }
        return al;
    }
    //대괄호 배열을 중괄호로
    public static String ArrayToString(ArrayList<String> al){
        String[] arr = al.toArray(new String[al.size()]);
        String seq = Arrays.toString(arr);
        seq = seq.substring(1, seq.length()-1);
        seq = "{"+seq+"}";
//        System.out.println(seq);
        return seq;
    }
}

