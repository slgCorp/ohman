package tdt.minh095.ohman.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterViewFlipper;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import tdt.minh095.ohman.R;
import tdt.minh095.ohman.pojo.Image;

public class ProductAlbumFragment extends Fragment implements View.OnTouchListener {

    ArrayList<Image> images;
    AdapterViewFlipper avpProductAlbum;

    Callback callback;

    float lastX;
    final float MIN_DISTANCE = 100.0f;

    Toolbar toolbar;

    int position;

    public ProductAlbumFragment() {
    }

    public static ProductAlbumFragment newInstance(ArrayList<Image> images, int position) {
        ProductAlbumFragment fragment = new ProductAlbumFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("images", images);
        bundle.putInt("position", position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        if (bundle != null) {
            images = (ArrayList<Image>) bundle.get("images");
            position = bundle.getInt("position", -1);
        } else
            images = new ArrayList<>();

        View view = inflater.inflate(R.layout.fragment_product_album, null, false);

        toolbar = (Toolbar) view.findViewById(R.id.toolbar);

        toolbar.inflateMenu(R.menu.menu_product_album);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_done:
                        callback.onMenuDoneClicked(images);
                        return true;
                }
                return false;
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        avpProductAlbum = (AdapterViewFlipper) view.findViewById(R.id.avpProductAlbum);
        ImageAdapter demoAdapter = new ImageAdapter(getContext(), images);
        avpProductAlbum.setAdapter(demoAdapter);
        avpProductAlbum.setOnTouchListener(this);

        // go to image position
        if (position != -1) {
            for (int i = 0; i < position; i++) {
                avpProductAlbum.showNext();
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            callback = (Callback) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement ProductAlbumFragment.Callback");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            lastX = event.getX();
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            float distance = lastX - event.getX();
            if (distance > 0 && distance > MIN_DISTANCE) {
                avpProductAlbum.showNext();
            } else if (distance < 0 && distance < -MIN_DISTANCE) {
                avpProductAlbum.showPrevious();
            }
        }
        return true;
    }

    public interface Callback {
        void onMenuDoneClicked(ArrayList<Image> images);
    }

    public class ImageAdapter extends ArrayAdapter {

        private ArrayList<Image> images;
        private Context context;

        public ImageAdapter(Context context, ArrayList<Image> images) {
            super(context, R.layout.image_description, images);
            this.context = context;
            this.images = images;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = View.inflate(context, R.layout.image_description, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.image);
            EditText editText = (EditText) view.findViewById(R.id.description);

            String description = images.get(position).getDescription();
            if (description != null) {
                editText.setText(description);
            }

            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s != null)
                        images.get(position).setDescription(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            Picasso.with(context).load(images.get(position).getFile())
                    .resize(500, 500)
                    .centerCrop()
                    .into(imageView);
            return view;
        }
    }
}
