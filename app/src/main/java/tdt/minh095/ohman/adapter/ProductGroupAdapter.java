package tdt.minh095.ohman.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

import tdt.minh095.ohman.R;
import tdt.minh095.ohman.pojo.ProductGroup;

/**
 * Created by toannguyen719 on 10/21/2015.
 */
public class ProductGroupAdapter extends ArrayAdapter {

    public static final int LAYOUT_RESOURCE = R.layout.item_list_product_group;

    class ViewHolder {
        TextView tvProductGroup;
        ImageView imgProductGroup;

        public ViewHolder() {
        }
    }

    private Context context;
    private ArrayList<ProductGroup> productGroups;


    public ProductGroupAdapter(Context context, ArrayList<ProductGroup> productGroups) {
        super(context, LAYOUT_RESOURCE, productGroups);
        this.context = context;
        this.productGroups = productGroups;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, LAYOUT_RESOURCE, null);
            viewHolder = new ViewHolder();
            viewHolder.imgProductGroup = (ImageView) convertView.findViewById(R.id.imgProductGroup);
            viewHolder.tvProductGroup = (TextView) convertView.findViewById(R.id.tvProductGroup);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        ProductGroup productGroup = (ProductGroup) getItem(position);

        viewHolder.tvProductGroup.setText(productGroup.getProductGroupName());

        if (!productGroup.getLocalLink().isEmpty()) {
            File imageFile = new File(productGroup.getLocalLink());
            Picasso.with(context)
                    .load(imageFile)
                    .fit()
                    .into(viewHolder.imgProductGroup);
        }

        return convertView;
    }

    @Override
    public Object getItem(int position) {
        return productGroups.get(position);
    }
}
