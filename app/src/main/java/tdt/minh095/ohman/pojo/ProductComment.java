package tdt.minh095.ohman.pojo;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "T02_ProductComment", id = "_id")
public class ProductComment extends Model {

    @Column(name = "Code")
    private String code;
    @Column(name = "UserID")
    private long userID;
    @Column(name = "ProductID")
    private long productID;
    @Column(name = "Comment")
    private String comment;
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

    public ProductComment() {
        super();
        this.status = true;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public long getProductID() {
        return productID;
    }

    public void setProductID(long productID) {
        this.productID = productID;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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
}
