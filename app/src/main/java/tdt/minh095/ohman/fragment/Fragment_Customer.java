package tdt.minh095.ohman.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.etiennelawlor.quickreturn.library.enums.QuickReturnViewType;
import com.etiennelawlor.quickreturn.library.listeners.QuickReturnRecyclerViewOnScrollListener;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import tdt.minh095.ohman.R;
import tdt.minh095.ohman.activity.CustomerDetailsActivity;
import tdt.minh095.ohman.adapter.CustomerAdapter;
import tdt.minh095.ohman.helper.Constant;
import tdt.minh095.ohman.pojo.Customer;

public class Fragment_Customer extends Fragment implements View.OnClickListener {

    private RecyclerView rvCustomer;
    private FrameLayout footerCustomer;
    private AppCompatSpinner spinnerCustomer;
    private Button btnAddNew;

    private CustomerAdapter mAdapter;

    public Button btnDeleteSelected;

    public static int FRAGMENT_RESULT = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_customer, container, false);

        rvCustomer = (RecyclerView) rootView.findViewById(R.id.list_customer);
        footerCustomer = (FrameLayout) rootView.findViewById(R.id.footer_customer);
        spinnerCustomer = (AppCompatSpinner) rootView.findViewById(R.id.spinner_group_customer);
        btnAddNew = (Button) rootView.findViewById(R.id.btnAddNew);

        btnDeleteSelected = (Button) rootView.findViewById(R.id.btnDeleteSelected);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setUpRecyclerViewCustomer();

        btnDeleteSelected.setVisibility(View.GONE);

        btnAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startCustomerDetailsActivity(-1);
            }
        });

        btnDeleteSelected.setOnClickListener(this);
    }

    public void setUpRecyclerViewCustomer() {
        rvCustomer.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvCustomer.setLayoutManager(layoutManager);

        mAdapter = new CustomerAdapter(this, Customer.getAllActive());
        rvCustomer.setAdapter(mAdapter);

        int headerHeight = getActivity().getResources().getDimensionPixelSize(R.dimen.footer_customer);
        QuickReturnRecyclerViewOnScrollListener scrollListener;
        scrollListener = new QuickReturnRecyclerViewOnScrollListener.Builder(QuickReturnViewType.FOOTER)
                .footer(footerCustomer)
                .minFooterTranslation(headerHeight)
                .isSnappable(true)
                .build();
        rvCustomer.setOnScrollListener(scrollListener);
        try {
            Field popup = AppCompatSpinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(spinnerCustomer);
            popupWindow.setHeight(headerHeight);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
        }
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//
//        if (FRAGMENT_RESULT == Constant.Statement.IS_INSERTING) {
//            Snackbar.make(footerCustomer, "Thêm khách hàng thành công...", Snackbar.LENGTH_LONG).show();
//            FRAGMENT_RESULT = 0;
//        }else if(FRAGMENT_RESULT == Constant.Statement.IS_UPDATETING){
//            Snackbar.make(footerCustomer, "Cập nhật thành công...", Snackbar.LENGTH_LONG).show();
//            FRAGMENT_RESULT = 0;
//        } if(FRAGMENT_RESULT == Constant.Statement.IS_DELETING){
//            Snackbar.make(footerCustomer, "Đã xóa khách hàng...", Snackbar.LENGTH_LONG).show();
//            FRAGMENT_RESULT = 0;
//        }
//
//        mAdapter.setModel(Customer.getAllActive());
//        mAdapter.notifyDataSetChanged();
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (resultCode){

            case Constant.Statement.IS_INSERTING:

                Snackbar.make(footerCustomer, getString(R.string.fragment_customer_add_successful_snackbar), Snackbar.LENGTH_LONG).show();
                mAdapter.setModel(Customer.getAllActive());
                mAdapter.notifyDataSetChanged();

                break;
            case Constant.Statement.IS_UPDATETING:

                Snackbar.make(footerCustomer, getString(R.string.fragment_customer_edit_successful_snackbar), Snackbar.LENGTH_LONG).show();
                mAdapter.setModel(Customer.getAllActive());
                mAdapter.notifyDataSetChanged();

                break;
            case Constant.Statement.IS_DELETING:

                Snackbar.make(footerCustomer, getString(R.string.fragment_customer_delete_successful_snackbar), Snackbar.LENGTH_LONG).show();
                mAdapter.setModel(Customer.getAllActive());
                mAdapter.notifyDataSetChanged();

                break;
        }

        mAdapter.setModel(Customer.getAllActive());
        mAdapter.notifyDataSetChanged();
    }

    public void startCustomerDetailsActivity(long id) {

        Intent intent = new Intent(this.getActivity(), CustomerDetailsActivity.class);
        intent.putExtra("id", id);
        startActivityForResult(intent, 0);

//        Fragment detailFragment = new CustomerDetailsFragment();
//        Bundle mBundle = new Bundle();
//        mBundle.putLong("id", id);
//        detailFragment.setArguments(mBundle);
//
//        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.container, detailFragment);
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commit();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btnDeleteSelected:

                AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
                builder.setTitle(getString(R.string.customer_detail_delete_alert))
                        .setCancelable(true)
                        .setNegativeButton(getString(R.string.no), null)
                        .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                final ArrayList<Long> undoLists = new ArrayList<>();

                                for (int i = mAdapter.customers.size() - 1; i >= 0; i--) {

                                    if (mAdapter.customers.get(i).isChecked()) {

                                        long id = mAdapter.customers.get(i).getId();
                                        Customer c = Customer.getCustomerById(id);
                                        c.setChecked(false);
                                        c.setStatus(false);
                                        c.save();

                                        undoLists.add(id);
                                    }
                                }

                                mAdapter.setModel(Customer.getAllActive());
                                mAdapter.notifyDataSetChanged();

                                Snackbar.make(footerCustomer, getString(R.string.fragment_customer_delete_successful_snackbar), Snackbar.LENGTH_LONG)
                                        .setAction(getString(R.string.undo), new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                                List<Customer> customers = Customer.getAll();
                                                for (int i = 0; i < customers.size(); i++) {

                                                    if (undoLists.contains(customers.get(i).getId())) {

                                                        long id = customers.get(i).getId();
                                                        Customer c = Customer.getCustomerById(id);
                                                        c.setStatus(true);
                                                        c.save();
                                                    }
                                                }

                                                mAdapter.setModel(Customer.getAllActive());
                                                mAdapter.notifyDataSetChanged();
                                            }
                                        })
                                        .show();

                                btnDeleteSelected.setVisibility(View.GONE);
                            }
                        }).show();

                break;
        }
    }
}
