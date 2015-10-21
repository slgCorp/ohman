package tdt.minh095.ohman.fragment;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

import tdt.minh095.ohman.R;
import tdt.minh095.ohman.helper.DisplayUtils;

/**
 * Sử dụng để chọn 1 image từ thiết bị
 */
public class ImagePickerFragment extends Fragment implements AdapterView.OnItemClickListener {

    static final String LOG_TAG = "SalaOhMan";

    private ArrayList<String> imagePaths;
    private String imageChoosePath;
    private Callback callback;

    GridView gridView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.gallery_fragment, null, false);
        gridView = (GridView) rootView.findViewById(R.id.photo_grid);
        imagePaths = new ArrayList<>();
        loadImagePaths();
        ImagePickerAdapter adapter = new ImagePickerAdapter(getContext(), imagePaths);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            callback = (Callback) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement ImagePickerFragment.Callback");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }

    private void loadImagePaths() {
        Cursor imageCursor = null;
        try {
            String[] columns = {MediaStore.Images.Media.DATA};
            String orderBy = MediaStore.Images.Media.DATE_ADDED + " DESC";
            imageCursor = getActivity().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null, null, orderBy);
            while (imageCursor.moveToNext()) {
                String imagePath = imageCursor.getString(imageCursor.getColumnIndex(MediaStore.Images.Media.DATA));
                imagePaths.add(imagePath);
            }
        } catch (Exception e) {
            Log.d("LOG_TAG", e.getMessage());
        } finally {
            if (imageCursor != null && !imageCursor.isClosed()) {
                imageCursor.close();
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        callback.onImageSelected(imagePaths.get(position));
    }

    public class ImagePickerAdapter extends BaseAdapter {

        public static final int LAYOUT_RESOURCE = R.layout.item_gallery_thumbnail;

        private Context context;
        private ArrayList<String> imagePaths;
        private int size;

        public ImagePickerAdapter(Context context, ArrayList<String> imagePaths) {
            this.context = context;
            this.imagePaths = imagePaths;
            this.size = (DisplayUtils.getScreenWidth(context) - DisplayUtils.dpToPixel(context, 16)) / 3;
        }

        @Override
        public int getCount() {
            return imagePaths.size();
        }

        @Override
        public Object getItem(int position) {
            return new File(imagePaths.get(position));
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
            Picasso.with(context)
                    .load((File) getItem(position))
                    .resize(size, size)
                    .centerCrop()
                    .into(imageView);
            
            return convertView;
        }
    }

    public interface Callback {
        void onImageSelected(String imageSelected);
    }
}
