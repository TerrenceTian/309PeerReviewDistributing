import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Distributor distributor = new Distributor();
        String[][] matrix = distributor.readFileToMatrix("eeee/a.csv");
        String[][] matrix2 = distributor.readFileToMatrix2("******.csv");
        String template = distributor.readTemplate();
    }

}
