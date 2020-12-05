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

                    // bookmark 구하기
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
                        university += sub_rs.getString("name")+"\n";
                    }
                    result = "=================================================================\n" +
                            "[학과명]\n" +
                            name+"\n\n" +
                            "[계열]\n" +
                            legend+"\n\n" +
                            "[직업 선호 가치]\n" +
                            new_bookmark+"\n" +
                            "[주요 학교]\n" +
                            university+"\n" +
                            "[학과 정보]\n" +
                            new_summary+"\n" +
                            "=================================================================\n";
                }
            } else if (type == "chart") {
                rs = st.executeQuery("select * from Chart where major_id=" + id + ";");

                if(rs.next()) {

                    // major 구하기
                    ResultSet sub_rs = null;
//                    String major_id = rs.getString("major_id");

                    sub_rs = st.executeQuery("select name from Major where id="+id+";");

                    String major = null;
                    if(sub_rs.next()) { major = sub_rs.getString("name"); }

                    // male_ratio & female_ratio
                    String male_ratio, female_ratio;
                    male_ratio = rs.getString("male_ratio");
                    female_ratio = rs.getString("female_ratio");

                    // avg_salary
                    String avg_salary = rs.getString("avg_salary");

                    // satisfaction_data
                    String satisfaction_data = rs.getString("satisfaction_data");
                    String satisfaction_item = rs.getString("satisfaction_item");

                    satisfaction_data.substring(1, satisfaction_data.length()-1);
                    satisfaction_item.substring(1, satisfaction_item.length()-1);

                    String[] data_arr = satisfaction_data.split(",");
                    String[] item_arr = satisfaction_item.split(",");
                    String satisfaction = "";
                    for (int i = 0; i < item_arr.length; i++) {
                        satisfaction += item_arr[i]+"\t";
                    }
                    satisfaction += "\n";
                    for (int i = 0; i < data_arr.length; i++) {
                        satisfaction += data_arr[i]+"\t";
                    }

                    // employment
                    String employment_rate = rs.getString("employment_rate");

                    // applicant
                    Float applicant_rate = rs.getFloat("applicant_rate");

                    // field
                    String field_data = rs.getString("field_data");
                    String field_item = rs.getString("field_item");

                    field_data.substring(1, field_data.length()-1);
                    field_item.substring(1, field_item.length()-1);

                    String[] data_arr_2 = field_data.split(",");
                    String[] item_arr_2 = field_item.split(",");
                    String field = "";
                    for (int i = 0; i < item_arr_2.length; i++) {
                        field += item_arr_2[i]+"\t";
                    }
                    field += "\n";
                    for (int i = 0; i < data_arr_2.length; i++) {
                        field += data_arr_2[i]+"\t";
                    }

                    result = "=================================================================\n" +
                            "[학과명]\n" +
                           major+"\n\n" +
                            "[남여 비율]\n" +
                            "  남  /  여  \n" +
                            male_ratio+" / "+female_ratio+"\n\n" +
                            "[경쟁률]\n" +
                            Math.round(applicant_rate*100)/100.0+":1\n\n" +
                            "[취업률]\n" +
                            employment_rate+"\n\n" +
                            "[신입 평균 월급]\n" +
                            avg_salary+" 만원\n\n" +
                            "[만족도]\n" +
                            satisfaction+"\n\n" +
                            "[취업 분야]\n" +
                            field+"\n\n" +
                            "=================================================================\n";
                }
            }
        } catch (SQLException sqlEX) {
            System.out.println(sqlEX);
            System.out.println("ERROR!");
        }
    }

    public String getResult() { return result; }

}
