package tdt.minh095.ohman.helper;

import android.widget.EditText;

/**
 * Created by toannguyen719 on 10/14/2015.
 */
public class IntegerHelper {

    public static int getInt(EditText editText) {
        String string = editText.getText().toString();
        try {
            return Integer.parseInt(string);
        } catch (NumberFormatException ex) {
            return 0;
        }
    }
}
