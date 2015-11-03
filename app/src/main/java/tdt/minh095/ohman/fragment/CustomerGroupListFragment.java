package tdt.minh095.ohman.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import tdt.minh095.ohman.R;
import tdt.minh095.ohman.adapter.CustomerGroupAdapter;
import tdt.minh095.ohman.helper.Constant;
import tdt.minh095.ohman.helper.SystemInfoHelper;
import tdt.minh095.ohman.helper.ValidationHelper;
import tdt.minh095.ohman.pojo.Customer;
import tdt.minh095.ohman.pojo.CustomerGroup;

public class CustomerGroupListFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {


    private LinearLayout customerGroupLayout;
    private EditText edtCustomerGroup;

    private Context mContext;
    private CustomerGroupAdapter mAdapter;

    private CustomerGroup cg;
    private int mPos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_customer_group_list, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity();

        customerGroupLayout = (LinearLayout) getActivity().findViewById(R.id.customerGroupLayout);
        ListView lvCustomerGroup = (ListView) getActivity().findViewById(R.id.lvAddCustomerGroup);
        FloatingActionButton fabAddCustomerGroup = (FloatingActionButton) getActivity().findViewById(R.id.fabAddCustomerGroup);

        mAdapter = new CustomerGroupAdapter(mContext, CustomerGroup.getAllActive());
        mNotifyDataSetChanged();
        lvCustomerGroup.setAdapter(mAdapter);

        fabAddCustomerGroup.setOnClickListener(this);

