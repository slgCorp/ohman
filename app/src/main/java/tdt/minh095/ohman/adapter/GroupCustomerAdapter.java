package tdt.minh095.ohman.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import tdt.minh095.ohman.R;
import tdt.minh095.ohman.pojo.GroupCustomer;


/**
 * Created by MyPC on 03/10/2015.
 */
public class GroupCustomerAdapter extends RecyclerView.Adapter<GroupCustomerAdapter.ContactViewHolder> {

    private ArrayList<GroupCustomer> listGroupCustomer;

    public GroupCustomerAdapter(ArrayList<GroupCustomer> listGroupCustomer) {
        this.listGroupCustomer = listGroupCustomer;
    }

    @Override
    public int getItemCount() {
        return listGroupCustomer.size();
    }

    @Override
    public void onBindViewHolder(ContactViewHolder contactViewHolder, int i) {
        GroupCustomer group = listGroupCustomer.get(i);
        contactViewHolder.groupName.setText(group.getNameGroup());
        contactViewHolder.groupMember.setText(group.getMemberGroupe() + "");
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.item_group_customer, viewGroup, false);

        return new ContactViewHolder(itemView);
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {
        protected TextView groupName;
        protected TextView groupMember;

        public ContactViewHolder(View v) {
            super(v);
            groupName = (TextView) v.findViewById(R.id.tv_group_name);
            groupMember = (TextView) v.findViewById(R.id.tv_group_member);
        }
    }
}
