package tdt.minh095.ohman.helper;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tdt.minh095.ohman.R;
import tdt.minh095.ohman.pojo.BadWord;

public class ValidationHelper {

    public static boolean isEnString(String s) {
        Pattern pattern = Pattern.compile("[^" + Constant.DIGITS + Constant.ALPHABET_CHARACTERS + Constant.ALLOW_SYMBOLS + "]", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(s);
        return !matcher.find();
    }

    public static boolean isViString(String s) {
        Pattern pattern = Pattern.compile("[^" + Constant.VIETNAMESE_DIACRITIC_CHARACTERS + Constant.DIGITS + Constant.ALPHABET_CHARACTERS + Constant.ALLOW_SYMBOLS + "]", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(s);
        return !matcher.find();
    }

    public static boolean checkViHumanNameString(String s) {
        Pattern pattern = Pattern.compile("[^" + Constant.VIETNAMESE_DIACRITIC_CHARACTERS + Constant.ALPHABET_CHARACTERS + Constant.DIGITS + " ." + "]", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(s);
        return !matcher.find();
    }

//    public static String getValidSpacesString(String originalString) {
//
//        StringTokenizer tokenizer = new StringTokenizer(originalString, " ");
//        String validString = "";
//        while (tokenizer.hasMoreTokens()) {
//            validString += tokenizer.nextToken() + " ";
//        }
//        return validString.trim();
//    }

    public static void addValidNameSpacesTextChanged(final EditText edt) {

        edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                String name = s.toString();
                if (name.length() != 0) {

                    String newChar = name.substring(name.length() - 1);
                    if (newChar.equals(" ")) {

                        try {

                            String lastChar = name.substring(name.length() - 2, name.length() - 1);
                            if (lastChar.equals(" ")) {

                                edt.setText(name.substring(0, name.length() - 1));
                                edt.setSelection(name.length() - 1);
                            }
                        } catch (Exception ex) {

                            edt.setText(name.substring(0, name.length() - 1));
                            edt.setSelection(name.length() - 1);
                        }
                    }
                }
            }
        });
    }

    private static String getValidFormatMoney(String money) {

        money = money.replaceAll(",", "");

        return money;
    }

    private static String getViFormatMoney(String money) {

//        StringBuilder strBuilder = new StringBuilder(money);
//        int add = 0;
//        for (int i = 1; i <= money.length(); i++) {
//
//            if (i != 1 && i % 3 == 1) {
//
//                strBuilder.insert(money.length() - i - add, ",");
//                add++;
//            }
//        }

        StringBuilder strBuilder = new StringBuilder(money);
        if (money.length() > 12) {

            strBuilder.insert(strBuilder.length() - 3, ",");
            strBuilder.insert(strBuilder.length() - 7, ",");
            strBuilder.insert(strBuilder.length() - 11, ",");
            strBuilder.insert(strBuilder.length() - 15, ",");
            return strBuilder.toString();
        }
        if (money.length() > 9) {

            strBuilder.insert(strBuilder.length() - 3, ",");
            strBuilder.insert(strBuilder.length() - 7, ",");
            strBuilder.insert(strBuilder.length() - 11, ",");
            return strBuilder.toString();
        }
        if (money.length() > 6) {

            strBuilder.insert(strBuilder.length() - 3, ",");
            strBuilder.insert(strBuilder.length() - 7, ",");
            return strBuilder.toString();
        }
        if (money.length() >= 3) {

            strBuilder.insert(strBuilder.length() - 3, ",");
            return strBuilder.toString();
        }

        return strBuilder.toString();
    }

