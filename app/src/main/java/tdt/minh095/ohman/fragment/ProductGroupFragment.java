package tdt.minh095.ohman.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import tdt.minh095.ohman.R;
import tdt.minh095.ohman.adapter.ProductGroupAdapter;
import tdt.minh095.ohman.pojo.ProductGroup;

/**
 * Dùng để hiển thị danh sách nhóm sản phẩm.
 */
public class ProductGroupFragment extends Fragment
        implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private ArrayList<ProductGroup> productGroups;
    private ProductGroupAdapter productGroupAdapter;

    ListView lvProductGroup;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_product_group, null, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lvProductGroup = (ListView) view.findViewById(R.id.lvProductGroup);

        lvProductGroup.setOnItemClickListener(this);
        lvProductGroup.setOnItemLongClickListener(this);

        productGroups = new ArrayList<>(ProductGroup.getAll());
        productGroupAdapter = new ProductGroupAdapter(getContext(), productGroups);
        lvProductGroup.setAdapter(productGroupAdapter);
    }

    public void reloadData() {
        productGroups = new ArrayList<>(ProductGroup.getAll());
        productGroupAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getContext(), "Chuyển sang màng hình sản phẩm (thuộc nhóm này)", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getContext(), "Đổi tên nhóm sản phẩm", Toast.LENGTH_SHORT).show();
        return true;
    }
}
