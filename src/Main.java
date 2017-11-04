import java.io.IOException;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) throws IOException {
        Distributor distributor = new Distributor();
        String[][] codingReview = distributor.readFileToMatrix(System.getProperty("user.dir") + "/demo2_coding.csv");
        String[][] teamwork = distributor.readFileToMatrix(System.getProperty("user.dir") + "/demo2_teamwork.csv");
        String[][] names = distributor.readFileToMatrix(System.getProperty("user.dir") + "/GroupPhotos.csv");
        HashMap<String, EmailContent> emailContentHashMap = new HashMap<>();
        distributor.fillNetID(emailContentHashMap, names);
        distributor.fillReview(emailContentHashMap, codingReview, teamwork);
        String template = distributor.readTemplate();
//        distributor.fillDataInTemplate(String template, String[][] codingReview, );
    }

}