        lvCustomerGroup.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return false;
                //TODO Cho phép xóa nhiều khách hàng ( hiện ra context menu )
            }
        });

        lvCustomerGroup.setOnItemClickListener(this);
    }

    private void mNotifyDataSetChanged() {

        List<CustomerGroup> customerGroups = CustomerGroup.getAllActive();
        CustomerGroup cg = new CustomerGroup();
        cg.setCustomerGroupName(getString(R.string.fragment_customer_group_nogroup));
        customerGroups.add(0, cg);
        mAdapter.setModel(customerGroups);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fabAddCustomerGroup:

                showDialogInsertUpdate(Constant.Statement.IS_INSERTING);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

        if (position != 0) {

            PopupMenu popup = new PopupMenu(mContext, view);
            popup.getMenuInflater().inflate(R.menu.popup_menu_customergroup, popup.getMenu());
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {

                    mPos = position;

                    switch (item.getItemId()) {

                        case R.id.action_popup_update:

                            showDialogInsertUpdate(Constant.Statement.IS_UPDATETING);

                            return true;

                        case R.id.action_popup_delete:

                            showDialogDelete();

                            return true;
                    }

                    return false;
                }
            });
            popup.show();//showing popup menu
        }
    }

    private void showDialogInsertUpdate(final int mState) {

        cg = new CustomerGroup();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View mDialog = inflater.inflate(R.layout.dialog_add_group_customer,
                (ViewGroup) getActivity().findViewById(R.id.dialog_add_customer_group));

        edtCustomerGroup = (EditText) mDialog.findViewById(R.id.edtCustomerGroup);
        edtCustomerGroup.setSingleLine();
        ValidationHelper.addValidNameSpacesTextChanged(edtCustomerGroup);
        //TODO xử lý tên nhóm nhập vào

        if (mState == Constant.Statement.IS_UPDATETING) {

            cg = CustomerGroup.getCustomerGroupById(mAdapter.customerGroups.get(mPos).getId());
            edtCustomerGroup.setText(cg.getCustomerGroupName());
            edtCustomerGroup.setSelection(cg.getCustomerGroupName().length());
        }

        final AlertDialog mAlertDialog = new AlertDialog.Builder(mContext)
                .setView(mDialog)
                .setNegativeButton(getString(R.string.btn_customer_group_cancel), null)
                .setPositiveButton(getString(R.string.btn_customer_group_save), null)
                .create();
        mAlertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {

                Button mPositiveButton = mAlertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                mPositiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (checkInput()) {

                            String name = edtCustomerGroup.getText().toString().trim();
                            cg.setCustomerGroupName(name);
                            //TODO cg.setShopID cg.setCode(); cg.setCreatedBy(); cg.setLastUpdatedBy(); cg.setNote();
                            cg.setStatus(true);

                            if(mState == Constant.Statement.IS_INSERTING){

                                cg.setCreatedDateTime(SystemInfoHelper.getCurrentDatetime(true));

                                Snackbar.make(customerGroupLayout, getString(R.string.fragment_customer_group_add_group_successful_snackbar),
                                        Snackbar.LENGTH_LONG).show();
                            }else {

                                cg.setLastUpdatedDateTime(SystemInfoHelper.getCurrentDatetime(true));


                                Snackbar.make(customerGroupLayout, getString(R.string.fragment_customer_group_edit_group_successful_snackbar),
                                        Snackbar.LENGTH_LONG).show();
                            }

                            cg.save();
                            mNotifyDataSetChanged();

                            mAlertDialog.dismiss();
                        }
                    }
                });
            }
        });
        mAlertDialog.show();
    }

    private boolean checkInput() {

        String name = edtCustomerGroup.getText().toString().trim();
        if (name.length() == 0) {

            edtCustomerGroup.setError(getString(R.string.fragment_customer_group_name_error));
            edtCustomerGroup.setText("");
            edtCustomerGroup.setSelection(0);
            return false;
        } else {

            edtCustomerGroup.setText(name);
        }

        return true;
    }

    private void showDialogDelete() {

        cg = CustomerGroup.getCustomerGroupById(mAdapter.customerGroups.get(mPos).getId());

        String mMessage = null;
        int customerCount = CustomerGroup.getCustomerCountById(cg.getId());
        if (customerCount != 0) {
            mMessage = "Nhóm khách hàng \"" + cg.getCustomerGroupName() + "\" có " + customerCount + " khách hàng, bạn có chắc muốn xóa?";
        }

        new AlertDialog.Builder(mContext)
                .setTitle("Xóa nhóm khách hàng \"" + cg.getCustomerGroupName() + "\"")
                .setMessage(mMessage)
                .setCancelable(true)
                .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        cg.setStatus(false);
                        cg.save();

                        final List<Long> undoIds = new ArrayList<>();

                        List<Customer> customers = Customer.getCustomersByGroupId(cg.getId());
                        for (Customer c : customers) {

                            c.setCustomerGroupID(0);
                            c.save();
                            undoIds.add(c.getId());
                        }

                        mNotifyDataSetChanged();

//                                                final Thread mThread = new Thread(new Runnable() {
//                                                    @Override
//                                                    public void run() {
//
//                                                        try {
//                                                            Thread.sleep(2750);
//
//                                                            List<Customer> customers = Customer.getCustomersByGroupId(cg.getId());
//                                                            for (Customer c : customers) {
//
//                                                                c.setCustomerGroupID(0);
//                                                                c.save();
//                                                            }
//                                                            Log.d("myDebug", "run");
//
//                                                        } catch (InterruptedException e) {
//                                                            e.printStackTrace();
//                                                        }
//                                                    }
//                                                });
//                                                mThread.start();

                        Snackbar.make(customerGroupLayout, "Đã xóa nhóm \"" + cg.getCustomerGroupName() + "\"",
                                Snackbar.LENGTH_LONG)
                                .setAction(getString(R.string.undo), new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

//                                                                mThread.interrupt();

                                        cg.setStatus(true);
                                        cg.save();

                                        for (long id : undoIds) {

                                            Customer c = Customer.getCustomerById(id);
                                            c.setCustomerGroupID(cg.getId());
                                            c.save();
                                        }

                                        mNotifyDataSetChanged();
                                    }
                                })
                                .show();
                    }
                })
                .setNegativeButton(getString(R.string.no), null)
                .show();
    }
}


