package tdt.minh095.ohman.helper;

import android.widget.EditText;

public class StringHelper {

    public static String getString(EditText editText) {
        String trimString = editText.getText().toString().trim();
        if (trimString.isEmpty())
            return "";
        else
            return trimString;
    }
}
