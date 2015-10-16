package tdt.minh095.ohman.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.etiennelawlor.quickreturn.library.enums.QuickReturnViewType;
import com.etiennelawlor.quickreturn.library.listeners.QuickReturnRecyclerViewOnScrollListener;
import com.etiennelawlor.quickreturn.library.utils.QuickReturnUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import tdt.minh095.ohman.R;
import tdt.minh095.ohman.adapter.ShopAdapter;
import tdt.minh095.ohman.pojo.Shop;

/**
 * Created by MyPC on 02/10/2015.
 */
public class Fragment_Shop extends Fragment {
    RecyclerView mRecyclerView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_shop, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.list_shop);
        setUpDataShop();
        return rootView;
    }
    public void setUpDataShop(){
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        ShopAdapter adapter = new ShopAdapter(createData());
        mRecyclerView.setAdapter(adapter);
    }
    private ArrayList<Shop> createData() {
        ArrayList<Shop> listShop = new ArrayList<Shop>();
        listShop.add(new Shop("MinhMinh", "tp hcm", " 0903759394"));
        listShop.add(new Shop("MinhMinh", "tp hcm", " 0903759394"));
        listShop.add(new Shop("MinhMinh", "tp hcm", " 0903759394"));
        listShop.add(new Shop("MinhMinh", "tp hcm", " 0903759394"));
        return listShop;
    }


}
