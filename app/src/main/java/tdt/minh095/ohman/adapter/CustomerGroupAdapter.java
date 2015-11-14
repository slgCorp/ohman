package tdt.minh095.ohman.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import tdt.minh095.ohman.R;
import tdt.minh095.ohman.pojo.CustomerGroup;

public class CustomerGroupAdapter extends ArrayAdapter<CustomerGroup> {

    private static final int LAYOUT_RESOURCE = R.layout.custom_item_fragment_customer_group;

    private Context context;
    public List<CustomerGroup> customerGroups;

    public void setModel(List<CustomerGroup> customerGroups) {
        this.customerGroups = customerGroups;
    }

    private class ViewHolder {

//        @Bind(R.id.tvNameCustomerGroup) TextView tvNameCustomerGroup;
//        @Bind(R.id.tvCountCustomerGroup) TextView tvCountCustomerGroup;
        TextView tvNameCustomerGroup;
        TextView tvCountCustomerGroup;
    }

    public CustomerGroupAdapter(Context context, List<CustomerGroup> customerGroups) {
        super(context, LAYOUT_RESOURCE, customerGroups);
        this.context = context;
        this.customerGroups = customerGroups;
    }

    @Override
    public int getCount() {
        if (customerGroups != null) {
            return customerGroups.size();
        }
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        CustomerGroup cg = customerGroups.get(position);

        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(context, LAYOUT_RESOURCE, null);
//            ButterKnife.bind(this, convertView);
            viewHolder.tvNameCustomerGroup = (TextView) convertView.findViewById(R.id.tvNameCustomerGroup);
            viewHolder.tvCountCustomerGroup = (TextView) convertView.findViewById(R.id.tvCountCustomerGroup);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvNameCustomerGroup.setText(cg.getCustomerGroupName());
        if(position==0){
            viewHolder.tvCountCustomerGroup.setText("("+CustomerGroup.getCustomerCountById(0)+")");
        }else {
            viewHolder.tvCountCustomerGroup.setText("("+CustomerGroup.getCustomerCountById(cg.getId())+")");
        }
        return convertView;
    }
}