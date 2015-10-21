package tdt.minh095.ohman.pojo;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;


@Table(name = "T02_ProductGroup", id = "_id")
public class ProductGroup extends Model {

    @Column(name = "UID")
    private long uid;

    @Column(name = "Code")
    private String code;

    @Column(name = "ShopID")
    private long shopID;

    @Column(name = "ProductGroupName")
    private String productGroupName;

    @Column(name = "ProductGroupNameE")
    private String productGroupNameE;

    @Column(name = "SettingID")
    private long settingID;

    @Column(name = "Level")
    private int level;

    @Column(name = "ParentID")
    private long parentID;

    @Column(name = "LocalLink")
    private String localLink;

    @Column(name = "ServerLink")
    private String serverLink;

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

    public ProductGroup() {
        super();
        status = true;
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

    public String getProductGroupName() {
        return productGroupName;
    }

    public void setProductGroupName(String productGroupName) {
        this.productGroupName = productGroupName;
    }

    public String getProductGroupNameE() {
        return productGroupNameE;
    }

    public void setProductGroupNameE(String productGroupNameE) {
        this.productGroupNameE = productGroupNameE;
    }

    public long getSettingID() {
        return settingID;
    }

    public void setSettingID(long settingID) {
        this.settingID = settingID;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public long getParentID() {
        return parentID;
    }

    public void setParentID(long parentID) {
        this.parentID = parentID;
    }

    public String getLocalLink() {
        return localLink;
    }

    public void setLocalLink(String localLink) {
        this.localLink = localLink;
    }

    public String getServerLink() {
        return serverLink;
    }

    public void setServerLink(String serverLink) {
        this.serverLink = serverLink;
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

    public static List<ProductGroup> getAll() {
        return new Select()
                .from(ProductGroup.class)
                .execute();
    }

    public static boolean checkName(String productGroupName) {
        String whereClause = "ProductGroupName = '" + productGroupName + "'";
        ProductGroup productGroup = new Select().from(ProductGroup.class)
                .where(whereClause)
                .executeSingle();
        if (productGroup != null) {
            return false;
        } else {
            return true;
        }
    }
}
