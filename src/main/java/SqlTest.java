import java.sql.*;
import java.util.Scanner;

public class SqlTest
{
    public static void main(String[] args) throws SQLException
    {
        LoadAPI api = new LoadAPI("100394","290");
        home h = new home();
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        PreparedStatement pstmt = null;

        Scanner scan = new Scanner(System.in);
        System.out.println("SQL Programming Test");
        System.out.println("Connecting PostgreSQL database");
        int cnt = 0;
        try {
            conn = DriverManager.getConnection(h.getUrl(), h.getUser(),h.getPassword());
            st = conn.createStatement();
            rs = st.executeQuery("SELECT VERSION()");

            if (rs.next())
                System.out.println(rs.getString(1));
        }catch(SQLException sqlEX){
            System.out.println(sqlEX);
        }
        System.out.println("Drop all relations!");
        try {
            String sql = "drop table universities CASCADE;\n"+"drop table university_majors;\n"+"drop table charts;\n"+"drop table legends CASCADE;\n"+"drop table majors;\n";
            st.executeUpdate(sql);
        } catch (SQLException sqlEX) {

            //System.out.println("hello");
            System.out.println(sqlEX);
        }
        System.out.println("Create relations");
        try {

            String sql = "create table charts(id bigint, major_id bigint, male_ratio decimal, female_ratio decimal, avg_salary integer, satisfaction decimal, employment_rate decimal, applicant_rate decimal);\n" +
                    "create table legends(id bigint, name text);\n" +
                    "create table majors(id bigint, name text, summary text, main_subject text, enter_field text, job text, legend_id bigint);\n" +
                    "create table universities(id bigint, name text, area text);\n" +
                    "create table university_majors(id bigint, major_id bigint, university_id bigint);";
            st.executeUpdate(sql);
        } catch (SQLException sqlEX) {

            //System.out.println("hello");
            System.out.println(sqlEX);
        }
    }
}
