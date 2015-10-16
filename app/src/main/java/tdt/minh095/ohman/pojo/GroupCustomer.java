package tdt.minh095.ohman.pojo;

/**
 * Created by MyPC on 03/10/2015.
 */
public class GroupCustomer {
    private String nameGroup;
    private int memberGroupe;

    public GroupCustomer(String nameGroup, int memberGroupe) {
        this.nameGroup = nameGroup;
        this.memberGroupe = memberGroupe;
    }

    public String getNameGroup() {
        return nameGroup;
    }

    public void setNameGroup(String nameGroup) {
        this.nameGroup = nameGroup;
    }

    public int getMemberGroupe() {
        return memberGroupe;
    }

    public void setMemberGroupe(int memberGroupe) {
        this.memberGroupe = memberGroupe;
    }
}
