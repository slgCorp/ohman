package tdt.minh095.ohman.helper;

public class Constant {

    public static final String DATETIME_FORMAT_TIMEZONE = "yyyy-MM-dd HH:mm:ss zzz";
    public static final String DATE_FORMAT_VIETNAM = "dd-MM-yyyy";
    public static final String TIME_FORMAT_24H = "HH:mm:ss";
    public static final String TIME_FORMAT_AMPM = "hh:mm:ss a";
    public static final String VIETNAMESE_DIACRITIC_CHARACTERS = "ẮẰẲẴẶĂẤẦẨẪẬÂÁÀÃẢẠĐẾỀỂỄỆÊÉÈẺẼẸÍÌỈĨỊỐỒỔỖỘÔỚỜỞỠỢƠÓÒÕỎỌỨỪỬỮỰƯÚÙỦŨỤÝỲỶỸỴ";
    public static final String ALPHABET_CHARACTERS = "A-Z";
    public static final String DIGITS = "0-9";
    public static final String ALLOW_SYMBOLS = " _";
    public static final String PROTOCOL = "http://";
    public static final String DOMAIN = "ohman.vn";
//public static final String DOMAIN = "192.168.0.142:9090";
    public static final String API_ROOT = "/api/users/";
    public static final String API_LOGIN = "login";
    public static final String API_REGISTER = "register";
    public static final String AUTH_FACEBOOK = "Facebook";
    public static final String AUTH_GOOGLE = "GooglePlus";
    public static final String AUTH_SALA = "Normal";
    public static final String TAG = "SalaGroup";

    public static final String USERNAME = "USERNAME";

    public static final String LOGIN_PREFERENCES = "LOGIN_PREFERENCES";
    public static final String LOGIN_PREFERENCES_USERNAME = "LOGIN_PREFERENCES_USERNAME";
    public static final String LOGIN_PREFERENCES_PASSWORD = "LOGIN_PREFERENCES_PASSWORD";

    public static final int CUSTOMER_MAX_AGE = 20;
    public static final int CUSTOMER_MIN_AGE = 13;

    public static final class Statement {

        public static final int IS_INSERTING = 1000;
        public static final int IS_UPDATETING = 2000;
        public static final int IS_DELETING = 3000;
    }

    public static final class RequestCode{

        public static final int PRODUCT_DETAILS = 10000;
    }

}
