package tdt.minh095.ohman.asynctask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import tdt.minh095.ohman.activity.RegisterActivity;
import tdt.minh095.ohman.helper.Constant;
import tdt.minh095.ohman.pojo.User;

/**
 * Created by TrytoThuan on 03/10/2015.
 */
public class RegisterAsyncTask extends AsyncTask<String, Void, String> {

    RegisterActivity activity;

    public RegisterAsyncTask(Context context) {
        this.activity = (RegisterActivity) context;
    }

    @Override
    protected String doInBackground(String... params) {

        String requestUrl = params[0];
        try {
            URL url = new URL(requestUrl);

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(20000);
            httpURLConnection.setReadTimeout(60000);

            String method = params[1];
            String jsonUser = params[2];

            User user = (new Gson()).fromJson(jsonUser, User.class);
            String username = user.getUsername();
            String pass = user.getPassword();
            String email = user.getEmail();
            String name = user.getDisplayName();
            String birthday = user.getBirthDay();
            String phone = user.getPhone();
            String address = "";
            try {
                address = user.getAddress();
            } catch (NullPointerException ignored) {

            }

            String jsonParams = "";
            //TODO lay gia tri IP, thoi gian,........
            switch (method) {
                case "Normal":
                    jsonParams = "{ \"username\":\"" + username + "\",\n" +
                            "\"password\":\"" + pass + "\",\n" +
                            "\"email\":\"" + email + "\",\n" +
                            "\"display_name\":\"" + name + "\",\n" +
                            "\"latitude\":0,\n" +
                            "\"longitude\":0,\n" +
                            "\"birthday\":\"" + birthday + "\",\n" +
                            "\"phone\":\"" + phone + "\",\n" +
                            "\"address\":\"" + address + "\",\n" +
                            "\"device_token\":\"tran huu thuan\",\n" +
                            "\"ostype\":1,\n" +
                            "\"gender\":0,\n" +
                            "\"is_shop\":0\n" +
                            "}";
                    break;
                case "Facebook":
                    String facebookID = user.getFacebookID();
                    String facebookToken = user.getFacebookToken();
                    Log.i(Constant.TAG, facebookID + "-" + facebookToken);

                    jsonParams = "{ \"username\":\"" + username + "\",\n" +
                            "\"password\":\"" + pass + "\",\n" +
                            "\"email\":\"" + email + "\",\n" +
                            "\"fb_id\":\"" + facebookID + "\",\n" +
                            "\"fb_token\":\"" + facebookToken + "\",\n" +
                            "\"display_name\":\"" + name + "\",\n" +
                            "\"birthday\":\"" + birthday + "\",\n" +
                            "\"phone\":\"" + phone + "\",\n" +
                            "\"address\":\"" + address + "\",\n" +
                            "\"latitude\":0,\n" +
                            "\"longitude\":0,\n" +
                            "\"device_token\":\"tran huu thuan\",\n" +
                            "\"ostype\":1,\n" +
                            "\"gender\":0,\n" +
                            "\"is_shop\":0\n" +
                            "}";
                    break;
                case "GooglePlus":
                    String googleID = user.getGoogleId();
                    Log.i(Constant.TAG, "GoogleId " + googleID);
                    Log.i(Constant.TAG,username +"-" + pass + "-" + email +"-"+googleID+"-"+name+"-"+birthday+"-"+phone+"-"+address);
                    jsonParams = "{ \"username\":\"" + username + "\",\n" +
                            "\"password\":\"" + pass + "\",\n" +
                            "\"email\":\"" + email + "\",\n" +
                            "\"gg_id\":\"" + googleID + "\",\n" +
                            "\"display_name\":\"" + name + "\",\n" +
                            "\"birthday\":\"" + birthday + "\",\n" +
                            "\"phone\":\"" + phone + "\",\n" +
                            "\"address\":\"" + address + "\",\n" +
                            "\"latitude\":0,\n" +
                            "\"longitude\":0,\n" +
                            "\"device_token\":\"tran huu thuan\",\n" +
                            "\"ostype\":1,\n" +
                            "\"gender\":0,\n" +
                            "\"is_shop\":0\n" +
                            "}";
                    break;
                default:
                    break;
            }

            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.setDoOutput(true);

            OutputStream out = httpURLConnection.getOutputStream();

            out.write(jsonParams.getBytes());
            out.flush();
            out.close();

            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream is = httpURLConnection.getInputStream();

                return readDataFromStream(is);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private String readDataFromStream(InputStream is) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        String line;

        StringBuilder buffer = new StringBuilder();

        while ((line = reader.readLine()) != null) {
            buffer.append(line);
            buffer.append("\n");
        }

        reader.close();
        is.close();


        return buffer.toString();
    }

}
