package tdt.minh095.ohman.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import butterknife.Bind;
import butterknife.ButterKnife;
import tdt.minh095.ohman.R;
import tdt.minh095.ohman.helper.IntegerHelper;
import tdt.minh095.ohman.helper.StringHelper;
import tdt.minh095.ohman.pojo.Image;
import tdt.minh095.ohman.pojo.Product;
import tdt.minh095.ohman.pojo.ProductDetail;


public class ProductDetailFragment extends Fragment
        implements View.OnClickListener, Toolbar.OnMenuItemClickListener {

    // Bind view
    @Bind(R.id.btnShowProductAlbum)
    ImageButton btnShowProductAlbum;
    @Bind(R.id.edtProductName)
    EditText edtProductName;
    @Bind(R.id.edtProductUnit)
    AutoCompleteTextView edtProductUnit;
    @Bind(R.id.edtCostPrice)
    EditText edtCostPrice;
    @Bind(R.id.edtRetailPrice)
    EditText edtRetailPrice;
    @Bind(R.id.edtProductDescription)
    EditText edtProductDescription;
    @Bind(R.id.btnIncreaseCP)
    ImageButton btnIncreaseCP;
    @Bind(R.id.btnDecreaseCP)
    ImageButton btnDecreaseCP;
    @Bind(R.id.btnIncreaseRP)
    ImageButton btnIncreaseRP;
    @Bind(R.id.btnDecreaseRP)
    ImageButton btnDecreaseRP;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.rcvProductAlbum)
    RecyclerView rcvProductAlbum;

    // Increment value
    public static final int INCREMENT = 1000;

    private Listener listener;

    private ArrayList<Image> imageSelected;
    private ProductDetail productDetail;

    ProductAlbumAdapter productAlbumAdapter;

    public static final String[] SAMPLE_UNIT = new String[]{
            "cái", "chiếc", "bịch", "hũ", "kilogram", "mét", "lít", "lạng"
    };

    public ProductDetailFragment() {
    }

    public static ProductDetailFragment newInstance(ArrayList<Image> imageSelected, ProductDetail productDetail) {
        ProductDetailFragment productDetailFragment = new ProductDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable("ImageSelected", imageSelected);
        args.putSerializable("ProductDetail", productDetail);
        productDetailFragment.setArguments(args);
        return productDetailFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_product_detail, null, false);

        ButterKnife.bind(this, view);

        // Set listener to buttons
        btnShowProductAlbum.setOnClickListener(this);
        btnIncreaseCP.setOnClickListener(this);
        btnDecreaseCP.setOnClickListener(this);
        btnIncreaseRP.setOnClickListener(this);
        btnDecreaseRP.setOnClickListener(this);

        // Create toolbar
        toolbar.inflateMenu(R.menu.menu_product_detail);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setOnMenuItemClickListener(this);
        toolbar.setNavigationOnClickListener(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rcvProductAlbum.setLayoutManager(layoutManager);
        rcvProductAlbum.setHasFixedSize(true);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, SAMPLE_UNIT);
        edtProductUnit.setAdapter(adapter);

        // Load data to views
        Bundle bundle = getArguments();
        if (bundle != null) {
            imageSelected = (ArrayList<Image>) bundle.getSerializable("ImageSelected");
            productDetail = (ProductDetail) bundle.get("ProductDetail");
            restoreProductDetail();
        } else {
            imageSelected = new ArrayList<>();
        }

        // create album adapter
        productAlbumAdapter = new ProductAlbumAdapter(imageSelected);
        rcvProductAlbum.setAdapter(productAlbumAdapter);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (Listener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement ProductDetailFragment.Listener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnShowProductAlbum:
                saveProductDetail();
                if (imageSelected != null) {
                    saveProductDetail();
                    listener.onAlbumClicked(imageSelected, productDetail);
                } else {
                    saveProductDetail();
                    listener.onAlbumClicked(new ArrayList<Image>(), productDetail);
                }
                break;

            case R.id.btnIncreaseCP:
                increaseCost(edtCostPrice);
                break;

            case R.id.btnDecreaseCP:
                decreaseCost(edtCostPrice);
                break;

            case R.id.btnIncreaseRP:
                increaseCost(edtRetailPrice);
                break;

            case R.id.btnDecreaseRP:
                decreaseCost(edtRetailPrice);
                break;
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_save:
                if (validateInput()) {
                    // TODO: ready to insert new product
                    Product p = new Product();
                    p.setProductName(productDetail.getProductName());
                    p.setDescription(productDetail.getProductDescription());
                    p.setUnit(productDetail.getProductUnit());
                    p.save();
                    getActivity().setResult(getActivity().RESULT_OK);
                    getActivity().finish();
                }
                break;
        }
        return true;
    }

    private boolean validateInput() {

        // Cập nhật lại input trước khi kiểm tra
        saveProductDetail();

        // Kiểm tra tên sản phẩm
        if (productDetail.getProductName().isEmpty()) {
            edtProductName.setError("Vui lòng nhập tên sản phẩm");
            edtProductName.requestFocus();
            return false;
        }

        // Kiểm tra đơn vị bán
        if (productDetail.getProductUnit().isEmpty()) {
            edtProductUnit.setError("Vui lòng nhập đơn vị");
            edtProductUnit.requestFocus();
            return false;
        }

        // Kiểm tra đã có ảnh đại diện cho sản phẩm chưa
        if (imageSelected.size() == 0) {
            Toast.makeText(getContext(), "Vui lòng chọn ảnh đại diện cho sản phẩm (ít nhất 01 hình).", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    public interface Listener {

        void onAlbumClicked(ArrayList<Image> imageSelected, ProductDetail productDetail);

        void onImageClicked(ArrayList<Image> imageSelected, ProductDetail productDetail, int position);

    }

    public void loadImages() {
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
        for (final Image image : imageSelected) {
            ImageView imageView = new ImageView(getActivity());
            imageView.setTag(image.getImagePath());
            imageView.setPadding(8, 8, 8, 8);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveProductDetail();
//                    listener.onImageClicked(imageSelected, productDetail);
                }
            });
            imageView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return true;
                }
            });

            if (image.getOrder() == 1)
                Picasso.with(getActivity())
                        .load(image.getFile())
                        .skipMemoryCache()
                        .resize(300, 300)
                        .centerCrop()
                        .into(imageView);
            else
                Picasso.with(getActivity()).load(image.getFile()).resize(200, 200).centerCrop().into(imageView);
        }
    }

    public void increaseCost(EditText editText) {
        try {
            String cp = editText.getText().toString();
            int iCP = Integer.parseInt(cp) + INCREMENT;
            editText.setText(String.valueOf(iCP));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void decreaseCost(EditText editText) {
        try {
            String cp = editText.getText().toString();
            int iCP = Integer.parseInt(cp) - INCREMENT;
            editText.setText(String.valueOf(iCP));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void saveProductDetail() {
        try {
            String productName = StringHelper.getString(edtProductName);
            String productUnit = StringHelper.getString(edtProductUnit);
            int priceCost = IntegerHelper.getInt(edtCostPrice);
            int priceRetail = IntegerHelper.getInt(edtRetailPrice);
            String productDescription = StringHelper.getString(edtProductDescription);
            productDetail = new ProductDetail(productName, productUnit, priceCost, priceRetail, productDescription);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void restoreProductDetail() {
        if (productDetail != null) {
            edtProductName.setText(productDetail.getProductName());
            edtProductUnit.setText(productDetail.getProductUnit());
            edtCostPrice.setText(productDetail.getPriceCost() + "");
            edtRetailPrice.setText(productDetail.getPriceRetail() + "");
            edtProductDescription.setText(productDetail.getProductDescription());
        }
    }

    public void imageClicked(int position) {
        saveProductDetail();
        listener.onImageClicked(imageSelected, productDetail, position);
    }

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
                imageClicked(getLayoutPosition());
            }

            @Override
            public boolean onLongClick(View v) {
                // create album adapter
                Image image = imageSelected.get(getLayoutPosition());
                if (image.getOrder() != 1) {
                    changeImageAvatar(image);
                }
                return true;
            }
        }

    }

    private void changeImageAvatar(Image image) {
        int order = image.getOrder();
        for (Image i : imageSelected) {
            Log.d("sala", "image order: " + i.getOrder());
            if (i.getOrder() == order) {
                i.setOrder(1);
                imageSelected.get(0).setOrder(order);
                break;
            }
        }
        // create album adapter
        productAlbumAdapter = new ProductAlbumAdapter(imageSelected);
        rcvProductAlbum.setAdapter(productAlbumAdapter);
    }
}
