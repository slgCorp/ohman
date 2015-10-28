package tdt.minh095.ohman.pojo;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

@Table(name = "T02_CustomerGroup", id = "_id")
public class CustomerGroup extends Model {
    @Column(name = "UID")
    private long uid;
    @Column(name = "Code")
    private String code;
    @Column(name = "ShopID")
    private long shopID;
    @Column(name = "CustomerGroupName")
    private String customerGroupName;
    @Column(name = "CustomerGroupNameE")
    private String customerGroupNameE;
    @Column(name = "Note")
    private String note;
    @Column(name = "Status")
    private boolean status;
    @Column(name = "CreatedBy")
    private long createdBy;
    @Column(name = "CreatedDateTime")
    private String createdDateTime;
    @Column(name = "LastUpdatedBy")
    private long lastUpdatedBy;
    @Column(name = "LastUpdatedDateTime")
    private String lastUpdatedDateTime;
    @Column(name = "IsSync")
    private boolean isSync;

    public CustomerGroup() {
        super();
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public long getShopID() {
        return shopID;
    }

    public void setShopID(long shopID) {
        this.shopID = shopID;
    }

    public String getCustomerGroupName() {
        return customerGroupName;
    }

    public void setCustomerGroupName(String customerGroupName) {
        this.customerGroupName = customerGroupName;
    }

    public String getCustomerGroupNameE() {
        return customerGroupNameE;
    }

    public void setCustomerGroupNameE(String customerGroupNameE) {
        this.customerGroupNameE = customerGroupNameE;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(long createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(String createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public long getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(long lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public String getLastUpdatedDateTime() {
        return lastUpdatedDateTime;
    }

    public void setLastUpdatedDateTime(String lastUpdatedDateTime) {
        this.lastUpdatedDateTime = lastUpdatedDateTime;
    }

    public boolean isSync() {
        return isSync;
    }

    public void setIsSync(boolean isSync) {
        this.isSync = isSync;
    }

    public static List<CustomerGroup> getAll() {

        return new Select()
                .from(CustomerGroup.class)
                .orderBy("CustomerGroupName ASC")
                .execute();
    }

    public static List<CustomerGroup> getAllActive() {

        return new Select()
                .from(CustomerGroup.class)
                .where("Status = ?", 1)
                .orderBy("CustomerGroupName ASC")
                .execute();
    }

    public static CustomerGroup getCustomerGroupById(long id) {

        return new Select()
                .from(CustomerGroup.class)
                .where("_id = ?", id)
                .executeSingle();
    }

    public static int getCustomerCountById(long id) {

        return new Select()
                .from(Customer.class)
                .where("CustomerGroupID = ? and Status = ?", id, 1)
                .count();
    }
}
