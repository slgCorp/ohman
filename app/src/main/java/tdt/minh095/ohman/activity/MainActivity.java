package tdt.minh095.ohman.activity;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import net.simonvt.menudrawer.MenuDrawer;

import tdt.minh095.ohman.R;
import tdt.minh095.ohman.data.DataMenu;
import tdt.minh095.ohman.fragment.Fragment_Customer;
import tdt.minh095.ohman.fragment.Fragment_Product;
import tdt.minh095.ohman.fragment.Fragment_Shop;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private MenuDrawer mDrawer;
    private ListView lstSetting;
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
        lstSetting = (ListView) findViewById(R.id.lstSetting);
        lstSetting.setDivider(null);
        DataMenu dataMenu = new DataMenu(this);
        lstSetting.setAdapter(dataMenu.getSettingAdapter());
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
                tvTitle.setText(getTAB_NAME()[position] );
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
}
