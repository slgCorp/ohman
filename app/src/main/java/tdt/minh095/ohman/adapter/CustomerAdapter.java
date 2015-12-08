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
import android.widget.TextView;

import java.util.List;

import tdt.minh095.ohman.R;
import tdt.minh095.ohman.fragment.Fragment_Customer;
import tdt.minh095.ohman.helper.ValidationHelper;
import tdt.minh095.ohman.pojo.Customer;
import tdt.minh095.ohman.pojo.Region;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.ViewHolder> {

    public List<Customer> customers;
    private Fragment_Customer fragment_customer;
    private Context context;

    public CustomerAdapter(Fragment_Customer fragment_customer, List<Customer> customers) {
        this.fragment_customer = fragment_customer;
        this.context = fragment_customer.getActivity();
        this.customers = customers;
    }

    public void setModel(List<Customer> customers) {
        this.customers = customers;
    }

    @Override
    public int getItemCount() {
        return customers.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.item_list_customer, viewGroup, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        Customer c = customers.get(position);

        viewHolder.tvName.setText(c.getCustomerName());

        if (c.getRegionL4() != 0) {
            String diachi = c.getAddress() + ", " + Region.getRegionNameById(c.getRegionL5()) + ", " + Region.getRegionNameById(c.getRegionL4());
            if (c.getAddress().isEmpty()) {
                diachi = Region.getRegionNameById(c.getRegionL5()) + ", " + Region.getRegionNameById(c.getRegionL4());
            }
            viewHolder.tvAddress.setText(diachi);
        } else {
            viewHolder.tvAddress.setText("");
        }

        viewHolder.tvPhone.setText(ValidationHelper.getViFormatPhone(c.getPhone()));

//      TODO viewHolder.tvInvoiceCount.setText("(" + Customer.getInvoiceCountById(c.getId()) + ")");

        viewHolder.chkSelect.setChecked(c.isChecked());
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

        CardView cardView;

        TextView tvName;
        //        TextView tvInvoiceCount;
        TextView tvAddress;
        TextView tvPhone;
        AppCompatButton btnDialCall;
        AppCompatButton btnSendMessage;
        CheckBox chkSelect;

        public ViewHolder(View v) {
            super(v);

            cardView = (CardView) v.findViewById(R.id.cardView);

            tvName = (TextView) v.findViewById(R.id.tvName);
            tvAddress = (TextView) v.findViewById(R.id.tvAddress);
            tvPhone = (TextView) v.findViewById(R.id.tvPhone);
            btnDialCall = (AppCompatButton) v.findViewById(R.id.btnDialCall);
            btnSendMessage = (AppCompatButton) v.findViewById(R.id.btnSendMessage);
            chkSelect = (CheckBox) v.findViewById(R.id.chkSelect);

            cardView.setOnClickListener(this);
            btnDialCall.setOnClickListener(this);
            btnSendMessage.setOnClickListener(this);

            chkSelect.setOnCheckedChangeListener(this);
        }

        @Override
        public void onClick(View v) {

            Customer c = customers.get(getAdapterPosition());

            switch (v.getId()) {

                case R.id.cardView:

                    fragment_customer.startCustomerDetailsActivity(c.getId());

                    break;
                case R.id.btnDialCall:

                    Intent dialIntent = new Intent(Intent.ACTION_DIAL);
                    dialIntent.setData(Uri.parse("tel:" + c.getPhone()));
                    context.startActivity(dialIntent);

                    break;
                case R.id.btnSendMessage:

                    Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                    sendIntent.setData(Uri.parse("sms:" + ValidationHelper.getValidFormatPhone(c.getPhone())));
                    context.startActivity(sendIntent);

                    break;
            }
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            Customer c = customers.get(getAdapterPosition());

            c.setChecked(isChecked);
//            notifyDataSetChanged();

            int chkCount = 0;
            for (int i = 0; i < customers.size(); i++) {

                if (customers.get(i).isChecked()) {
                    chkCount++;
                }
            }

            if (chkCount == 0) {

                fragment_customer.btnDeleteSelected.setVisibility(View.GONE);

            } else {

                fragment_customer.btnDeleteSelected.setVisibility(View.VISIBLE);
            }
        }
    }
}