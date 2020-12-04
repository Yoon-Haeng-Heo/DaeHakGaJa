import org.json.simple.JSONObject;

import java.sql.*;
import java.util.Scanner;
public class SqlTest
{
    public static void main(String[] args) throws SQLException
    {
        home h = new home();

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
            String sql = "create table chart(id bigint, major_id bigint, male_ratio decimal, female_ratio decimal, avg_salary integer, satisfaction decimal, employment_rate decimal, applicant_rate decimal);\n" +
                    "create table legend(id bigint, name text, majorSeq bigint[]);\n" +
                    "create table major(id bigint, name text, summary text, main_subject varchar(50)[], job text, legend_id bigint, qualification text);\n" +
                    "create table university(id bigint, name text, area text);\n" +
                    "create table university_major(id bigint, university_id bigint, major_id bigint);";

            st.executeUpdate(sql);

            // insert legend
            System.out.println("Inserting tuples to Legend");
            sql = "insert into legend values('100391', '인문계열', '{29, 48, 56, 10145, 69, 93, 10053, 122, 124, 132, 134, 163, 164, 165, 166, 169, 10030, 10031, 179, 188}');\n" +
                    "insert into legend values('100392', '사회계열', '{8, 22, 10139, 23, 24, 25, 26, 27, 45, 46, 50, 54, 10141, 10143, 70, 10148, 10007, 74, 373, 10026}');\n" +
                    "insert into legend values('100393', '교육계열', '{10006, 44, 57, 58, 59, 68, 32, 108, 123, 174, 183, 225, 238, 263, 299, 10033, 331, 346, 350, 365}');\n" +
                    "insert into legend values('100394', '공학계열', '{16, 17, 18, 20, 21, 30, 35, 36, 55, 10142, 61, 10147, 10142, 61, 10147, 10038, 79, 85, 86, 87, 88, 89, 290}');\n" +
                    "insert into legend values('100395', '자연계열', '{7, 10144, 10146, 96, 105, 106, 111, 126, 10120, 177, 190, 245, 260, 261, 262, 10048, 265, 267, 296}');\n" +
                    "insert into legend values('100396', '의약계열', '{9, 10, 43, 175, 176, 196, 10012, 210, 215, 255, 288, 341, 10016, 10046, 10045, 399, 10014, 402, 404, 405}');\n" +
                    "insert into legend values('100397', '예체능계열', '{12, 11, 28, 10041, 33, 34, 37, 38, 39, 41, 49, 51, 10140, 67, 77, 78, 94, 113, 114, 120}');";

           st.executeUpdate(sql);

            rs = st.executeQuery("select * from Legend;");

            while(rs.next()) {
                String id = rs.getString(1);
                String name = rs.getString(2);
                String majorSeq = rs.getString(3);
                majorSeq = majorSeq.substring(1, majorSeq.length()-1);
                String[] majorArr = majorSeq.split(",");
//                for(String str : majorArr){
//                    System.out.println(str);
//                }
                System.out.println();
                System.out.println(majorSeq);
                
            }

            // load api
            LoadAPI api = new LoadAPI("100394","290");

            // insert major
            System.out.println("Inserting tuples to Major");
            int um_index = 1;
//            System.out.println(api.getJa());
            for (int index = 0; index < api.getUnivArray().size(); index++) {
                JSONObject univObject = (JSONObject) api.getUnivArray().get(index);
                st.executeUpdate("insert into university values('"+(index+1)+"','"+univObject.get("schoolName")+"', '"+ univObject.get("area") +"');");
                st.executeUpdate("insert into university_major values('"+(um_index++)+"', '"+(index+1)+"', '"+"290"+"')");
            }

            System.out.println("university done");

            System.out.println("summary ----------");
            System.out.println(api.getSummary());

            System.out.println("job ----------");
            System.out.println(api.getJob());

            System.out.println("qualification ----------");
            System.out.println(api.getQualification());

            System.out.println("univarray ----------");
            System.out.println(api.getUnivArray());

            System.out.println("mainsubject ----------");
            System.out.println(api.getMainSubject());

            System.out.println("majorid ----------");
            System.out.println(api.getMajorSeq());

            System.out.println("majorSeq --------");
            System.out.println(api.getMajorName());

            System.out.println("legend_id");
            System.out.println(api.getLegendId());

//            st.executeUpdate("insert into major values('1', '소프트웨어학과','"+api.getSummary()+"','"+api.getMainSubject()+"', '"+api.getJob()+"', '1', '"+api.getQualification()+"')");
            // "create table major(id bigint, name text, summary text, main_subject text, job text, legend_id bigint, qualification text);\n" +

            st.executeUpdate("insert into major (name, summary, job) values('소프트웨어', '"+api.getSummary()+"', '"+api.getJob()+"')");
            st.executeUpdate("insert into major (id, name, summary, main_subject, job, legend_id, qualification) values('" +
                    api.getMajorSeq() + "', '" + api.getMajorName() + "', '" + api.getSummary() + "', '" + api.getMainSubject() +
                    "', '" + api.getJob() + "', '" + api.getLegendId() + "', '"+ api.getQualification() +"')");
            System.out.println(api.getMainSubject());
            System.out.println(api.getMainSubject());
            System.out.println(api.getMainSubject());

            System.out.println("All done.");

        } catch (SQLException sqlEX) {
            System.out.println("ERROR!");

        }
    }
}

