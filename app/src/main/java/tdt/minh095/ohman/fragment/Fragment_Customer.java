package tdt.minh095.ohman.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.etiennelawlor.quickreturn.library.enums.QuickReturnViewType;
import com.etiennelawlor.quickreturn.library.listeners.QuickReturnRecyclerViewOnScrollListener;

import java.lang.reflect.Field;
import java.util.ArrayList;

import tdt.minh095.ohman.R;
import tdt.minh095.ohman.activity.CreateCustomerActivity;
import tdt.minh095.ohman.adapter.ContactAdapter;
import tdt.minh095.ohman.pojo.ContactInfo;


/**
 * Created by MyPC on 02/10/2015.
 */
public class Fragment_Customer extends Fragment {
    RecyclerView recyclerViewCustomer;
    FrameLayout footerCustomer;
    AppCompatSpinner spinnerCustomer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_customer, container, false);

        recyclerViewCustomer = (RecyclerView) rootView.findViewById(R.id.list_customer);
        footerCustomer = (FrameLayout) rootView.findViewById(R.id.footer_customer);
        spinnerCustomer = (AppCompatSpinner) rootView.findViewById(R.id.spinner_group_customer);
        setUpRecyclerViewCustomer();

        return rootView;
    }

    public void setUpRecyclerViewCustomer() {
        recyclerViewCustomer.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewCustomer.setLayoutManager(layoutManager);
        ContactAdapter adapter = new ContactAdapter(createData());
        recyclerViewCustomer.setAdapter(adapter);
        int headerHeight = getActivity().getResources().getDimensionPixelSize(R.dimen.footer_customer);
        QuickReturnRecyclerViewOnScrollListener scrollListener;
        scrollListener = new QuickReturnRecyclerViewOnScrollListener.Builder(QuickReturnViewType.FOOTER)
                .footer(footerCustomer)
                .minFooterTranslation(headerHeight)
                .isSnappable(true)
                .build();
        recyclerViewCustomer.setOnScrollListener(scrollListener);
        try {
            Field popup = AppCompatSpinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(spinnerCustomer);
            popupWindow.setHeight(headerHeight);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
        }
    }


    private ArrayList<ContactInfo> createData() {
        ArrayList<ContactInfo> listContact = new ArrayList<ContactInfo>();
        listContact.add(new ContactInfo("Minh", "Tphcm", "0903759493"));
        listContact.add(new ContactInfo("Minh", "Tphcm", "0903759493"));
        listContact.add(new ContactInfo("Minh", "Tphcm", "0903759493"));
        listContact.add(new ContactInfo("Minh", "Tphcm", "0903759493"));
        listContact.add(new ContactInfo("Minh", "Tphcm", "0903759493"));
        listContact.add(new ContactInfo("Minh", "Tphcm", "0903759493"));
        listContact.add(new ContactInfo("Minh", "Tphcm", "0903759493"));
        listContact.add(new ContactInfo("Minh", "Tphcm", "0903759493"));
        listContact.add(new ContactInfo("Minh", "Tphcm", "0903759493"));
        listContact.add(new ContactInfo("Minh", "Tphcm", "0903759493"));
        listContact.add(new ContactInfo("Minh", "Tphcm", "0903759493"));
        listContact.add(new ContactInfo("Minh", "Tphcm", "0903759493"));
        listContact.add(new ContactInfo("Minh", "Tphcm", "0903759493"));
        listContact.add(new ContactInfo("Minh", "Tphcm", "0903759493"));
        listContact.add(new ContactInfo("Minh", "Tphcm", "0903759493"));
        return listContact;
    }
}
