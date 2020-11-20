import java.sql.*;
import java.util.Scanner;

public class SqlTest
{
    public static void main(String[] args) throws SQLException
    {
        LoadAPI api = new LoadAPI();
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        PreparedStatement pstmt = null;

        Scanner scan = new Scanner(System.in);
        System.out.println("SQL Programming Test");

        System.out.println("Connecting PostgreSQL database");
        String url = "jdbc:postgresql://localhost:5432/daehakgaja_dev";
        String user = "haeng";
        String password = "dbsgod4574!";
        int cnt = 0;
        try {
            conn = DriverManager.getConnection(url, user, password);
            st = conn.createStatement();
            rs = st.executeQuery("SELECT VERSION()");

            if (rs.next())
                System.out.println(rs.getString(1));
        }catch(SQLException sqlEX){
            System.out.println(sqlEX);
        }
        System.out.println("Creating College, Student, Apply relations");
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
