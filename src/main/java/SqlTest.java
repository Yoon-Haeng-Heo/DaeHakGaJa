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
                    "create table major(id bigint, name text, summary text, main_subject text, job text, legend_id bigint, qualification text);\n" +
                    "create table university(id bigint, name text, area text);\n" +
                    "create table university_major(id bigint, university_id bigint, major_id bigint);";

            st.executeUpdate(sql);

            System.out.println("Inserting tuples to Chart, Legend, Major, University");

//            System.out.println(api.getJa());
            for(int index = 0; index < api.getUnivArray().size(); index++) {
                JSONObject univObject = (JSONObject) api.getUnivArray().get(index);
                st.executeUpdate("insert into University values('"+(index+1)+"','"+univObject.get("schoolName")+"', '"+ univObject.get("area") +"');");
            }

            System.out.println("All done.");

        } catch (SQLException sqlEX) {
            System.out.println("ERROR!");

        }
    }
}