package tdt.minh095.ohman.pojo;

import java.io.Serializable;

public class ProductDetail implements Serializable {
    private String productName;
    private String productUnit;
    private int priceCost;
    private int priceRetail;
    private String productDescription;

    public ProductDetail() {
    }

    public ProductDetail(String productName, String productUnit, int priceCost, int priceRetail, String productDescription) {
        this.productName = productName;
        this.productUnit = productUnit;
        this.priceCost = priceCost;
        this.priceRetail = priceRetail;
        this.productDescription = productDescription;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductUnit() {
        return productUnit;
    }

    public void setProductUnit(String productUnit) {
        this.productUnit = productUnit;
    }

    public int getPriceCost() {
        return priceCost;
    }

    public void setPriceCost(int priceCost) {
        this.priceCost = priceCost;
    }

    public int getPriceRetail() {
        return priceRetail;
    }

    public void setPriceRetail(int priceRetail) {
        this.priceRetail = priceRetail;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }
}
