package tdt.minh095.ohman.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import java.util.ArrayList;

import tdt.minh095.ohman.fragment.GalleryFragment;
import tdt.minh095.ohman.fragment.ProductAlbumFragment;
import tdt.minh095.ohman.fragment.ProductDetailFragment;
import tdt.minh095.ohman.helper.Constant;
import tdt.minh095.ohman.pojo.Image;
import tdt.minh095.ohman.pojo.Product;
import tdt.minh095.ohman.pojo.ProductDetail;
import tdt.minh095.ohman.pojo.ProductPicture;


public class NewProductActivity extends FragmentActivity
        implements ProductDetailFragment.Listener, GalleryFragment.Callback, ProductAlbumFragment.Callback {

    final int CONTAINER = android.R.id.content;

    private FragmentManager mFragmentManager;

    // Using 2 object below to save product details.
    private ProductDetail productDetail;
    private ArrayList<Image> imageSelected;

    private long _id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFragmentManager = getSupportFragmentManager();

        if (mFragmentManager.getFragments() != null) {
            return;
        }

        // Display product detail form
        _id = getIntent().getLongExtra(Constant.ID, -1);
        if (_id == -1) {

            ProductDetailFragment productDetailFragment = new ProductDetailFragment();
            mFragmentManager.beginTransaction()
                    .add(CONTAINER, productDetailFragment, "ProductDetailFragment")
                    .commit();
        } else {

            Product p = Product.getProductById(_id);
            productDetail = new ProductDetail();
            productDetail.setProductName(p.getProductName());
            productDetail.setProductUnit(p.getUnit());
            productDetail.setProductDescription(p.getDescription());

            imageSelected = new ArrayList<>();
            for (ProductPicture pp : Product.getProductPictures(_id)) {
                imageSelected.add(new Image(pp.getLocalLink(), pp.getDescription(), pp.getPosition()));
            }

            ProductDetailFragment productDetailFragment = ProductDetailFragment.newInstance(_id, imageSelected, productDetail);
            mFragmentManager.beginTransaction()
                    .replace(CONTAINER, productDetailFragment, "ProductDetailFragment")
                    .commit();
        }

    }

    @Override
    public void onAlbumClicked(ArrayList<Image> imageSelected, ProductDetail productDetail) {
        // save product detail
        this.productDetail = productDetail;
        this.imageSelected = imageSelected;

        // Display image picker screen
        GalleryFragment galleryFragment = GalleryFragment.newInstance(imageSelected);
        mFragmentManager.beginTransaction()
                .replace(CONTAINER, galleryFragment)
                .addToBackStack("GalleryFragment")
                .commit();
    }

    @Override
    public void onImageClicked(ArrayList<Image> imageSelected, ProductDetail productDetail, int position) {
        // save product detail
        this.productDetail = productDetail;
        this.imageSelected = imageSelected;

        // Display product album
        ProductAlbumFragment idFragment = ProductAlbumFragment.newInstance(imageSelected, position);
        mFragmentManager.beginTransaction()
                .replace(CONTAINER, idFragment)
                .addToBackStack("ProductAlbumFragment")
                .commit();
    }

    @Override
    public void loadImagesSelected(ArrayList<Image> imageSelected) {
        this.imageSelected = imageSelected;
        mFragmentManager.popBackStack();
        ProductDetailFragment productDetailFragment = ProductDetailFragment.newInstance(_id, imageSelected, productDetail);
        mFragmentManager.beginTransaction()
                .replace(CONTAINER, productDetailFragment, "ProductDetailFragment")
                .commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mFragmentManager.popBackStack();
    }

    @Override
    public void onMenuDoneClicked(ArrayList<Image> images) {
        mFragmentManager.popBackStack();
        ProductDetailFragment productDetailFragment = ProductDetailFragment.newInstance(_id, images, productDetail);
        mFragmentManager.beginTransaction()
                .replace(CONTAINER, productDetailFragment, "ProductDetailFragment")
                .commit();
    }
}
