package tdt.minh095.ohman.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

import tdt.minh095.ohman.R;
import tdt.minh095.ohman.helper.DisplayUtils;
import tdt.minh095.ohman.pojo.Image;

public class GalleryAdapter extends BaseAdapter {

    public static final int LAYOUT_RESOURCE = R.layout.item_gallery_thumbnail;

    private Context context;
    private ArrayList<Image> images;
    private int size;
    private int order;

    public GalleryAdapter(Context context) {
        this.context = context;
        this.images = new ArrayList<>();
        this.size = (DisplayUtils.getScreenWidth(context) - DisplayUtils.dpToPixel(context, 16)) / 3;
        this.order = 1;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object getItem(int position) {
        return images.get(position).getFile();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(context, LAYOUT_RESOURCE, null);
        }
        ImageView imageView = (ImageView) convertView.findViewById(R.id.thumb);
        TextView orderText = (TextView) convertView.findViewById(R.id.order);
        Picasso.with(context)
                .load((File) getItem(position))
                .resize(size, size)
                .centerCrop()
                .into(imageView);
        if (!images.get(position).isSelected()) {
            orderText.setBackgroundResource(android.R.color.transparent);
            orderText.setText("");
        } else {
            orderText.setBackgroundResource(R.drawable.gallery_photo_selected);
            orderText.setText(images.get(position).getOrder() + "");
        }
        return convertView;
    }

    public void add(Image image) {
        images.add(image);
    }

    public void selectItem(int position) {
        boolean selected = images.get(position).isSelected();
        Image image = images.get(position);
        if (selected) {
            image.setSelected(false);
            updateOrder(image.getOrder());
            image.setOrder(0);
            order--;
        } else {
            images.get(position).setSelected(true);
            image.setOrder(order);
            order++;
        }
    }

    public void updateOrder(int ordered) {
        for (Image image : images) {
            if (image.getOrder() > ordered) {
                image.setOrder(image.getOrder() - 1);
            }
        }
    }

    public ArrayList<Image> getImageSelected() {
        ArrayList<Image> imageSelected = new ArrayList<>();
        for (Image image : images) {
            if (image.isSelected())
                imageSelected.add(image);
        }
        return imageSelected;
    }

    public void setImageSelected(ArrayList<Image> imageSelected) {
        if (imageSelected.size() > 0 && imageSelected != null) {
            for (Image image : imageSelected) {
                for (Image i : images) {
                    if (i.getFile().getAbsolutePath().equals(image.getFile().getAbsolutePath())) {
                        i.setOrder(image.getOrder());
                        i.setSelected(true);
                    }
                }
            }
            order = imageSelected.size() + 1;
        }
    }
}
