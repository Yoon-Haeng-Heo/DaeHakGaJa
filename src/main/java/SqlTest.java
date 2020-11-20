import java.sql.*;
import java.util.Scanner;

public class SqlTest
{g
    public static void main(String[] args) throws SQLException
    {
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
//        System.out.println("Creating College, Student, Apply relations");
//        try {
//
//            String sql = "create table College(cName text, state text, enrollment integer);\n" +
//                    "create table Student(sID integer, sName text, GPA float, sizeHS integer);\n" +
//                    "create table Apply(sID integer, cName text, major text, decision char);";
//            st.executeUpdate(sql);
//        } catch (SQLException sqlEX) {
//
//            //System.out.println("hello");
//            System.out.println(sqlEX);
//        }
//        // 3개 테이블 생성: Create table문 이용
//        System.out.println("Inserting tuples to College, Student, Apply relations");
//        // 3개 테이블에 튜플 생성: Insert문 이용
//        try {
//            conn = DriverManager.getConnection(url, user, password);
//            st = conn.createStatement();
//            String sql = "\n" +
//                    "insert into College values('Stanford','CA',15000);\n" +
//                    "insert into College values('Berkeley','CA',36000);\n" +
//                    "insert into College values('MIT','MA',10000);\n" +
//                    "insert into College values('Cornell','NY',21000);\n" +
//                    "\n" +
//                    "insert into Student values(123,'Amy',3.9,1000);\n" +
//                    "insert into Student values(234,'Bob',3.6,1500);\n" +
//                    "insert into Student values(345,'Craig',3.5,500);\n" +
//                    "insert into Student values(456,'Doris',3.9,1000);\n" +
//                    "insert into Student values(567,'Edward',2.9,2000);\n" +
//                    "insert into Student values(678,'Fay',3.8,200);\n" +
//                    "insert into Student values(789,'Gary',3.4,800);\n" +
//                    "insert into Student values(987,'Helen',3.7,800);\n" +
//                    "insert into Student values(876,'Irene',3.9,400);\n" +
//                    "insert into Student values(765,'Jay',2.9,1500);\n" +
//                    "insert into Student values(654,'Amy',3.9,1000);\n" +
//                    "insert into Student values(543,'Craig',3.4,2000);\n" +
//                    "\n" +
//                    "insert into Apply values(123,'Stanford','CS','Y');\n" +
//                    "insert into Apply values(123,'Stanford','EE','N');\n" +
//                    "insert into Apply values(123,'Berkeley','CS','Y');\n" +
//                    "insert into Apply values(123,'Cornell','EE','Y');\n" +
//                    "insert into Apply values(234,'Berkeley','biology','N');\n" +
//                    "insert into Apply values(345,'MIT','bioengineering','Y');\n" +
//                    "insert into Apply values(345,'Cornell','bioengineering','N');\n" +
//                    "insert into Apply values(345,'Cornell','CS','Y');\n" +
//                    "insert into Apply values(345,'Cornell','EE','N');\n" +
//                    "insert into Apply values(678,'Stanford','history','Y');\n" +
//                    "insert into Apply values(987,'Stanford','CS','Y');\n" +
//                    "insert into Apply values(987,'Berkeley','CS','Y');\n" +
//                    "insert into Apply values(876,'Stanford','CS','N');\n" +
//                    "insert into Apply values(876,'MIT','biology','Y');\n" +
//                    "insert into Apply values(876,'MIT','marine biology','N');\n" +
//                    "insert into Apply values(765,'Stanford','history','Y');\n" +
//                    "insert into Apply values(765,'Cornell','history','N');\n" +
//                    "insert into Apply values(765,'Cornell','psychology','Y');\n" +
//                    "insert into Apply values(543,'MIT','CS','N');";
//            st.executeUpdate(sql);
//        } catch (SQLException sqlEX) {
//            System.out.println(sqlEX);
//        }
//        System.out.println("Continue? (Enter 1 for continue)");
//        scan.nextLine();
        System.out.println("Query 1");
        // Query 1을 실행: Select문 이용
        // Query 처리 결과는 적절한 Print문을 이용해 Display
        try {
            rs = st.executeQuery("SELECT * FROM College");
            ResultSetMetaData rsmd = rs.getMetaData();
            cnt = rsmd.getColumnCount();
        } catch (SQLException sqlEX) {
            System.out.println(sqlEX);
        }
        while(rs.next()){
            for(int i=1;i<=cnt;i++) {
                System.out.print(rs.getString(i)+ " ");
            }
            System.out.println();
        }
        System.out.println("Continue? (Enter 1 for continue)");
        scan.nextLine();
        System.out.println("Query 2");
        // Query 2 ~ Query 6에 대해 Query 1과 같은 방식으로 실행: Select문 이용
        // Query 처리 결과는 적절한 Print문을 이용해 Display
        try {
            rs = st.executeQuery("SELECT * FROM Student");
            ResultSetMetaData rsmd = rs.getMetaData();
            cnt = rsmd.getColumnCount();
        } catch (SQLException sqlEX) {
            System.out.println(sqlEX);
        }
        while(rs.next()){
            for(int i=1;i<=cnt;i++) {
                System.out.print(rs.getString(i)+ " ");
            }
            System.out.println();
        }

        System.out.println("Continue? (Enter 1 for continue)");
        scan.nextLine();
        System.out.println("Query 3");
        // Query 3
        // Query 2 ~ Query 6에 대해 Query 1과 같은 방식으로 실행: Select문 이용
        // Query 처리 결과는 적절한 Print문을 이용해 Display
        try {
            rs = st.executeQuery("SELECT * FROM Apply");
            ResultSetMetaData rsmd = rs.getMetaData();
            cnt = rsmd.getColumnCount();
        } catch (SQLException sqlEX) {
            System.out.println(sqlEX);
        }
        while(rs.next()){
            for(int i=1;i<=cnt;i++) {
                System.out.print(rs.getString(i)+ " ");
            }
            System.out.println();
        }

        System.out.println("Continue? (Enter 1 for continue)");
        scan.nextLine();
        System.out.println("Query 4");
        // Query 2 ~ Query 6에 대해 Query 1과 같은 방식으로 실행: Select문 이용
        // Query 처리 결과는 적절한 Print문을 이용해 Display
        try {
            rs = st.executeQuery("select *\n" +
                    "from Student S1\n" +
                    "where (select count(*) from Student S2\n" +
                    " where S2.sID <> S1.sID and S2.GPA = S1.GPA) =\n" +
                    " (select count(*) from Student S2\n" +
                    " where S2.sID <> S1.sID and S2.sizeHS = S1.sizeHS);");
            ResultSetMetaData rsmd = rs.getMetaData();
            cnt = rsmd.getColumnCount();
        } catch (SQLException sqlEX) {
            System.out.println(sqlEX);
        }
        while(rs.next()){
            for(int i=1;i<=cnt;i++) {
                System.out.print(rs.getString(i)+ " ");
            }
            System.out.println();
        }

        System.out.println("Continue? (Enter 1 for continue)");
        scan.nextLine();
        System.out.println("Query 5");
        // Query 2 ~ Query 6에 대해 Query 1과 같은 방식으로 실행: Select문 이용
        // Query 처리 결과는 적절한 Print문을 이용해 Display
        try {
            rs = st.executeQuery("select Student.sID, sName, count(distinct cName)\n" +
                    "from Student, Apply\n" +
                    "where Student.sID = Apply.sID\n" +
                    "group by Student.sID, sName;");
            ResultSetMetaData rsmd = rs.getMetaData();
            cnt = rsmd.getColumnCount();
        } catch (SQLException sqlEX) {
            System.out.println(sqlEX);
        }
        while(rs.next()){
            for(int i=1;i<=cnt;i++) {
                System.out.print(rs.getString(i)+ " ");
            }
            System.out.println();
        }

        System.out.println("Continue? (Enter 1 for continue)");
        scan.nextLine();
        System.out.println("Query 6");
        // Query 2 ~ Query 6에 대해 Query 1과 같은 방식으로 실행: Select문 이용
        // Query 처리 결과는 적절한 Print문을 이용해 Display
        try {
            rs = st.executeQuery("select major\n" +
                    "from Student, Apply\n" +
                    "where Student.sID = Apply.sID\n" +
                    "group by major\n" +
                    "having max(GPA) < (select avg(GPA) from Student);");
            ResultSetMetaData rsmd = rs.getMetaData();
            cnt = rsmd.getColumnCount();
        } catch (SQLException sqlEX) {
            System.out.println(sqlEX);
        }
        while(rs.next()){
            for(int i=1;i<=cnt;i++) {
                System.out.print(rs.getString(i)+ " ");
            }
            System.out.println();
        }
        System.out.println("Continue? (Enter 1 for continue)");
        scan.nextLine();
        System.out.println("Query 7");
        int sizeHS = 0;
        String major = "";
        String cName = "";
        // Query 7을 실행: Select문 이용
        // 사용자에게 sizeHS, major, cName 값으로 1000, CS, Stanford 입력 받음
        // 입력 받은 값을 넣어 Query를 처리하고
        // 결과는 적절한 Print문을 이용해 Display
        System.out.println("Enter sizeHS");
        sizeHS = scan.nextInt();
        System.out.println("Enter major");
        major = scan.next();
        System.out.println("Enter cName");
        cName = scan.next();
        try {
            rs = st.executeQuery("select sName, GPA\n" +
                    "from Student join Apply\n" +
                    "on Student.sID = Apply.sID\n" +
                    "where sizeHS < "+sizeHS+ " and major ='"+major+"' and cName = '"+cName+"';");
            ResultSetMetaData rsmd = rs.getMetaData();
            cnt = rsmd.getColumnCount();
        } catch (SQLException sqlEX) {
            System.out.println(sqlEX);
        }
        while(rs.next()){
            for(int i=1;i<=cnt;i++) {
                System.out.print(rs.getString(i)+ " ");
            }
            System.out.println();
        }
    }
}
