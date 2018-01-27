package com.example.ixilureazura.testapp1;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by ixilureazura on 1/26/18.
 */

public class CryptoAPI extends AsyncTask <String, String, String> {
    private Response delegate;

    public interface Response {
        void onJSON (String jsonString);
    }

    public CryptoAPI (Response response) {
        delegate = response;
    }

    @Override
    protected String doInBackground(String... params) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        try {
            URL url = new URL(params[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            InputStream stream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder builder = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append("\n");
                Log.d("CryptoAPI: ", line);
            }
            return builder.toString();

        } catch (MalformedURLException m) {
            m.printStackTrace();
        } catch (IOException i) {
            i.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException i) {
                i.printStackTrace();
            }
        }
        return null;
    }



    @Override
    protected void onPostExecute(String input) {
        super.onPostExecute(input);
        delegate.onJSON(input);
    }
}
