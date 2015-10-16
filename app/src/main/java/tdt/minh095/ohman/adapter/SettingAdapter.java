package tdt.minh095.ohman.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import tdt.minh095.ohman.R;
import tdt.minh095.ohman.pojo.KeySettingType;


/**
 * Created by MyPC on 25/07/2015.
 */
public class SettingAdapter extends BaseAdapter {
    public static final int TYPE_NAVIGATION_SETTING = 0;
    public static final int TYPE_TILTE = 1;
    public static final int TYPE_ON_OFF = 2;
    private static final int TYPE_MAX_COUNT = TYPE_NAVIGATION_SETTING + TYPE_TILTE  + 1;
    private LayoutInflater mInflater;
    private Context context;
    private List<KeySettingType> lstKeySetting;
    public SettingAdapter(Context context, List<KeySettingType> lstKey) {
        this.context = context;
        this.lstKeySetting = lstKey;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getItemViewType(int position) {

        return lstKeySetting.get(position).getTypeCustom();
    }

    @Override
    public int getViewTypeCount() {
        return TYPE_MAX_COUNT;
    }

    @Override
    public int getCount() {
        return lstKeySetting.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);
        switch (type) {
            case TYPE_NAVIGATION_SETTING:
                convertView = mInflater.inflate(R.layout.item_navigation_setting, null);
                TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitleNavigation);
                ImageView imageNavigation = (ImageView) convertView.findViewById(R.id.imageIconNavigation);
                imageNavigation.setImageResource(lstKeySetting.get(position).getImageIcon());
                tvTitle.setText(lstKeySetting.get(position).getTitle());
                break;
            case TYPE_TILTE:
                convertView = mInflater.inflate(R.layout.item_tittle, null);
                TextView tvTitle1 = (TextView) convertView.findViewById(R.id.tvTitle);
                tvTitle1.setText(lstKeySetting.get(position).getTitle());
                break;
        }

        return convertView;
    }
}
