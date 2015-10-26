package tdt.minh095.ohman.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import tdt.minh095.ohman.R;
import tdt.minh095.ohman.helper.SystemInfoHelper;
import tdt.minh095.ohman.helper.ValidationHelper;
import tdt.minh095.ohman.pojo.Industry;
import tdt.minh095.ohman.pojo.Region;
import tdt.minh095.ohman.pojo.Shop;
import tdt.minh095.ohman.pojo.ShopIndustry;

public class CreateShopActivity extends AppCompatActivity
        implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    public static final String TAG = "CreateShopActivity";
    public static final int ERROR_INSERT_SHOP = 100;
    public static final int ERROR_INSERT_SHOP_INDUSTRY = 200;
    public static final int STAGE_CREATE = 300;
    public static final int STAGE_UPDATE = 400;
    private static final long SHOP_ID_TESTING = 719;
    private static final long USER_ID_TESTING = 917;

    private static final int INDUSTRY_FOOD = 1;
    private static final int INDUSTRY_BEAUTY = 2;
    private static final int INDUSTRY_FAMILY_PRODUCT = 4;
    private static final int INDUSTRY_ELECTRONIC = 5;
    private static final int INDUSTRY_BOOK = 8;
    private static final int INDUSTRY_FASHION = 9;

    @Bind(R.id.edtShopName)
    EditText edtShopName;
    @Bind(R.id.edtShopPeriod)
    EditText edtShopPeriod;
    @Bind(R.id.ckbIndustry1)
    CheckBox ckbIndustry1;
    @Bind(R.id.ckbIndustry2)
    CheckBox ckbIndustry2;
    @Bind(R.id.ckbIndustry3)
    CheckBox ckbIndustry3;
    @Bind(R.id.ckbIndustry4)
    CheckBox ckbIndustry4;
    @Bind(R.id.ckbIndustry5)
    CheckBox ckbIndustry5;
    @Bind(R.id.ckbIndustry6)
    CheckBox ckbIndustry6;
    @Bind(R.id.btnOtherIndustry)
    AppCompatButton btnOtherIndustry;
    @Bind(R.id.spnProvincial)
    AppCompatSpinner spnProvincial;
    @Bind(R.id.spnDistrict)
    AppCompatSpinner spnDistrict;
    @Bind(R.id.edtShopAddress)
    EditText edtShopAddress;
    @Bind(R.id.edtShopPhone)
    EditText edtShopPhone;
    @Bind(R.id.btn_add_shop)
    AppCompatButton btnCreateShop;

    private ArrayList<Industry> industries;
    private RegionAdapter provincialAdapter;
    private IndustryAdapter industryAdapter;
    private int stage;

    private Shop mShop;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_shop);
        ButterKnife.bind(this);

        if (Shop.isCreated()) {
            btnCreateShop.setText("Cập nhật");
            stage = STAGE_UPDATE;
            mShop = Shop.getShopInfo();
        } else {
            btnCreateShop.setText("Tạo cửa hàng");
            stage = STAGE_CREATE;
        }

        industries = new ArrayList<>(Industry.getAllIndustry());
        industryAdapter = new IndustryAdapter(this, industries);


        ArrayList<Region> provincials = new ArrayList<>(Region.getRegionByLevel(Region.REGION_LEVEL_4));
        provincialAdapter = new RegionAdapter(this, provincials);
        spnProvincial.setAdapter(provincialAdapter);

        if (stage == STAGE_UPDATE) {
            ArrayList<ShopIndustry> shopIndustries = new ArrayList<>(ShopIndustry.getList(SHOP_ID_TESTING));

            industries = new ArrayList<>(Industry.getAllIndustry());
            for (ShopIndustry shopIndustry : shopIndustries) {
                for (Industry industry : industries) {
                    if (industry.getId() == shopIndustry.getIndustryID())
                        industry.setIsChecked(true);
                }
            }
            ckbIndustry1.setChecked(industries.get(INDUSTRY_FOOD - 1).isChecked());
            ckbIndustry2.setChecked(industries.get(INDUSTRY_FASHION - 1).isChecked());
            ckbIndustry3.setChecked(industries.get(INDUSTRY_ELECTRONIC - 1).isChecked());
            ckbIndustry4.setChecked(industries.get(INDUSTRY_BEAUTY - 1).isChecked());
            ckbIndustry5.setChecked(industries.get(INDUSTRY_FAMILY_PRODUCT - 1).isChecked());
            ckbIndustry6.setChecked(industries.get(INDUSTRY_BOOK - 1).isChecked());

            edtShopName.setText(mShop.getShopName());
            edtShopPeriod.setText(mShop.getWorkingTime());
            edtShopAddress.setText(mShop.getAddress());
            edtShopPhone.setText(mShop.getPhone());

            spnProvincial.setSelection(provincialAdapter.getPosition(mShop.getRegionL4()));
        }

        // set event
        spnProvincial.setOnItemSelectedListener(this);
        btnOtherIndustry.setOnClickListener(this);
        ckbIndustry1.setOnClickListener(this);
        ckbIndustry2.setOnClickListener(this);
        ckbIndustry3.setOnClickListener(this);
        ckbIndustry4.setOnClickListener(this);
        ckbIndustry5.setOnClickListener(this);
        ckbIndustry6.setOnClickListener(this);
        btnCreateShop.setOnClickListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spnProvincial:
                long regionID = provincialAdapter.getItemId(position);
                ArrayList<Region> districts = new ArrayList<>(Region.getRegionByParentId(regionID));
                RegionAdapter districtAdapter = new RegionAdapter(this, districts);
                spnDistrict.setAdapter(districtAdapter);
                if (stage == STAGE_UPDATE) {
                    spnDistrict.setSelection(districtAdapter.getPosition(mShop.getRegionL5()));
                }
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnOtherIndustry:
                pickIndustry();
                break;
            case R.id.ckbIndustry1:
                industryAdapter.itemClicked(INDUSTRY_FOOD - 1);
                break;
            case R.id.ckbIndustry2:
                industryAdapter.itemClicked(INDUSTRY_FASHION - 1);
                break;
            case R.id.ckbIndustry3:
                industryAdapter.itemClicked(INDUSTRY_ELECTRONIC - 1);
                break;
            case R.id.ckbIndustry4:
                industryAdapter.itemClicked(INDUSTRY_BEAUTY - 1);
                break;
            case R.id.ckbIndustry5:
                industryAdapter.itemClicked(INDUSTRY_FAMILY_PRODUCT - 1);
                break;
            case R.id.ckbIndustry6:
                industryAdapter.itemClicked(INDUSTRY_BOOK - 1);
                break;
            case R.id.btn_add_shop:
                if (stage == STAGE_CREATE) {
                    createShop();
                } else if (stage == STAGE_UPDATE) {
                    updateShop();
                }
                break;
        }
    }

    private void updateShop() {
        // Delete industry list
        ArrayList<ShopIndustry> shopIndustries = new ArrayList<>(ShopIndustry.getList(SHOP_ID_TESTING));
        for (ShopIndustry shopIndustry : shopIndustries) {
            shopIndustry.delete();
        }

        mShop.setShopName(edtShopName.getText().toString().trim());
        mShop.setWorkingTime(edtShopPeriod.getText().toString().trim());
        mShop.setRegionL4(spnProvincial.getSelectedItemId());
        mShop.setRegionL5(spnDistrict.getSelectedItemId());
        mShop.setAddress(edtShopAddress.getText().toString().trim());
        mShop.setPhone(edtShopPhone.getText().toString().trim());
        mShop.setCreatedBy(USER_ID_TESTING);
        mShop.setCreatedDateTime(SystemInfoHelper.getCurrentDatetime(true));

        if (validateShopInfo(mShop)) {
            int error = 0;
            if (mShop.save() != -1) {
                for (Industry industry : industries) {
                    if (industry.isChecked()) {
                        ShopIndustry shopIndustry = new ShopIndustry();
                        shopIndustry.setShopID(SHOP_ID_TESTING);
                        shopIndustry.setIndustryID(industry.getId());
                        shopIndustry.setCreatedBy(USER_ID_TESTING);
                        shopIndustry.setCreatedDateTime(SystemInfoHelper.getCurrentDatetime(true));
                        if (shopIndustry.save() == -1) {
                            error = ERROR_INSERT_SHOP_INDUSTRY;
                        }
                    }
                }
            } else {
                error = ERROR_INSERT_SHOP;
            }
            if (error == 0) {
                Toast.makeText(this, "Cập nhật thông tin shop thành công!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CreateShopActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                Log.d(TAG, "Error code: " + error);
            }
        }
    }

    private void createShop() {
        Shop shop = new Shop();
        shop.setShopName(edtShopName.getText().toString().trim());
        shop.setWorkingTime(edtShopPeriod.getText().toString().trim());
        shop.setRegionL4(spnProvincial.getSelectedItemId());
        shop.setRegionL5(spnDistrict.getSelectedItemId());
        shop.setAddress(edtShopAddress.getText().toString().trim());
        shop.setPhone(edtShopPhone.getText().toString().trim());
        shop.setCreatedBy(USER_ID_TESTING);
        shop.setCreatedDateTime(SystemInfoHelper.getCurrentDatetime(true));

        if (validateShopInfo(shop)) {
            int error = 0;

            // TODO: create shop in server (API) -> return shopID

            shop.setUserID(USER_ID_TESTING);
            if (shop.save() != -1) {
                for (Industry industry : industries) {
                    if (industry.isChecked()) {
                        ShopIndustry shopIndustry = new ShopIndustry();
                        shopIndustry.setShopID(SHOP_ID_TESTING);
                        shopIndustry.setIndustryID(industry.getId());
                        shopIndustry.setCreatedBy(USER_ID_TESTING);
                        shopIndustry.setCreatedDateTime(SystemInfoHelper.getCurrentDatetime(true));
                        if (shopIndustry.save() == -1) {
                            error = ERROR_INSERT_SHOP_INDUSTRY;
                        }
                    }
                }
            } else {
                error = ERROR_INSERT_SHOP;
            }
            if (error == 0) {
                Toast.makeText(this, "Tạo shop thành công", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CreateShopActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                Log.d(TAG, "Error code: " + error);
            }
        }
    }

    private boolean validateShopInfo(Shop shop) {
        String shopName = shop.getShopName();
        String shopWorkingTime = shop.getWorkingTime();
        String shopAddress = shop.getAddress();
        String shopPhone = shop.getPhone();

        if (shopName.length() < 2) {
            edtShopName.setError("Tên shop ít nhất có 2 kí tự.");
            edtShopName.requestFocus();
            return false;
        } else if (!ValidationHelper.isViString(shopName)) {
            edtShopName.setError("Tên shop không chứa kí tự đặc biệt.");
            edtShopName.requestFocus();
            return false;
        }

        if (shopWorkingTime.isEmpty()) {
            edtShopPeriod.setError("Vui lòng nhập thời gian làm việc.");
            edtShopPeriod.requestFocus();
            return false;
        }

        if (shopAddress.isEmpty()) {
            edtShopAddress.setError("Vui lòng nhập địa chỉ cửa hàng.");
            edtShopAddress.requestFocus();
            return false;
        }

        if (shopPhone.isEmpty()) {
            edtShopPhone.setError("Vui lòng nhập số điện thoại cửa hàng.");
            edtShopPhone.requestFocus();
            return false;
        } else if (shopPhone.length() < 6) {
            edtShopPhone.setError("Số điện thoại ít nhất 6 số");
            edtShopPhone.requestFocus();
            return false;
        }

        boolean hasIndustry = false;
        for (Industry industry : industries) {
            if (industry.isChecked()) {
                hasIndustry = true;
                break;
            }
        }
        if (!hasIndustry) {
            Toast.makeText(this, "Vui lòng chọn ngành kinh doanh.", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void pickIndustry() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialog = inflater.inflate(R.layout.dialog_other_industry, null);
        ListView lvOtherIndustry = (ListView) dialog.findViewById(R.id.lvOtherIndustry);
        lvOtherIndustry.setAdapter(industryAdapter);
        lvOtherIndustry.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                industryAdapter.itemClicked(position);
                industryAdapter.notifyDataSetChanged();
            }
        });
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialog)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ckbIndustry1.setChecked(industryAdapter.isChecked(INDUSTRY_FOOD - 1));
                        ckbIndustry2.setChecked(industryAdapter.isChecked(INDUSTRY_FASHION - 1));
                        ckbIndustry3.setChecked(industryAdapter.isChecked(INDUSTRY_ELECTRONIC - 1));
                        ckbIndustry4.setChecked(industryAdapter.isChecked(INDUSTRY_BEAUTY - 1));
                        ckbIndustry5.setChecked(industryAdapter.isChecked(INDUSTRY_FAMILY_PRODUCT - 1));
                        ckbIndustry6.setChecked(industryAdapter.isChecked(INDUSTRY_BOOK - 1));
                    }
                })
                .show();
    }

    class RegionAdapter extends ArrayAdapter<Region> {

        public static final int LAYOUT_RESOURCE = android.R.layout.simple_dropdown_item_1line;

        private Context context;
        private ArrayList<Region> regions;

        public RegionAdapter(Context context, ArrayList<Region> regions) {
            super(context, LAYOUT_RESOURCE, regions);
            this.context = context;
            this.regions = regions;
        }

        @Override
        public long getItemId(int position) {
            return regions.get(position).getId();
        }

        public String getRegionName(int position) {
            return regions.get(position).getRegionName();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(context, LAYOUT_RESOURCE, null);
            }
            ((TextView) convertView).setText(getRegionName(position));
            return convertView;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            return getView(position, convertView, parent);
        }

        public int getPosition(long regionID) {
            for (int i = 0; i < regions.size(); i++) {
                if (regions.get(i).getId() == regionID)
                    return i;
            }
            return -1;
        }
    }

    class IndustryAdapter extends ArrayAdapter<Industry> {

        public static final int LAYOUT_RESOURCE = android.R.layout.select_dialog_multichoice;

        private Context context;
        private ArrayList<Industry> industries;

        public IndustryAdapter(Context context, ArrayList<Industry> industries) {
            super(context, LAYOUT_RESOURCE, industries);
            this.context = context;
            this.industries = industries;
        }

        @Override
        public long getItemId(int position) {
            return industries.get(position).getId();
        }

        public String getIndustryName(int position) {
            return industries.get(position).getIndustryName();
        }

        public boolean isChecked(int position) {
            return industries.get(position).isChecked();
        }

        public void itemClicked(int position) {
            if (isChecked(position)) {
                industries.get(position).setIsChecked(false);
            } else {
                industries.get(position).setIsChecked(true);
            }
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(context, LAYOUT_RESOURCE, null);
            }
            ((CheckedTextView) convertView).setText(getIndustryName(position));
            ((CheckedTextView) convertView).setChecked(isChecked(position));
            return convertView;
        }
    }
}
