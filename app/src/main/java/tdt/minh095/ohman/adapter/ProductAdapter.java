package tdt.minh095.ohman.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import tdt.minh095.ohman.R;
import tdt.minh095.ohman.pojo.ContactInfo;


/**
 * Created by MyPC on 03/10/2015.
 */
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ContactViewHolder> {


    public ProductAdapter() {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    @Override
    public void onBindViewHolder(ContactViewHolder contactViewHolder, int i) {

    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.item_list_product, viewGroup, false);
        return new ContactViewHolder(itemView);
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {
        protected TextView vName;
        protected TextView vSurname;
        protected TextView vTitle;

        public ContactViewHolder(View v) {
            super(v);
           /* vName = (TextView) v.findViewById(R.id.txtName);
            vSurname = (TextView) v.findViewById(R.id.txtSurname);
            vTitle = (TextView) v.findViewById(R.id.title);*/
        }
    }
}
