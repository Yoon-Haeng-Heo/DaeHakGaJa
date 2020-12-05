import java.sql.*;

public class display {
    String result ="";

    public display(int id, String type ) {
        try {
            home h = new home();
            Connection conn = null;
            Statement st = null;
            ResultSet rs = null;
            PreparedStatement pstmt = null;

            conn = DriverManager.getConnection(h.getUrl(), h.getUser(), h.getPassword());
            st = conn.createStatement();

            if (type == "major") {
                rs = st.executeQuery("select * from Major where id=" + id + ";");
                
                if(rs.next()) {

                    // legend 구하기
                    ResultSet sub_rs = null;
                    String legend_id = rs.getString("legend_id");

                    sub_rs = st.executeQuery("select name from Legend where id="+legend_id+";");

                    String legend = null;
                    if(sub_rs.next()) { legend = sub_rs.getString("name");}
                    String name = rs.getString("name");

                    // bookmark 구하
                    String bookmark = rs.getString("bookmark");
                    bookmark = bookmark.substring(1, bookmark.length()-1);
                    String[] arr = bookmark.split(",");
                    String new_bookmark = "";
                    for (int i = 0; i < arr.length; i++) {
                        new_bookmark += (i+1)+"순위: "+arr[i]+"\n";
                    }

                    // summary 구하기
                    String summary = rs.getString("summary");
                    int max_width = 50;
                    String new_summary = "";
                    for (int i =0; i<(summary.length() / max_width)+1; i++) {
                        int start = i*max_width;
                        int end = (i+1)*max_width < summary.length() ? (i+1)*max_width : summary.length();
                        new_summary += summary.substring(start, end)+"\n";
                    }

                    // university 구하기
                    String university = "";
                    sub_rs = st.executeQuery("SELECT university.name FROM university INNER JOIN university_major ON university.id = university_major.university_id WHERE university_major.major_id = "+id+";");
                    while(sub_rs.next()) {
                        university += sub_rs.getString("name")+" ";
                    }
                    result = "=================================================================\n" +
                            "[학과명]\n" +
                            name+"\n\n" +
                            "[계열]\n" +
                            legend+"\n\n" +
                            "[직업 선호 가치]\n" +
                            new_bookmark+"\n" +
                            "[주요 학교]\n" +
                            university+"\n\n" +
                            "[학과 정보]\n" +
                            new_summary+"\n" +
                            "=================================================================\n";
                }
            } else if (type == "university") {

            }
        } catch (SQLException sqlEX) {
            System.out.println(sqlEX);
            System.out.println("ERROR!");
        }
    }

    public String getResult() { return result; }

}
