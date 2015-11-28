package tdt.minh095.ohman.helper;

import android.content.Context;
import android.content.Intent;
import android.widget.EditText;

import tdt.minh095.ohman.R;
import tdt.minh095.ohman.pojo.ResultLoginFacebook;
import tdt.minh095.ohman.pojo.User;

/**
 * Created by TrytoThuan on 06/10/2015.
 */
public class ValidateInputValue {

    public static int invalid_count = 0;
    private User temp = new User();

    /**
     *
     * @param context Activity of edtUsername, edtPassword
     * @param edtUserName Username need to validate
     * @param edtPassword Password need to validate
     */
    public static int onValidateAccount(Context context, EditText edtUserName, EditText edtPassword) {

        invalid_count = 0;

        if (edtUserName.getText().toString().trim().equals("")) {
            edtUserName.setError(context.getString(R.string.invalid_input_username_empty));
            ++invalid_count;
        }else if (edtUserName.getText().toString().trim().contains(" ")) {
            edtUserName.setError(context.getString(R.string.invalid_input_username_whitespace));
            ++invalid_count;
        }else if (!ValidationHelper.isEnString(edtUserName.getText().toString().trim())){
            edtUserName.setError(context.getString(R.string.invalid_input_username));
            ++invalid_count;
        }

        if (edtPassword.getText().toString().trim().equals("")) {
            edtPassword.setError(context.getString(R.string.invalid_input_passwd_empty));
            ++invalid_count;
        } else if (edtPassword.getText().toString().trim().contains(" ")) {
            edtPassword.setError(context.getString(R.string.invalid_input_passwd_whitespace));
            ++invalid_count;
        } else if (edtPassword.getText().toString().length() < 6) {
            edtPassword.setError(context.getString(R.string.invalid_input_passwd_least_6_char));
            ++invalid_count;
        }
        return invalid_count;
    }

    /**
     *
     * @param intent which change from Login Activity to Register Activity
     * @param result ResultLoginFacebook object return from FacebookLogin
     * @return intent after handling
     */
    public static Intent onPassValueToIntent(Intent intent, ResultLoginFacebook result) {

        try {
            intent.putExtra("id", result.getId());
        } catch (NullPointerException e) {
            e.getMessage();
        }
        try {
            intent.putExtra("birthday", result.getBirthday());
        } catch (NullPointerException e) {
            e.getMessage();
        }
        try {
            intent.putExtra("gender", result.getGender());
        } catch (NullPointerException e) {
            e.getMessage();
        }
        try {
            intent.putExtra("email", result.getEmail());
        } catch (NullPointerException e) {
            e.getMessage();
        }
        try {
            intent.putExtra("name", result.getName());
        } catch (NullPointerException e) {
            e.getMessage();
        }
        try {
            intent.putExtra("accessToken", result.getAccessToken());
        } catch (NullPointerException e) {
            e.getMessage();
        }
        return intent;
    }

}
