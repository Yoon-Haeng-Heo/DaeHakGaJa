import java.util.Scanner;

public class survey {
    public survey(){
        Scanner scan = new Scanner(System.in);
        char[] user_answer = new char[6];

        String guide_message = "해당하는 항목을 입력해주세요. 예를 들어, a)에 해당한다면 'a'를 입력해주세요.";

        String[] question = new String[6];
        String[][] answer = new String[6][3];

        // legend 분기
        question[1] = "1. 대화 중 갈등이 생겼을 때 어떤식으로 해결하는 편인가요?";
        answer[1][1] = "a) 논리로 해결하려 한다.";
        answer[1][2] = "b) 공감을 통해 해결하려 한다.";

        // chart_data에서 졸업 후 첫 직장분야로 분기
        question[2] = "2. 전공에 대해 어떤 생각을 가지고 계신가요?";
        answer[2][1] = "a) 전공을 반드시 살리는 학과에 가고 싶다.";
        answer[2][2] = "b) 전공을 굳이 살리지 않아도 괜찮다.";

        // lst value에서 사회적 인정 or 능력 발휘로 분기
        question[3] = "3. 어떤 것에 더 가치를 두시나요?";
        answer[3][1] = "a) 세상에 기여";
        answer[3][2] = "b) 나의 행복";

        // employment_rate로 분기
        question[4] = "4. 어떤 직업을 선택하고 싶으신가요?";
        answer[4][1] = "a) 불안정 할지라도 좋아하는 일";
        answer[4][2] = "b) 좋아하진 않더라도 안정적인 일";

        // avg_salary로 분기
        question[5] = "5. 취업을 한다면, 무엇이 더 중요한가요?";
        answer[5][1] = "a) 워라밸";
        answer[5][2] = "b) 임금";

        for(int question_index=1; question_index<=5; question_index++){
            System.out.println(question[question_index]);
            for(int answer_index=1; answer_index<=2; answer_index++){
                System.out.println(answer[question_index][answer_index]);
            }
            while(true){
                user_answer[question_index] = scan.next().charAt(0);
                if(user_answer[question_index] == 'a'){
                    // rs = st.executeUpdate("select major from major where legend_id in (100394, 100395, 100396);"
                    break;
                }
                else if(user_answer[question_index] == 'b'){
                    // rs = st.executeUpdate("select major from major where legend_id in (100391, 100392, 100393, 100397);"
                    break;
                }//select name, bookmark from major where '안정성'=ANY(bookmark) order by (select array_position(major.bookmark, '안정성')) asc;
                System.out.println("올바른 값을 입력해주세요.");
            }
        }
        // user_answer[1] == 'a' ? "(100394, 100395, 100396)" : "(100391, 100392, 100393, 100397)"
        // user_answer[2] == 'a' ? "
    }
}
