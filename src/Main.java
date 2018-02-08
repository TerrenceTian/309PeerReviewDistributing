import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    static String TA_NAME_VALUE = ""; // Your full name
     static String TA_EMAIL_VALUE = ""; // xxx@iastate.edu
     static String TA_EMAIL_PWD = ""; // password
     static String TEAM_PREFIX = ""; // initial, e.g. YT
     static String EMAIL_DOMAIN = "@iastate.edu";
     static String SUBJECT = "";

    public static void main(String[] args) throws IOException {
        System.out.println("This is 309 peer evaluation distributor, answer 4 questions and it will send out emails to your students. ");
        BufferedReader buf = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter your full name: ");
        TA_NAME_VALUE = buf.readLine();
        System.out.println("Enter your iastate email (xxx@iastate.edu): ");
        TA_EMAIL_VALUE = buf.readLine();
        System.out.println("Enter your iastate email password (check carefully): ");
        TA_EMAIL_PWD = buf.readLine();
        System.out.println("Enter your initials, e.g. YT: ");
        TEAM_PREFIX = buf.readLine();
        System.out.println("Enter email subject, e.g. Demo-1 Peer Review: ");
        SUBJECT = buf.readLine();
        System.out.println("All correct & GO? (Y/N)");
        String yesOrNo = buf.readLine().toLowerCase();

        if("y".equals(yesOrNo) || "yes".equals(yesOrNo)){
            Distributor distributor = new Distributor();

            String[][] codingReview = new String[0][0];
            try {
                codingReview = distributor.readFileToMatrix(System.getProperty("user.dir") + "/demo_coding.csv");
            } catch (IOException e){
                System.out.println("Didn't find demo_coding.csv");
            }

            String[][] teamwork = new String[0][0];
            try {
                teamwork = distributor.readFileToMatrix(System.getProperty("user.dir") + "/demo_teamwork.csv");
            } catch (IOException e){
                System.out.println("Didn't find demo_teamwork.csv");
            }

            String[][] names = new String[0][0];
            try {
                names = distributor.readFileToMatrix(System.getProperty("user.dir") + "/GroupPhotos.csv");
            } catch (IOException e){
                System.out.println("Didn't find GroupPhotos.csv");
            }

            HashMap<String, EmailContent> emailContentHashMap = new HashMap<>();
            distributor.fillNetID(emailContentHashMap, names, TEAM_PREFIX);
            distributor.fillReview(emailContentHashMap, codingReview, teamwork);
            String template = distributor.readTemplate();
            distributor.fillDataInTemplate(emailContentHashMap, template);
            System.out.println("sending");
            distributor.send(emailContentHashMap);
            System.out.println("done");
        }

    }

}
