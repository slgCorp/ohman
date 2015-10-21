package tdt.minh095.ohman.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import tdt.minh095.ohman.R;
import tdt.minh095.ohman.fragment.ImagePickerFragment;
import tdt.minh095.ohman.fragment.ProductGroupFragment;
import tdt.minh095.ohman.helper.ValidationHelper;
import tdt.minh095.ohman.pojo.ProductGroup;

public class NewProductGroup extends AppCompatActivity
        implements View.OnClickListener, ImagePickerFragment.Callback {

    private FragmentManager mFragmentManager;

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;

    private ProductGroup productGroup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_new_product_group);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        mFragmentManager = getSupportFragmentManager();

        // Khi activity restart thì vẫn giữ nguyên fragment cuối cùng
        // ví dụ : trường hợp xoay màng hình
        if (mFragmentManager.getFragments() != null) {
            return;
        }

        ProductGroupFragment fragment = new ProductGroupFragment();
        mFragmentManager.beginTransaction()
                .replace(R.id.container, fragment, "ProductGroupFragment")
                .commit();

        fab.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                fab.hide();
                setTitle("Chọn ảnh đại diện");
                ImagePickerFragment fragment = new ImagePickerFragment();
                mFragmentManager.beginTransaction()
                        .replace(R.id.container, fragment)
                        .addToBackStack("ImagePickerFragment")
                        .commit();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        fab.show();
        setTitle("Nhóm sản phẩm");
    }

    @Override
    public void onImageSelected(String imageSelected) {
        // Lưu lại image local link
        productGroup = new ProductGroup();
        productGroup.setLocalLink(imageSelected);

        showInputDialog();


    }

    protected void showInputDialog() {
        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View promptView = layoutInflater.inflate(R.layout.dialog_product_group, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(promptView);

        final EditText edtProductGroupName = (EditText) promptView.findViewById(R.id.edtProductGroupName);

        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        String productGroupName = edtProductGroupName.getText().toString().trim();

                        productGroup.setProductGroupName(productGroupName);

                        // TODO: cần bổ sung các trường còn thiếu

                        long result = productGroup.save();

                        if (result != -1) {

                            mFragmentManager.popBackStack();

                            fab.show();

                            setTitle("Nhóm sản phẩm");

                            Toast.makeText(NewProductGroup.this, "Thêm nhóm sản phẩm thành công.", Toast.LENGTH_SHORT).show();

                            ProductGroupFragment fragment = (ProductGroupFragment) mFragmentManager.findFragmentByTag("ProductGroupFragment");

                            fragment.reloadData();

                        }

                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                fab.show();
                            }
                        });

        // create an alert dialog
        final AlertDialog alert = alertDialogBuilder.create();

        edtProductGroupName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String productGroupName = s.toString().trim();

                if (!productGroupName.isEmpty()) {
                    if (ProductGroup.checkName(productGroupName)) {
                        if (ValidationHelper.isViString(productGroupName)) {
                            alert.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                            edtProductGroupName.setError(null);
                        } else {
                            alert.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                            edtProductGroupName.setError("Tên nhóm chứa kí tự đặc biệt.");
                        }
                    } else {
                        alert.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                        edtProductGroupName.setError("Nhóm này đã tồn tại.");
                    }
                } else {
                    alert.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                }
            }
        });

        alert.show();
        alert.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
    }
}
