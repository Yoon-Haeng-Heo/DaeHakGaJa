import org.json.simple.JSONObject;

import java.sql.*;
import java.util.Scanner;
public class SqlTest
{
    public static void main(String[] args) throws SQLException
    {
        LoadAPI api = new LoadAPI("100394","290");
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
            System.out.println("Create relations");

            String sql = "create table chart(id bigint, major_id bigint, male_ratio decimal, female_ratio decimal, avg_salary integer, satisfaction decimal, employment_rate decimal, applicant_rate decimal);\n" +
                    "create table legend(id bigint, name text);\n" +
                    "create table major(id bigint, name text, summary text, main_subject varchar(50)[], job text, legend_id bigint, qualification text);\n" +
                    "create table university(id bigint, name text, area text);\n" +
                    "create table university_major(id bigint, university_id bigint, major_id bigint);";

            st.executeUpdate(sql);

            // insert legend
            System.out.println("Inserting tuples to Legend");
            sql = "insert into legend values('100391', '인문계열');\n" +
                    "insert into legend values('100392', '사회계열');\n" +
                    "insert into legend values('100393', '교육계열');\n" +
                    "insert into legend values('100394', '공학계열');\n" +
                    "insert into legend values('100395', '자연계열');\n" +
                    "insert into legend values('100396', '의약계열');\n" +
                    "insert into legend values('100397', '예체능계열');";

            st.executeUpdate(sql);

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