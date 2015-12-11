package tdt.minh095.ohman.fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.etiennelawlor.quickreturn.library.enums.QuickReturnViewType;
import com.etiennelawlor.quickreturn.library.listeners.QuickReturnRecyclerViewOnScrollListener;

import java.util.ArrayList;
import java.util.List;

import tdt.minh095.ohman.R;
import tdt.minh095.ohman.activity.NewProductActivity;
import tdt.minh095.ohman.adapter.ProductAdapter;
import tdt.minh095.ohman.helper.Constant;
import tdt.minh095.ohman.pojo.Product;

public class Fragment_Product extends Fragment implements View.OnClickListener {

    private RecyclerView rvProduct;
    private FrameLayout footerProduct;

    private ProductAdapter mAdapter;

    public Button btnDeleteSelected;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_product, container, false);
        rvProduct = (RecyclerView) rootView.findViewById(R.id.list_product);
        footerProduct = (FrameLayout) rootView.findViewById(R.id.footer_product);
        setUpRecyclerViewProduct();

        rootView.findViewById(R.id.btnAddNew).setOnClickListener(this);
        btnDeleteSelected = (Button) rootView.findViewById(R.id.btnDeleteSelected);
        btnDeleteSelected.setVisibility(View.GONE);
        btnDeleteSelected.setOnClickListener(this);

        return rootView;
    }

    public void startProductDetailsActivity(long _id) {

        Intent intent = new Intent(getActivity(), NewProductActivity.class);
        intent.putExtra(Constant.ID, _id);
        startActivityForResult(intent, Constant.RequestCode.PRODUCT_DETAILS);
    }

    public void setUpRecyclerViewProduct() {
        rvProduct.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvProduct.setLayoutManager(layoutManager);
        mAdapter = new ProductAdapter(this, Product.getAllActive());
        rvProduct.setAdapter(mAdapter);
        int headerHeight = getActivity().getResources().getDimensionPixelSize(R.dimen.footer_customer);
        QuickReturnRecyclerViewOnScrollListener scrollListener;

        scrollListener = new QuickReturnRecyclerViewOnScrollListener.Builder(QuickReturnViewType.FOOTER)
                .footer(footerProduct)
                .minFooterTranslation(headerHeight)
                .isSnappable(true)
                .build();
        rvProduct.setOnScrollListener(scrollListener);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.RequestCode.PRODUCT_DETAILS && resultCode == Activity.RESULT_OK) {

            mAdapter.setModel(Product.getAllActive());
            mAdapter.notifyDataSetChanged();
        }
    }

    private boolean undoSnackBar = false;

    @Override
    public void onClick(View v) {


        switch (v.getId()) {
            case R.id.btnAddNew:

                startProductDetailsActivity(-1);

                break;
            case R.id.btnDeleteSelected:

                AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
                builder.setTitle(getString(R.string.lowcase_delete_product) + "?")
                        .setCancelable(true)
                        .setNegativeButton(getString(R.string.no), null)
                        .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                final ArrayList<Long> undoLists = new ArrayList<>();

                                for (int i = mAdapter.model.size() - 1; i >= 0; i--) {

                                    if (mAdapter.model.get(i).isChecked()) {

                                        long id = mAdapter.model.get(i).getId();
                                        Product p = Product.getProductById(id);
                                        p.setChecked(false);
                                        p.setStatus(false);
                                        p.save();

                                        undoLists.add(id);
                                    }
                                }

                                mAdapter.setModel(Product.getAllActive());
                                mAdapter.notifyDataSetChanged();

                                Snackbar.make(footerProduct, getString(R.string.snackbar_delete_product_successful), Snackbar.LENGTH_LONG)
                                        .setAction(getString(R.string.undo), new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                                List<Product> products = Product.getAll();
                                                for (int i = 0; i < products.size(); i++) {

                                                    if (undoLists.contains(products.get(i).getId())) {

                                                        long id = products.get(i).getId();
                                                        Product p = Product.getProductById(id);
                                                        p.setStatus(true);
                                                        p.save();
                                                    }
                                                }

                                                mAdapter.setModel(Product.getAllActive());
                                                mAdapter.notifyDataSetChanged();
                                            }
                                        })
                                        .setCallback(new Snackbar.Callback() {
                                            @Override
                                            public void onDismissed(Snackbar snackbar, int event) {
                                                super.onDismissed(snackbar, event);

                                                //check carefully because 2 event occur
                                                if (event == DISMISS_EVENT_ACTION) {

                                                    undoSnackBar = true;
                                                }
                                                if (event == DISMISS_EVENT_MANUAL) {

                                                    if (undoSnackBar){

                                                        undoSnackBar = false;
                                                    }else {

                                                        //delete Pictures of deleted Products
                                                        for (long _id: undoLists) {

                                                            Product.deleteProductPictures(_id);
                                                        }
                                                    }
                                                }
                                            }
                                        })
                                        .show();

                                btnDeleteSelected.setVisibility(View.GONE);
                            }
                        }).show();

                break;
        }
    }
}
