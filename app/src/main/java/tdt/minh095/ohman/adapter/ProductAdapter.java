package tdt.minh095.ohman.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import tdt.minh095.ohman.R;
import tdt.minh095.ohman.fragment.Fragment_Customer;
import tdt.minh095.ohman.helper.DisplayUtils;
import tdt.minh095.ohman.helper.ValidationHelper;
import tdt.minh095.ohman.pojo.Customer;
import tdt.minh095.ohman.pojo.Product;


/**
 * Created by MyPC on 03/10/2015.
 */
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    public List<Product> model;
    private Context context;

    public ProductAdapter(Context context, List<Product> model) {
        this.model = model;
        this.context = context;
    }

    public void setModel(List<Product> model) {
        this.model = model;
    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.item_list_product, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        Product item = model.get(position);
        String avatarLocalLink = Product.getAvatarLocalLink(item.getId());
        if(!avatarLocalLink.equals("")){
            Picasso.with(context)
                    .load(new File(avatarLocalLink))
                    .resize((DisplayUtils.getScreenWidth(context) - DisplayUtils.dpToPixel(context, 16)) / 3,
                            (DisplayUtils.getScreenWidth(context) - DisplayUtils.dpToPixel(context, 16)) / 3)
                    .centerCrop()
                    .into(viewHolder.imgvAvatar);
        }
        viewHolder.tvName.setText(item.getProductName());
        viewHolder.tvDescription.setText(viewHolder.tvDescription.getText() + " " + item.getDescription());

        viewHolder.chkSelect.setChecked(item.isChecked());
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

        View rootView;

        ImageView imgvAvatar;
        TextView tvName;
        TextView tvDescription;
        TextView tvAmount;
        TextView tvPrice;
        CheckBox chkSelect;

        public ViewHolder(View v) {
            super(v);

            rootView = v.findViewById(R.id.rootView);

            imgvAvatar = (ImageView) v.findViewById(R.id.imgvAvatar);
            tvName = (TextView) v.findViewById(R.id.tvName);
            tvDescription = (TextView) v.findViewById(R.id.tvDescription);
            tvAmount = (TextView) v.findViewById(R.id.tvAmount);
            tvPrice = (TextView) v.findViewById(R.id.tvPrice);

            chkSelect = (CheckBox) v.findViewById(R.id.chkSelect);

            rootView.setOnClickListener(this);

            chkSelect.setOnCheckedChangeListener(this);
        }

        @Override
        public void onClick(View v) {

//            Customer c = customers.get(getAdapterPosition());
//
//            switch (v.getId()) {
//
//                case R.id.cardView:
//
//                    fragment_customer.startCustomerDetailsActivity(c.getId());
//
//                    break;
//                case R.id.btnDialCall:
//
//                    Intent dialIntent = new Intent(Intent.ACTION_DIAL);
//                    dialIntent.setData(Uri.parse("tel:" + c.getPhone()));
//                    context.startActivity(dialIntent);
//
//                    break;
//                case R.id.btnSendMessage:
//
//                    Intent sendIntent = new Intent(Intent.ACTION_VIEW);
//                    sendIntent.setData(Uri.parse("sms:" + ValidationHelper.getValidFormatPhone(c.getPhone())));
//                    context.startActivity(sendIntent);
//
//                    break;
//            }
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

//            Customer c = customers.get(getAdapterPosition());
//
//            c.setChecked(isChecked);
////            notifyDataSetChanged();
//
//            int chkCount = 0;
//            for (int i = 0; i < customers.size(); i++) {
//
//                if (customers.get(i).isChecked()) {
//                    chkCount++;
//                }
//            }
//
//            if (chkCount == 0) {
//
//                fragment_customer.btnDeleteSelected.setVisibility(View.GONE);
//
//            } else {
//
//                fragment_customer.btnDeleteSelected.setVisibility(View.VISIBLE);
//            }
        }
    }
}
