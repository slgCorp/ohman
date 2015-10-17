package tdt.minh095.ohman.asynctask;

import android.os.AsyncTask;
import android.util.Log;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import tdt.minh095.ohman.helper.Constant;

/**
 * Created by TrytoThuan on 03/10/2015.
 */
public class LoginAsyncTask extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... params) {

        String requestUrl = params[0];
        try {
            URL url = new URL(requestUrl);
            Log.i(Constant.TAG,"toi URL");

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(20000);
            httpURLConnection.setReadTimeout(60000);

            String method = params[1];
            String jsonParams = "";
            Log.i(Constant.TAG,"Da set timeout");
            switch (method) {
                case Constant.AUTH_SALA:
                    String user = params[2];
                    String pass = params[3];

                    jsonParams = "{ \"username\":\"" + user + "\",\n" +
                            "\"password\":\"" + pass + "\",\n" +
                            "\"latitude\":0,\n" +
                            "\"longitude\":0,\n" +
                            "\"device_token\":\"tran huu thuan\",\n" +
                            "\"ostype\":1,\n" +
                            "\"login_type\":0\n" +
                            "}";
                    break;
                case Constant.AUTH_FACEBOOK:
                    String facebookID = params[2];
                    jsonParams = "{\"fb_id\":\"" + facebookID + "\",\n" +
                            "\"latitude\":0,\n" +
                            "\"longitude\":0,\n" +
                            "\"device_token\":\"tran huu thuan\",\n" +
                            "\"ostype\":1,\n" +
                            "\"login_type\":1\n" +
                            "}";
                    break;
                case Constant.AUTH_GOOGLE:
                    Log.i(Constant.TAG, "Method: " + method);
                    String googleID = params[2];
                    jsonParams = "{\"gg_id\":\"" + googleID + "\",\n" +
                            "\"latitude\":0,\n" +
                            "\"longitude\":0,\n" +
                            "\"device_token\":\"tran huu thuan\",\n" +
                            "\"ostype\":1,\n" +
                            "\"login_type\":2\n" +
                            "}";
                    break;
                default:
                    break;
            }

            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.setDoOutput(true);
            Log.i(Constant.TAG, "Toi setsooutput");
            OutputStream out = httpURLConnection.getOutputStream();

            out.write(jsonParams.getBytes());
            out.flush();
            out.close();
            Log.i(Constant.TAG, "dong outputstream");
            int responseCode = httpURLConnection.getResponseCode();
            Log.i(Constant.TAG, "RESPONSE CODE: " + responseCode);
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
