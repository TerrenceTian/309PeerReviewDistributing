import java.util.ArrayList;
import java.util.List;

public class EmailContent {
    private String firstName;
    private String lastName;
    private List<String[]> coding;
    private List<String[]> teamwork;
    private String content;
    private String receipt;


    public EmailContent(){
        this.coding = new ArrayList<>();
        this.teamwork = new ArrayList<>();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReceipt() {
        return receipt;
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<String[]> getCoding() {
        return coding;
    }

    public void setCoding(List<String[]> coding) {
        this.coding = coding;
    }

    public List<String[]> getTeamwork() {
        return teamwork;
    }

    public void setTeamwork(List<String[]> teamwork) {
        this.teamwork = teamwork;
    }
}
