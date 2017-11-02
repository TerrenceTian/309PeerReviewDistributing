import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Distributor {//将所用文件导入二维字符串数组
    public String[][] readFileToMatrix(String filePath) throws IOException {
        List<String[]> list = new ArrayList<>();//创建一个新数组
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));//读入文件
        String line;
        while((line = bufferedReader.readLine()) != null){//读入表格中数据
            String[] elements = line.split(",");//定义一个新的字符串数组，将字符串分割成字符串数组
            list.add(elements);//将分割的字符串数组加入list中
        }
        int width = list.get(0).length;//取list中第一个元素的长度
        String[][] matrix = new String[list.size()][width];//建立一个新的二维字符串数组
        for (int i = 0; i < list.size(); i++){
            for(int j = 0; j < width; j++){
                matrix[i][j] = list.get(i)[j];//将分割的字符串数组中的每一个元素放入该新的二维字符串数组
            }
        }
        return matrix;
    }

    public String[][] readFileToMatrix2(String filePath2) throws IOException {
        List<String[]> list = new ArrayList<>();
        BufferedReader bufferedReader2 = new BufferedReader(new FileReader (filePath2));
        String line2;
        while((line2 = bufferedReader2.readLine()) != null){
            String[] elements2 = line2.split(",");
            list.add(elements2);
        }
        int width2 = list.get(0).length;
        String matrix2[][] = new String[list.size()][width2];
        for (int i = 0; i < list.size(); i++){
            for(int j = 0; j < width2; j++){
                matrix2[i][j] = list.get(i)[j];
            }
        }
        return matrix2;
    }

    public String readTemplate() throws IOException {//将模板读入系统缓存
        BufferedReader bufferedReader = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/template"));//从用户的当前工作目录中读取模板文件
        StringBuilder stringBuilder = new StringBuilder();//创建一个新的字符串
        String line;
        while((line = bufferedReader.readLine()) != null){//readLine()函数可跳行
            stringBuilder.append("\n"+line);//将每一行读取的数值加入到字符串中
        }
        return stringBuilder.toString();

    }

    public String fillDataInTemplate(String template, String[][] matrix, String[][] matrix2){
        int i = 0, j;
        while(i < matrix.length){
            for(j = 0; j < matrix2.length; j++){
                if(matrix[i][2].equals(matrix2[j][0])) {
                    break;
                }
            }
            if (matrix[i][2].equals(matrix[i+1][2]) && matrix[i][2].equals(matrix[i+2][2])) {
                String template2 = template.replace("#name", matrix2[j][1])
                        .replace("#code.rank1", matrix[i][4])
                        .replace("#code.rank2", matrix[i + 1][4])
                        .replace("#code.rank3", matrix[i + 2][4])
                        .replace("#code.commend1", matrix[i][5])
                        .replace("#code.commend2", matrix[i + 1][5])
                        .replace("#code.commend3", matrix[i + 2][5]);
                i += 3;
                continue;
            }
            else if(matrix[i][2].equals(matrix[i+2][2])) {
                String template2 = template.replace("#name", matrix2[j][1])
                        .replace("#code.rank1", matrix[i][4])
                        .replace("#code.rank2", matrix[i + 1][4])
                        .replace("#code.commend1", matrix[i][5])
                        .replace("#code.commend2", matrix[i + 1][5])
                        .replace("#code.rank3","")
                        .replace("#code.commend3","");
                i += 2;
            }
            else{
                String template2 = template.replace("name", matrix2[j][1])
                        .replace("#code.rank1", matrix[i][4])
                        .replace("#code.rank2", "")
                        .replace("#code.rank3", "")
                        .replace("#code.commend1", matrix[i][5])
                        .replace("#code.commend2", "")
                        .replace("#code.commend3", "");
                i += 1;
            }

        }
            return null;
    }



}
