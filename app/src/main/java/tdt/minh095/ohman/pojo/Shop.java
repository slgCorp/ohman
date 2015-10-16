package tdt.minh095.ohman.pojo;

/**
 * Created by MyPC on 03/10/2015.
 */
public class Shop {
    private String shopName;
    private String shopAdress;
    private String shopPhone;

    public Shop(String shopName, String shopAdress, String shopPhone) {
        this.shopName = shopName;
        this.shopAdress = shopAdress;
        this.shopPhone = shopPhone;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopAdress() {
        return shopAdress;
    }

    public void setShopAdress(String shopAdress) {
        this.shopAdress = shopAdress;
    }

    public String getShopPhone() {
        return shopPhone;
    }

    public void setShopPhone(String shopPhone) {
        this.shopPhone = shopPhone;
    }
}
