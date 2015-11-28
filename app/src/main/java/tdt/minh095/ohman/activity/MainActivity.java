package tdt.minh095.ohman.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import net.simonvt.menudrawer.MenuDrawer;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import tdt.minh095.ohman.R;
import tdt.minh095.ohman.data.DataMenu;
import tdt.minh095.ohman.fragment.Fragment_Customer;
import tdt.minh095.ohman.fragment.Fragment_Product;
import tdt.minh095.ohman.fragment.Fragment_Shop;
import tdt.minh095.ohman.helper.Constant;
import tdt.minh095.ohman.helper.EncryptionUtil;
import tdt.minh095.ohman.pojo.User;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    boolean status = false;
    private User user;
    private boolean doubleBackToComeBackLogin = false;
    private final Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            doubleBackToComeBackLogin = false;
        }
    };
    private Handler mHandler = new Handler();
    private Toolbar toolbar;
    private MenuDrawer mDrawer;
    private ListView lstSetting;
    private DataMenu dataMenu;
    private TextView tvTitle;

    public String[] getTAB_NAME() {
        String[] TAB_NAME = {getString(R.string.tab_shop), getString(R.string.tab_customer)
                , getString(R.string.tab_product), getString(R.string.tab_history)};
        return TAB_NAME;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpMenuDrawer();
        setUpToolbar();
        setUpTabViewPager();
    }

    public void setUpMenuDrawer() {
        mDrawer = MenuDrawer.attach(this);
        mDrawer.setContentView(R.layout.activity_main);
        mDrawer.setMenuView(R.layout.menu_sample);
        ((TextView) mDrawer.findViewById(R.id.tvUser)).setText(getIntent().getStringExtra(Constant.USERNAME));
        lstSetting = (ListView) findViewById(R.id.lstSetting);
        lstSetting.setDivider(null);
        dataMenu = new DataMenu(this);
        lstSetting.setAdapter(dataMenu.getSettingAdapter());
        lstSetting.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        String menuItemTitle = dataMenu.lstKey.get(position).getTitle();

        if (menuItemTitle.equals(getString(R.string.navigation_logout))) {

            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            try {
                User user = User.load(User.class, LoginActivity.USER_ROOT);
                user.status = 0;
                user.save();
            } catch (NullPointerException e) {
                e.getMessage();
            }
            getSharedPreferences(Constant.LOGIN_PREFERENCES, MODE_PRIVATE)
                    .edit()
                    .putString(Constant.LOGIN_PREFERENCES_USERNAME, "")
                    .putString(Constant.LOGIN_PREFERENCES_PASSWORD, "")
                    .commit();

            startActivity(intent);
            this.finish();


        }
    }

    public void setUpToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tvTitle = (TextView) toolbar.findViewById(R.id.title_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_navigation);
        tvTitle.setText(getTAB_NAME()[0]);
    }

    private void setUpTabViewPager() {
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        SmartTabLayout viewPagerTab = (SmartTabLayout) findViewById(R.id.viewpagerTab);

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add(getTAB_NAME()[0], Fragment_Shop.class)
                .add(getTAB_NAME()[1], Fragment_Customer.class)
                .add(getTAB_NAME()[2], Fragment_Product.class)
                .add(getTAB_NAME()[3], Fragment_Product.class)
                .create());
        final LayoutInflater inflater = LayoutInflater.from(viewPagerTab.getContext());
        viewPagerTab.setCustomTabView(new SmartTabLayout.TabProvider() {
            @Override
            public View createTabView(ViewGroup container, int position, PagerAdapter adapter) {
                ImageView icon = (ImageView) inflater.inflate(R.layout.tab_icon, container, false);
                switch (position) {
                    case 0:
                        icon.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_home_white_24dp));
                        break;
                    case 1:
                        icon.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_user_group));
                        break;
                    case 2:
                        icon.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_product));
                        break;
                    case 3:
                        icon.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_history));
                        break;
                    default:
                        throw new IllegalStateException("Invalid position: " + position);
                }
                return icon;
            }
        });
        viewPagerTab.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                tvTitle.setText(getTAB_NAME()[position]);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setAdapter(adapter);
        viewPagerTab.setViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            mDrawer.openMenu();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();
        status = true;
        //Cập nhật trạng thái trước khi thoát
        try {
            User user2 = User.load(User.class, LoginActivity.USER_ROOT);
            user2.status = 0;
            user2.save();
        } catch (NullPointerException e) {
            e.getMessage();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            user = User.load(User.class, LoginActivity.USER_ROOT);
            if (user != null) {
                if (status)
                    showAlertDialog();
            }
        } catch (NullPointerException e) {
            e.getMessage();
        }

    }

    @Override
    public void onBackPressed() {
        if (doubleBackToComeBackLogin) {

            super.onBackPressed();
            return;
        }
        this.doubleBackToComeBackLogin = true;
        Toast.makeText(this, R.string.press_back_to_exit, Toast.LENGTH_SHORT).show();
        mHandler.postDelayed(mRunnable, 2000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHandler != null) {
            mHandler.removeCallbacks(mRunnable);
        }
    }

    private void showAlertDialog() {

        LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
        View mDialog = inflater.inflate(R.layout.dialog_re_enter_passwd,
                (ViewGroup) findViewById(R.id.dialog_re_enter_passwd), false);

        final EditText edtReEnterPwd = (EditText) mDialog.findViewById(R.id.edtReEnterPwd);
        edtReEnterPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setView(mDialog)
                .setCancelable(false)
                .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        try {
                            User user1 = User.load(User.class, LoginActivity.USER_ROOT);
                            user1.status = 0;
                            user1.save();
                        } catch (NullPointerException e) {
                            e.getMessage();
                        }
                        startActivity(intent);
                        finish();

                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String passwd = edtReEnterPwd.getText().toString().trim();
                        try {
                            if (!EncryptionUtil.SHA1(passwd).equals("")
                                    && EncryptionUtil.SHA1(passwd).equals(user.getPassword())) {
                                dialog.cancel();

                            } else {
                                showAlertDialog();
                            }
                        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                }).show();
    }
}
