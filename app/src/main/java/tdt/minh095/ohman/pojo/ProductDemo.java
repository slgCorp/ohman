package tdt.minh095.ohman.pojo;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class ProductDemo {

    @Expose
    private Long productId;
    @Expose
    private String productName;

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    @Expose
    private List<String> images = new ArrayList<String>();

    /**
     * @return The productId
     */
    public Long getProductId() {
        return productId;
    }

    /**
     * @param productId The productId
     */
    public void setProductId(Long productId) {
        this.productId = productId;
    }

    /**
     * @return The productName
     */
    public String getProductName() {
        return productName;
    }
}
