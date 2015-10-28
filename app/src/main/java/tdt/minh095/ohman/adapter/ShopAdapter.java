package tdt.minh095.ohman.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import tdt.minh095.ohman.R;
import tdt.minh095.ohman.pojo.Shop;


public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ViewHolder> {

    private ArrayList<Shop> shops;

    public ShopAdapter(ArrayList<Shop> shops) {
        this.shops = shops;
    }

    @Override
    public int getItemCount() {
        return shops.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Shop shop = shops.get(i);
        viewHolder.shopName.setText(shop.getShopName());
        viewHolder.shopAdress.setText(shop.getAddress());
        viewHolder.shopPhone.setText(shop.getPhone());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_shop, viewGroup, false);
        return new ViewHolder(itemView);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        protected TextView shopName;
        protected TextView shopAdress;
        protected TextView shopPhone;


        public ViewHolder(View v) {
            super(v);
            shopName = (TextView) v.findViewById(R.id.tv_shopname);
            shopAdress = (TextView) v.findViewById(R.id.tv_shop_address);
            shopPhone = (TextView) v.findViewById(R.id.tv_shop_phone);
        }
    }
}
