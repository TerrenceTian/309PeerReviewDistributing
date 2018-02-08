import com.csvreader.CsvReader;
import org.simplejavamail.email.Email;
import org.simplejavamail.mailer.Mailer;
import org.simplejavamail.mailer.config.TransportStrategy;

import javax.mail.internet.MimeMessage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class Distributor {//将所用文件导入二维字符串数组
    private final static String STUDENT_NAME_LABEL = "#studentName#";
    private final static String CODE_REVIEW_LABEL = "#codeReview#";
    private final static String TEAMWORK_LABEL = "#teamwork#";
    private final static String TA_NAME_LABEL = "#TAName#";



    public String[][] readFileToMatrix(String filePath) throws IOException {
        List<String[]> list = new ArrayList<>();//创建一个新数组
        CsvReader csvReader = new CsvReader(filePath, ',', Charset.forName("UTF-8"));//读入文件
        String line;
        while(csvReader.readRecord()){//读入表格中数据
            String[] elements = csvReader.getValues();//定义一个新的字符串数组，将字符串分割成字符串数组
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



    public String readTemplate() throws IOException {//将模板读入系统缓存
        BufferedReader bufferedReader = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/template"));//从用户的当前工作目录中读取模板文件
        StringBuilder stringBuilder = new StringBuilder();//创建一个新的字符串
        String line;
        while((line = bufferedReader.readLine()) != null){//readLine()函数可跳行
            stringBuilder.append("\n").append(line);//将每一行读取的数值加入到字符串中
        }
        return stringBuilder.toString();

    }
    public void fillNetID(HashMap<String, EmailContent> emailContentHashMap, String[][] names, String TEAM_PREFIX) {
        for (String[] name : names) {
            if (name[0].contains(TEAM_PREFIX)) {
                EmailContent emailContent = new EmailContent();
                emailContent.setFirstName(name[2]);
                emailContent.setLastName(name[3]);
                emailContentHashMap.put(name[4], emailContent);
            }
        }
    }

    public void fillReview(HashMap<String, EmailContent> emailContentHashMap, String[][] codingReview, String[][] teamwork) {
        for(int i = 1; i < codingReview.length; i++){
            String netId = codingReview[i][2];
            EmailContent emailContent = emailContentHashMap.get(netId);
            if(emailContent != null)
                emailContent.getCoding().add(new String[]{codingReview[i][4], codingReview[i][5].replace("Comment only needed if 1,2 or 5", "")});
        }
        for(int i = 1; i < teamwork.length; i++){
            String netId = teamwork[i][2];
            EmailContent emailContent = emailContentHashMap.get(netId);
            if(emailContent != null)
                emailContent.getTeamwork().add(new String[]{teamwork[i][4], teamwork[i][5].replace("Comment only needed if 1,2 or 5", "")});
        }

    }
    public void fillDataInTemplate(HashMap<String, EmailContent> emailContentHashMap, String template){
        for(Map.Entry<String, EmailContent> entry : emailContentHashMap.entrySet()) {
            String content = template;
            EmailContent emailContent = entry.getValue();
            String netId = entry.getKey();
            emailContent.setReceipt(netId + Main.EMAIL_DOMAIN);

            StringBuilder codeReview = new StringBuilder();
            for (String[] review : emailContent.getCoding()) {
                codeReview.append(review[0]).append(", ").append(review[1]).append("\n");
            }
            StringBuilder teamwork = new StringBuilder();
            for (String[] team : emailContent.getTeamwork()) {
                teamwork.append(team[0]).append(", ").append(team[1]).append("\n");
            }
            content = content.replace(STUDENT_NAME_LABEL, emailContent.getFirstName())
                    .replace(CODE_REVIEW_LABEL, codeReview.toString())
                    .replace(TEAMWORK_LABEL, teamwork.toString())
                    .replace(TA_NAME_LABEL, Main.TA_NAME_VALUE);
            emailContent.setContent(content);
        }
    }

    public void send(HashMap<String, EmailContent> emailContentHashMap){
        for(Map.Entry<String, EmailContent> entry : emailContentHashMap.entrySet()){
            EmailContent emailContent = entry.getValue();
            final Email email = new Email();
            email.setFromAddress(Main.TA_NAME_VALUE, Main.TA_EMAIL_VALUE);
            email.addRecipient(emailContent.getFirstName() + " " + emailContent.getLastName(), emailContent.getReceipt(), MimeMessage.RecipientType.TO);
            email.setText(emailContent.getContent());
            email.setSubject(Main.SUBJECT);
//            new Mailer("smtp.gmail.com", 25, "your user", "your password", TransportStrategy.SMTP_TLS).sendMail(email);
            new Mailer("smtp.gmail.com", 587, Main.TA_EMAIL_VALUE, Main.TA_EMAIL_PWD, TransportStrategy.SMTP_TLS).sendMail(email);
//            new Mailer("smtp.gmail.com", 465, "your user", "your password", TransportStrategy.SMTP_SSL).sendMail(email);
            System.out.println("sent to " + emailContent.getFirstName() + " " + emailContent.getLastName());
        }
    }

//    public String fillDataInTemplate(String template, String[][] matrix, String[][] matrix2){
//        int i = 0, j;
//        while(i < matrix.length){
//            for(j = 0; j < matrix2.length; j++){
//                if(matrix[i][2].equals(matrix2[j][0])) {
//                    break;
//                }
//            }
//            if (matrix[i][2].equals(matrix[i+1][2]) && matrix[i][2].equals(matrix[i+2][2])) {
//                String template2 = template.replace("#name", matrix2[j][1])
//                        .replace("#code.rank1", matrix[i][4])
//                        .replace("#code.rank2", matrix[i + 1][4])
//                        .replace("#code.rank3", matrix[i + 2][4])
//                        .replace("#code.commend1", matrix[i][5])
//                        .replace("#code.commend2", matrix[i + 1][5])
//                        .replace("#code.commend3", matrix[i + 2][5]);
//                i += 3;
//                continue;
//            }
//            else if(matrix[i][2].equals(matrix[i+2][2])) {
//                String template2 = template.replace("#name", matrix2[j][1])
//                        .replace("#code.rank1", matrix[i][4])
//                        .replace("#code.rank2", matrix[i + 1][4])
//                        .replace("#code.commend1", matrix[i][5])
//                        .replace("#code.commend2", matrix[i + 1][5])
//                        .replace("#code.rank3","")
//                        .replace("#code.commend3","");
//                i += 2;
//            }
//            else{
//                String template2 = template.replace("name", matrix2[j][1])
//                        .replace("#code.rank1", matrix[i][4])
//                        .replace("#code.rank2", "")
//                        .replace("#code.rank3", "")
//                        .replace("#code.commend1", matrix[i][5])
//                        .replace("#code.commend2", "")
//                        .replace("#code.commend3", "");
//                i += 1;
//            }
//
//        }
//            return null;
//    }



}
