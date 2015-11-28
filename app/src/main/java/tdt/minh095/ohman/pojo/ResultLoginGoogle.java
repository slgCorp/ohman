package tdt.minh095.ohman.pojo;

/**
 * Created by TrytoThuan on 09/10/2015.
 */
public class ResultLoginGoogle {

    private String displayName;
    private String gg_id;
    private int gender;
    private String email;

    public ResultLoginGoogle(String displayName, String gg_id, int gender, String email) {
        this.displayName = displayName;
        this.gg_id = gg_id;
        this.gender = gender;
        this.email = email;
    }

    public ResultLoginGoogle() {
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getGg_id() {
        return gg_id;
    }

    public void setGg_id(String gg_id) {
        this.gg_id = gg_id;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
