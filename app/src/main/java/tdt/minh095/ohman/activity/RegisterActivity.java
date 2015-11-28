package tdt.minh095.ohman.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.activeandroid.query.Select;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutionException;

import butterknife.Bind;
import butterknife.ButterKnife;
import tdt.minh095.ohman.R;
import tdt.minh095.ohman.asynctask.RegisterAsyncTask;
import tdt.minh095.ohman.helper.Constant;
import tdt.minh095.ohman.helper.EmailValidator;
import tdt.minh095.ohman.helper.EncryptionUtil;
import tdt.minh095.ohman.helper.NetworkUtil;
import tdt.minh095.ohman.helper.SystemInfoHelper;
import tdt.minh095.ohman.helper.ValidationHelper;
import tdt.minh095.ohman.pojo.User;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener {

    private static final String REGISTER_API = Constant.PROTOCOL + Constant.DOMAIN + Constant.API_ROOT + Constant.API_REGISTER;
    public static int GENDER_TYPE = 0;
    private static int INVALID_COUNT = 0;
    @Bind(R.id.edtUsername)
    EditText edtUsername;
    @Bind(R.id.edtPasswd)
    EditText edtPasswd;
    @Bind(R.id.edtRePasswd)
    EditText edtRePasswd;
    @Bind(R.id.edtDisplayName)
    EditText edtDisplayName;
    @Bind(R.id.edtBirthday)
    EditText edtBirthday;
    @Bind(R.id.edtEmail)
    EditText edtEmail;
    @Bind(R.id.edtPhoneNumber)
    EditText edtPhoneNumber;
    @Bind(R.id.edtAddress)
    EditText edtAddress;
    @Bind(R.id.rdgGender)
    RadioGroup rdgGender;
    @Bind(R.id.btnSignUp)
    AppCompatButton btnSignUp;
    private String loginType;
    private String facebookID, googleID = "", birthday, gender, email, name, accessToken, password = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        edtUsername.setOnFocusChangeListener(this);
        edtPasswd.setOnFocusChangeListener(this);
        edtRePasswd.setOnFocusChangeListener(this);
        edtDisplayName.setOnFocusChangeListener(this);
        edtEmail.setOnFocusChangeListener(this);
        edtAddress.setOnFocusChangeListener(this);
        edtBirthday.setOnFocusChangeListener(this);
        edtPhoneNumber.setOnFocusChangeListener(this);

        edtBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ValidationHelper.showDatetimeDialog(edtBirthday);
            }
        });

        Intent intent = getIntent();
        if (intent != null) {
            loginType = intent.getStringExtra("loginType");
            Log.i("facebbook", loginType);
            if (loginType.equals("Facebook")) {
                getValueFromFacebook(intent);
            } else if (loginType.equals("GooglePlus")) {
                getValueFromGoogle(intent);
            }
        }
        setValueUI();

        btnSignUp.setOnClickListener(this);
        rdgGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rdbMale) GENDER_TYPE = 0;
                else if (checkedId == R.id.rdbFemale) GENDER_TYPE = 1;
                else GENDER_TYPE = 2;
            }
        });
    }


    private void getValueFromFacebook(Intent intent) {

        facebookID = intent.getStringExtra("id");
        birthday = intent.getStringExtra("birthday");
        gender = intent.getStringExtra("gender");
        email = intent.getStringExtra("email");
        name = intent.getStringExtra("name");
        accessToken = intent.getStringExtra("accessToken");

    }

    private void getValueFromGoogle(Intent intent) {

        googleID = intent.getStringExtra("id");
        if ((intent.getIntExtra("gender", 2) == 0)) gender = "male";
        else if ((intent.getIntExtra("gender", 2) == 1)) gender = "female";
        else gender = "other";
        email = intent.getStringExtra("email");
        name = intent.getStringExtra("name");

    }

    private boolean isNotNull(String value) {
        return value != null;
    }

    private void setValueUI() {
        if (isNotNull(name)) edtDisplayName.setText(name);
        if (isNotNull(birthday)) edtBirthday.setText(birthday);
        if (isNotNull(email)) edtEmail.setText(email);
        if (isNotNull(gender)) {
            switch (gender) {
                case "male":
                    rdgGender.check(R.id.rdbMale);
                    break;
                case "female":
                    rdgGender.check(R.id.rdbFemale);
                    break;
                default:
                    rdgGender.check(R.id.rdbGenderOther);
                    break;
            }
        }
        if (!isNotNull(accessToken)) accessToken = "";
        if (!isNotNull(facebookID)) facebookID = "";
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {

        INVALID_COUNT = 0;
        validateInputValue();

        if (INVALID_COUNT == 0) {

            //TODO Lưu thông tin xuống DB
            name = edtDisplayName.getText().toString();
            String passwd = edtPasswd.getText().toString().trim();
            try {
                password = EncryptionUtil.SHA1(passwd);
            } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            String username = edtUsername.getText().toString().trim();
            birthday = edtBirthday.getText().toString();
            email = edtEmail.getText().toString();
            String phoneNumber = edtPhoneNumber.getText().toString();
            String address = edtAddress.getText().toString();

            User userTemp = new User(username, password, name, GENDER_TYPE, birthday, email, phoneNumber, address);
            userTemp.setCreatedDateTime(SystemInfoHelper.getCurrentDatetime(true));
            userTemp.setLastIP(NetworkUtil.getIpAddr(this));
            //TODO bổ sung thêm DeviceID, IP, Lat/Lng

            if (loginType.equals("Facebook")) {
                userTemp.setFacebookID(facebookID);
                userTemp.setFacebookToken(accessToken);
            }

            if (loginType.equals("GooglePlus")) {
                userTemp.setGoogleId(googleID);
            }

            if (sendToServer(loginType, userTemp)) {

                Toast.makeText(this, R.string.successful_register, Toast.LENGTH_SHORT).show();

                //Cập nhật trạng thái đã login
                LoginActivity.status = 1;
                User user = User.load(User.class, LoginActivity.USER_ROOT);
                user.status = (LoginActivity.status);
                user.save();
            }

        } else {
            Toast.makeText(this, R.string.not_successful_register, Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * @param loginType Normal or Facebook or GooglePlus
     * @param user      Object contains register info.
     * @return True if successfully register, otherwise False
     */
    private boolean sendToServer(String loginType, User user) {


        GsonBuilder builder = new GsonBuilder();
        builder.excludeFieldsWithoutExposeAnnotation();
        Gson gson = builder.create();

        RegisterAsyncTask asyncTask = new RegisterAsyncTask(this);
        asyncTask.execute(REGISTER_API, loginType, gson.toJson(user));

        try {

            JSONObject jsonObject = new JSONObject(asyncTask.get());
            int status = jsonObject.getInt("success");
            Log.i(Constant.TAG, "success" + status);

            if (status == 0) {

                user.save();
                //Cập nhật lại User mới nhất
                LoginActivity.USER_ROOT = new Select().from(User.class).count();
                moveToMainScreen(user.getUsername());

            } else {
                String message = jsonObject.getString("message");
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            }
        } catch (InterruptedException | ExecutionException | NullPointerException | JSONException ex) {
            ex.printStackTrace();
        }
        return (new Select().from(User.class).where("Username = ?", user.getUsername()).executeSingle()) != null;
    }

    private void validateInputValue() {
        if (edtUsername.getText().toString().trim().equals("")) {
            edtUsername.setError(getString(R.string.invalid_input_username_empty));
            ++INVALID_COUNT;
        } else if (edtUsername.getText().toString().contains(" ")) {
            edtUsername.setError(getString(R.string.invalid_input_username_whitespace));
            ++INVALID_COUNT;
        } else if (!ValidationHelper.isEnString(edtUsername.getText().toString().trim())) {
            edtUsername.setError(getString(R.string.invalid_input_username));
            ++INVALID_COUNT;
        } else if (edtUsername.getText().toString().trim().length() < 6 || edtUsername.getText().toString().trim().length() > 20) {
            edtUsername.setError(getString(R.string.invalid_input_username_length));
            ++INVALID_COUNT;
        }

        if (edtPasswd.getText().toString().trim().equals("")) {
            edtPasswd.setError(getString(R.string.invalid_input_passwd_empty));
            ++INVALID_COUNT;
        } else if (edtPasswd.getText().toString().trim().contains(" ")) {
            edtPasswd.setError(getString(R.string.invalid_input_passwd_whitespace));
            ++INVALID_COUNT;
        } else if (edtPasswd.getText().toString().length() < 6) {
            edtPasswd.setError(getString(R.string.invalid_input_passwd_least_6_char));
            ++INVALID_COUNT;
        }


        if (!edtRePasswd.getText().toString().equals(edtPasswd.getText().toString())) {
            edtRePasswd.setError(getString(R.string.invalid_input_repasswd));
            ++INVALID_COUNT;
        }


        if (edtDisplayName.getText().toString().trim().equals("")) {
            edtDisplayName.setError(getString(R.string.invalid_input_displayname_empty));
            ++INVALID_COUNT;
        }else if (edtDisplayName.getText().toString().trim().length() <2){
            edtDisplayName.setError(getString(R.string.invalid_input_displayname));
            ++INVALID_COUNT;
        }

        if (edtBirthday.getText().toString().trim().equals("")) {
            edtBirthday.setError(getString(R.string.invalid_input_birthday));
            ++INVALID_COUNT;
        }

        EmailValidator validator = new EmailValidator();
        if (!validator.validate(edtEmail.getText().toString())) {
            edtEmail.setError(getString(R.string.invalid_input_email));
            ++INVALID_COUNT;
        }

        if (edtPhoneNumber.getText().toString().length() < 10) {
            edtPhoneNumber.setError(getString(R.string.invalid_input_phone_number));
            ++INVALID_COUNT;
        }
    }

    public void moveToMainScreen(String username) {
        try {

            Intent i = new Intent(RegisterActivity.this, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);

            //Cập nhật trạng thái login
            LoginActivity.status = 1;
            User user = User.load(User.class, LoginActivity.USER_ROOT);
            user.status = (LoginActivity.status);
            user.save();

            //Hủy Activity
            finish();
        } catch (NullPointerException ex) {
            ex.getMessage();
        }

    }

    /**
     * Called when the focus state of a view has changed.
     *
     * @param v        The view whose state has changed.
     * @param hasFocus The new focus state of v.
     */
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        v = this.getCurrentFocus();
        if (v != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View v = getCurrentFocus();

        if (v != null &&
                (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE) &&
                v instanceof EditText &&
                !v.getClass().getName().startsWith("android.webkit.")) {
//            int scrcoords[] = new int[2];
//            v.getLocationOnScreen(scrcoords);
//            float x = ev.getRawX() + v.getLeft() - scrcoords[0];
//            float y = ev.getRawY() + v.getTop() - scrcoords[1];
//            if (x < v.getLeft() || x > v.getRight() || y < v.getTop() || y > v.getBottom())
//                hideKeyboard(this);
            if (ev.getRawX() < v.getLeft() || ev.getRawX() > v.getRight() || ev.getRawY() < v.getTop() || ev.getRawY() > v.getBottom() ){
                ValidationHelper.hideKeyboard(this);
            }
        }
        return super.dispatchTouchEvent(ev);
    }
}





