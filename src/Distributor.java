import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Distributor {
    public String[][] readFileToMatrix(String filePath) throws IOException {
        List<String[]> list = new ArrayList<>();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
        String line;
        while((line = bufferedReader.readLine()) != null){
            String[] elements = line.split(",");
            list.add(elements);
        }
        int width = list.get(0).length;
        String[][] matrix = new String[list.size()][width];
        for (int i = 0; i < list.size(); i++){
            for(int j = 0; j < width; j++){
                matrix[i][j] = list.get(i)[j];
            }
        }
        return matrix;
    }
}
