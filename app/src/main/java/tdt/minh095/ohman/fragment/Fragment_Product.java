package tdt.minh095.ohman.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.etiennelawlor.quickreturn.library.enums.QuickReturnViewType;
import com.etiennelawlor.quickreturn.library.listeners.QuickReturnRecyclerViewOnScrollListener;

import tdt.minh095.ohman.R;
import tdt.minh095.ohman.activity.NewProductActivity;
import tdt.minh095.ohman.adapter.ProductAdapter;
import tdt.minh095.ohman.helper.Constant;
import tdt.minh095.ohman.pojo.Product;


/**
 * Created by MyPC on 02/10/2015.
 */
public class Fragment_Product extends Fragment {

    private RecyclerView rvProduct;
    private FrameLayout footerProduct;

    private ProductAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_product, container, false);
        rvProduct = (RecyclerView) rootView.findViewById(R.id.list_product);
        footerProduct = (FrameLayout) rootView.findViewById(R.id.footer_product);
        setUpRecyclerViewProduct();

        rootView.findViewById(R.id.btnAddNew).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), NewProductActivity.class);
                startActivityForResult(intent, Constant.RequestCode.PRODUCT_DETAILS);
            }
        });

        return rootView;
    }

    public void setUpRecyclerViewProduct() {
        rvProduct.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvProduct.setLayoutManager(layoutManager);
        mAdapter = new ProductAdapter(this.getContext(), Product.getAllActive());
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
        if(requestCode == Constant.RequestCode.PRODUCT_DETAILS && resultCode == getActivity().RESULT_OK){

            //TODO notify
            mAdapter.notifyDataSetChanged();
        }
    }
}
