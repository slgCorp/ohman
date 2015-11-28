package tdt.minh095.ohman.helper;

import android.content.Context;


import java.lang.reflect.Field;

import tdt.minh095.ohman.R;

/**
 * Created by Leon on 10/10/2015.
 */
public class AppSettingHelper {

    public static void setTheme(Context context) {

        try {
            String themeName = "MyAppTheme";// this string will be get from SharedReferences
            Field idField = R.style.class.getDeclaredField(themeName);
            int themeId = idField.getInt(idField);
            context.setTheme(themeId);
        } catch (Exception e) {

            context.setTheme(R.style.AppTheme);
        }
    }
}
