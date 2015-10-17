package tdt.minh095.ohman.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.etiennelawlor.quickreturn.library.enums.QuickReturnViewType;
import com.etiennelawlor.quickreturn.library.listeners.QuickReturnRecyclerViewOnScrollListener;

import java.lang.reflect.Field;

import tdt.minh095.ohman.R;
import tdt.minh095.ohman.adapter.ProductAdapter;
import tdt.minh095.ohman.view.DividerItemDecoration;


/**
 * Created by MyPC on 02/10/2015.
 */
public class Fragment_Product extends Fragment {
    RecyclerView recyclerViewProduct;
    FrameLayout footerProduct;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_product, container, false);
        recyclerViewProduct = (RecyclerView) rootView.findViewById(R.id.list_product);
        footerProduct = (FrameLayout) rootView.findViewById(R.id.footer_product);
        setUpRecyclerViewProduct();
        return rootView;
    }

    public void setUpRecyclerViewProduct() {
        recyclerViewProduct.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewProduct.setLayoutManager(layoutManager);
        recyclerViewProduct.setAdapter(new ProductAdapter());
        int headerHeight = getActivity().getResources().getDimensionPixelSize(R.dimen.footer_customer);
        QuickReturnRecyclerViewOnScrollListener scrollListener;

        scrollListener = new QuickReturnRecyclerViewOnScrollListener.Builder(QuickReturnViewType.FOOTER)
                .footer(footerProduct)
                .minFooterTranslation(headerHeight)
                .isSnappable(true)
                .build();
        recyclerViewProduct.setOnScrollListener(scrollListener);


    }
}
