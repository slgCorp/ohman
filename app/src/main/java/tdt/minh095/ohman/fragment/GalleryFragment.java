package tdt.minh095.ohman.fragment;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import tdt.minh095.ohman.R;
import tdt.minh095.ohman.adapter.GalleryAdapter;
import tdt.minh095.ohman.pojo.Image;


public class GalleryFragment extends Fragment
        implements AdapterView.OnItemClickListener, Toolbar.OnMenuItemClickListener {

    @Bind(R.id.photo_grid)
    GridView gridView;
    @Bind(R.id.galleryToolbar)
    Toolbar toolbar;

    private Callback callback;

    private GalleryAdapter galleryAdapter;
    private ArrayList<Image> imageSelected;

    public GalleryFragment() {

    }

    public static GalleryFragment newInstance(ArrayList<Image> imageSelected) {
        GalleryFragment galleryFragment = new GalleryFragment();
        Bundle args = new Bundle();
        args.putSerializable("ImageSelected", imageSelected);
        galleryFragment.setArguments(args);
        return galleryFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.gallery_fragment, null, false);

        ButterKnife.bind(this, view);

        galleryAdapter = new GalleryAdapter(getActivity());
        loadThumbsFromGallery();
        gridView.setAdapter(galleryAdapter);
        gridView.setOnItemClickListener(this);
        toolbar.inflateMenu(R.menu.menu_image_picker);
        toolbar.setOnMenuItemClickListener(this);


        Bundle bundle = getArguments();
        if (bundle != null) {
            imageSelected = (ArrayList<Image>) bundle.getSerializable("ImageSelected");
            galleryAdapter.setImageSelected(imageSelected);
            galleryAdapter.notifyDataSetChanged();
            Log.d("sala", imageSelected.size() + "");
        } else {
            imageSelected = new ArrayList<>();
        }

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            callback = (Callback) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement GalleryFragment.Callback");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }

    private void loadThumbsFromGallery() {
        Cursor imageCursor = null;
        try {
            String[] columns = {MediaStore.Images.Media.DATA};
            String orderBy = MediaStore.Images.Media.DATE_ADDED + " DESC";
            imageCursor = getActivity().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null, null, orderBy);
            while (imageCursor.moveToNext()) {
                String imagePath = imageCursor.getString(imageCursor.getColumnIndex(MediaStore.Images.Media.DATA));
                Image image = new Image(imagePath);
                galleryAdapter.add(image);
            }
            galleryAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (imageCursor != null && !imageCursor.isClosed()) {
                imageCursor.close();
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.photo_grid) {
            galleryAdapter.selectItem(position);
            galleryAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.menu_done) {
            imageSelected = galleryAdapter.getImageSelected();
            callback.loadImagesSelected(imageSelected);
            return true;
        }
        return false;
    }

    public interface Callback {
        void loadImagesSelected(ArrayList<Image> imageSelected);
    }
}
