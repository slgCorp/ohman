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

import java.util.ArrayList;

import tdt.minh095.ohman.R;
import tdt.minh095.ohman.activity.CreateShopActivity;
import tdt.minh095.ohman.adapter.ShopAdapter;
import tdt.minh095.ohman.helper.RecyclerItemClickListener;
import tdt.minh095.ohman.pojo.Shop;

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

    public void setUpDataShop() {
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        ArrayList<Shop> shops = new ArrayList<>(Shop.getAllShop());
        ShopAdapter adapter = new ShopAdapter(shops);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), CreateShopActivity.class);
                startActivity(intent);
            }
        }));
    }
}
