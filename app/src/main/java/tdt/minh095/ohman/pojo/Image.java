package tdt.minh095.ohman.pojo;

import java.io.File;
import java.io.Serializable;

public class Image implements Serializable {

    private String imagePath;
    private boolean selected;
    private int order;
    private String description;

    public Image(String imagePath) {
        this.imagePath = imagePath;
        this.selected = false;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public File getFile() {
        if (imagePath != null)
            return new File(imagePath);
        else
            return null;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
