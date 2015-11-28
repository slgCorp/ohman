package tdt.minh095.ohman.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import tdt.minh095.ohman.pojo.Image;


/**
 * Created by toannguyen719 on 10/15/2015.
 */
public class ProductAlbumAdapter extends RecyclerView.Adapter<ProductAlbumAdapter.ViewHolder> {

    static final int IMAGE_AVATAR_SIZE = 300;
    static final int IMAGE_OTHER_SIZE = 200;

    private ArrayList<Image> imageSelected;

    public ProductAlbumAdapter(ArrayList<Image> imageSelected) {
        // sort by order
        Collections.sort(imageSelected, new Comparator<Image>() {
            @Override
            public int compare(Image lhs, Image rhs) {
                if (lhs.getOrder() == rhs.getOrder())
                    return 0;
                else if (lhs.getOrder() > rhs.getOrder())
                    return 1;
                else
                    return -1;
            }
        });
        this.imageSelected = imageSelected;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ImageView v = new ImageView(parent.getContext());
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Context context = holder.mImageView.getContext();
        File file = imageSelected.get(position).getFile();
        if (imageSelected.get(position).getOrder() == 1) {
            Log.d("sala", "Image has order " + imageSelected.get(position).getOrder());
            Picasso.with(context)
                    .load(file)
                    .skipMemoryCache()
                    .resize(IMAGE_AVATAR_SIZE, IMAGE_AVATAR_SIZE)
                    .centerCrop()
                    .into(holder.mImageView);
        } else {
            Log.d("sala", "Image has order " + imageSelected.get(position).getOrder());
            Picasso.with(context)
                    .load(file)
                    .skipMemoryCache()
                    .resize(IMAGE_OTHER_SIZE, IMAGE_OTHER_SIZE)
                    .centerCrop()
                    .into(holder.mImageView);
        }
    }

    @Override
    public int getItemCount() {
        return imageSelected.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public ImageView mImageView;

        public ViewHolder(ImageView v) {
            super(v);
            mImageView = v;
            mImageView.setPadding(8, 8, 8, 8);
            mImageView.setOnClickListener(this);
            mImageView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(v.getContext(), "Item click: " + getLayoutPosition(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public boolean onLongClick(View v) {
            Toast.makeText(v.getContext(), "Item long click: " + getLayoutPosition(), Toast.LENGTH_SHORT).show();
            return true;
        }
    }
}