    public static void addViMoneyTextChanged(final EditText edt) {


        edt.addTextChangedListener(new TextWatcher() {

            int beforeLength;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                beforeLength = edt.getText().toString().length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                int afterLength = s.length();
                if (afterLength != beforeLength) {

                    String validMoney = getValidFormatMoney(s.toString());
                    String viMoney = getViFormatMoney(validMoney);
                    edt.setText(viMoney);
                    edt.setSelection(viMoney.length());
                }

            }
        });
    }

    /**
     * Hide KeyBoard when click outside EditText
     *
     * @param activity : Activity's EditText in
     */
    public static void hideKeyboard(Activity activity) {
        if (activity != null && activity.getWindow() != null && activity.getWindow().getDecorView() != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
        }
    }

    public static String getValidFormatPhone(String phone) {

        phone = phone.replaceAll(" ", "");
//        phoneNumber = phoneNumber.replace("-", "");
//        phoneNumber = phoneNumber.replace("+", "");
//        phoneNumber = phoneNumber.replace("*", "");
//        phoneNumber = phoneNumber.replace("/", "");
//        phoneNumber = phoneNumber.replace("#", "");
//        phoneNumber = phoneNumber.replace("(", "");
//        phoneNumber = phoneNumber.replace(")", "");
//        phoneNumber = phoneNumber.replace(",", "");
//        phoneNumber = phoneNumber.replace(".", "");
//        phoneNumber = phoneNumber.replace(";", "");

        return phone;
    }

    public static String getViFormatPhone(String phone) {

        StringBuilder strBuilder = new StringBuilder(phone);

        if (phone.length() >= 11) {

            strBuilder.insert(8, " ");
            strBuilder.insert(4, " ");
        } else {

            if (phone.length() >= 8) {

                strBuilder.insert(7, " ");
            }
            if (phone.length() >= 4) {
                strBuilder.insert(3, " ");
            }
        }
        return strBuilder.toString();
    }

    public static void addViPhoneTextChanged(final EditText edt) {


        edt.addTextChangedListener(new TextWatcher() {

            int beforeLength;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                beforeLength = edt.getText().toString().length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                int afterLength = s.length();
                if (afterLength != beforeLength) {

                    String validPhone = getValidFormatPhone(s.toString());
                    String viPhone = getViFormatPhone(validPhone);
                    edt.setText(viPhone);
                    edt.setSelection(viPhone.length());
                }
            }
        });
    }

    public static boolean checkBadWordContaining(String text) {

        for (BadWord badWord : BadWord.getAllBadWords()) {

            StringTokenizer tokenizer = new StringTokenizer(text);
            while (tokenizer.hasMoreTokens()){

                if(tokenizer.nextToken().equals(badWord.getWord())){

                    return false;
                }
            }
        }
        return true;
    }

    public static void showDatetimeDialog(final EditText editText){

        final DatePicker dpkBirthday = new DatePicker(editText.getContext());
        dpkBirthday.setCalendarViewShown(false);
        dpkBirthday.setMaxDate(new Date().getTime());

        if (!editText.getText().toString().equals("")) {
            StringTokenizer tokenizer = new StringTokenizer(editText.getText().toString(), "-");
            int day = Integer.parseInt(tokenizer.nextToken());
            int month = Integer.parseInt(tokenizer.nextToken()) - 1;
            int year = Integer.parseInt(tokenizer.nextToken());
            dpkBirthday.updateDate(year, month, day);
        } else {
            dpkBirthday.updateDate(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        }

        new AlertDialog.Builder(editText.getContext())
                .setView(dpkBirthday)
                .setNegativeButton(editText.getContext().getString(R.string.default_cancel), null)
                .setPositiveButton(editText.getContext().getString(R.string.default_save), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Calendar birthdayCalendar = Calendar.getInstance();
                        birthdayCalendar.set(dpkBirthday.getYear(), dpkBirthday.getMonth(), dpkBirthday.getDayOfMonth());
                        Date birthDay = birthdayCalendar.getTime();
                        SimpleDateFormat birthdayFormat = new SimpleDateFormat(Constant.DATE_FORMAT_VIETNAM);
                        editText.setText(birthdayFormat.format(birthDay));
                    }
                })
                .show();
    }
}

