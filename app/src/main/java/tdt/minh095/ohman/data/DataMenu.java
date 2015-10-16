package tdt.minh095.ohman.data;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

import tdt.minh095.ohman.R;
import tdt.minh095.ohman.adapter.SettingAdapter;
import tdt.minh095.ohman.pojo.KeySettingType;

/**
 * Created by MyPC on 15/10/2015.
 */
public class DataMenu {
    private Context context;
    private List<KeySettingType> lstKey;

    public DataMenu(Context context) {
        this.context = context;
    }

    public void addItemNavigation(String tittle, int imageIcon) {
        lstKey.add(new KeySettingType(SettingAdapter.TYPE_NAVIGATION_SETTING, tittle, imageIcon));
    }

    public void addTitle(final String tittle) {
        lstKey.add(new KeySettingType(SettingAdapter.TYPE_TILTE, tittle));
    }

    public SettingAdapter getSettingAdapter() {
        lstKey = new ArrayList<KeySettingType>();
        addTitle("Tài khoản");
        addItemNavigation(context.getString(R.string.navigation_change_password), R.drawable.ic_lock);
        addItemNavigation(context.getString(R.string.navigation_logout), R.drawable.ic_logout);
        addTitle("Hệ thống");
        addItemNavigation(context.getString(R.string.navigation_passcode), R.drawable.ic_protect_app);
        addItemNavigation(context.getString(R.string.navigation_language), R.drawable.ic_language);
        addItemNavigation(context.getString(R.string.navigation_notification), R.drawable.ic_notification);
        addItemNavigation(context.getString(R.string.navigation_rate), R.drawable.ic_rating);
        addItemNavigation(context.getString(R.string.navigation_feedback), R.drawable.ic_feedback);
        addItemNavigation(context.getString(R.string.navigation_about), R.drawable.ic_about);
        addItemNavigation(context.getString(R.string.navigation_help), R.drawable.ic_help);
        return new SettingAdapter(context, lstKey);
    }
}
