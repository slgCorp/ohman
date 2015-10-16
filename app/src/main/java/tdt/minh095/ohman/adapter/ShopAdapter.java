package tdt.minh095.ohman.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import tdt.minh095.ohman.R;
import tdt.minh095.ohman.pojo.Shop;


/**
 * Created by MyPC on 03/10/2015.
 */
public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ContactViewHolder> {

    private ArrayList<Shop> shopList;

    public ShopAdapter(ArrayList<Shop> shopList) {
        this.shopList = shopList;
    }

    @Override
    public int getItemCount() {
        return shopList.size();
    }

    @Override
    public void onBindViewHolder(ContactViewHolder contactViewHolder, int i) {
        Shop shop = shopList.get(i);
        contactViewHolder.shopName.setText(shop.getShopName());
        contactViewHolder.shopAdress.setText(shop.getShopAdress());
        contactViewHolder.shopPhone.setText(shop.getShopPhone());
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.item_list_shop, viewGroup, false);

        return new ContactViewHolder(itemView);
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {
        protected TextView shopName;
        protected TextView shopAdress;
        protected TextView shopPhone;

        public ContactViewHolder(View v) {
            super(v);
            shopName = (TextView) v.findViewById(R.id.tv_shopname);
            shopAdress = (TextView) v.findViewById(R.id.tv_shop_address);
            shopPhone = (TextView) v.findViewById(R.id.tv_shop_phone);
        }
    }
}
