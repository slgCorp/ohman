package tdt.minh095.ohman.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import tdt.minh095.ohman.R;
import tdt.minh095.ohman.fragment.Fragment_Customer;
import tdt.minh095.ohman.fragment.Fragment_Product;
import tdt.minh095.ohman.helper.Constant;
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
    private Fragment contextFragment;

    public ProductAdapter(Fragment contextFragment, List<Product> model) {
        this.model = model;
        this.contextFragment = contextFragment;
        this.context = contextFragment.getContext();
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
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {

        Product item = model.get(position);
        String avatarLocalLink = Product.getAvatarLocalLink(item.getId());
        if(!avatarLocalLink.equals("")){
//            LoadImageAsyncTask asyncTask = new LoadImageAsyncTask(context, avatarLocalLink);
//            asyncTask.execute(viewHolder.imgvAvatar);
            Picasso.with(context)
                    .load(new File(avatarLocalLink))
                    .resize((DisplayUtils.getScreenWidth(context) - DisplayUtils.dpToPixel(context, 16)) / 3,
                            (DisplayUtils.getScreenWidth(context) - DisplayUtils.dpToPixel(context, 16)) / 3)
                    .centerCrop()
                    .into(viewHolder.imgvAvatar, new Callback() {
                        @Override
                        public void onSuccess() {
                            viewHolder.progressBar.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onError() {

                        }
                    });
        }
        viewHolder.tvName.setText(item.getProductName());
        viewHolder.tvDescription.setText(item.getDescription());

        viewHolder.chkSelect.setChecked(item.isChecked());
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

        View rootView;

        ProgressBar progressBar;
        ImageView imgvAvatar;
        TextView tvName;
        TextView tvDescription;
        TextView tvAmount;
        TextView tvPrice;
        CheckBox chkSelect;

        public ViewHolder(View v) {
            super(v);

            rootView = v.findViewById(R.id.rootView);

            progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
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

            Product item = model.get(getAdapterPosition());

            ((Fragment_Product)contextFragment).startProductDetailsActivity(item.getId());
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

            Product item = model.get(getAdapterPosition());

            item.setChecked(isChecked);
//            notifyDataSetChanged();

            int chkCount = 0;
            for (int i = 0; i < model.size(); i++) {

                if (model.get(i).isChecked()) {
                    chkCount++;
                }
            }

            if (chkCount == 0) {

                ((Fragment_Product)contextFragment).btnDeleteSelected.setVisibility(View.GONE);

            } else {

                ((Fragment_Product)contextFragment).btnDeleteSelected.setVisibility(View.VISIBLE);
            }
        }
    }
}
