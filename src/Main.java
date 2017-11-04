import java.io.IOException;
import java.util.HashMap;

public class Main {
    final static String TA_NAME_VALUE = "";
    final static String EMAIL_DOMAIN = "@iastate.edu";

    public static void main(String[] args) throws IOException {
        Distributor distributor = new Distributor();
        String[][] codingReview = distributor.readFileToMatrix(System.getProperty("user.dir") + "/demo2_coding.csv");
        String[][] teamwork = distributor.readFileToMatrix(System.getProperty("user.dir") + "/demo2_teamwork.csv");
        String[][] names = distributor.readFileToMatrix(System.getProperty("user.dir") + "/GroupPhotos.csv");
        HashMap<String, EmailContent> emailContentHashMap = new HashMap<>();
        distributor.fillNetID(emailContentHashMap, names);
        distributor.fillReview(emailContentHashMap, codingReview, teamwork);
        String template = distributor.readTemplate();
        distributor.fillDataInTemplate(emailContentHashMap, template);
        new SendEmail(emailContentHashMap).send();
//        distributor.fillDataInTemplate(String template, String[][] codingReview, );
    }

}
