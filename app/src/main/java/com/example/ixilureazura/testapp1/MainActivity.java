package com.example.ixilureazura.testapp1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Spinner fromDrop;
    Spinner toDrop;
    Button convertButton;
    TextView convertedValue;
    TextView textFrom;
    TextView textTo;
    CryptoAPI cryptoAPI;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        fromDrop = findViewById(R.id.fromDrop);
        toDrop = findViewById(R.id.toDrop);
        convertButton = findViewById(R.id.convertButton);
        convertedValue = findViewById(R.id.convertedValue);
        progressBar = findViewById(R.id.progressBar);

        final ArrayList<String> codes = new ArrayList<>();

        final ArrayAdapter<String> fromAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item);
        final ArrayAdapter<String> toAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item);
        fromDrop.setAdapter(fromAdapter);
        toDrop.setAdapter(toAdapter);

        new CryptoAPI(new CryptoAPI.Response() {
            @Override
            public void onJSON(String jsonString) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    JSONArray rows = jsonObject.getJSONArray("rows");
                    for (int i = 0; i < rows.length(); i++) {
                        JSONObject entry = rows.getJSONObject(i);
                        String code = entry.getString("code");
                        String name = entry.getString("name");
                        codes.add(code);
                        fromAdapter.add(name + " (" + code + ") ");
                        toAdapter.add(name + " (" + code + ") ");
                    }
                    fromAdapter.notifyDataSetChanged();
                    toAdapter.notifyDataSetChanged();
                } catch (JSONException j) {
                    j.printStackTrace();
                }
            }
        }).execute(getString(R.string.currency_api));

        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fromDrop.getSelectedItem().toString().equals(toDrop.getSelectedItem().toString())) {
                    convertedValue.setText("Please select different currencies");
                } else {
                    convertButton.setVisibility(View.INVISIBLE);
                    progressBar.setVisibility(View.VISIBLE);

                    new CryptoAPI(new CryptoAPI.Response() {
                        @Override
                        public void onJSON(String jsonString) {
                            try {
                                JSONObject jsonObject = new JSONObject(jsonString);
                                if (!jsonObject.getBoolean("success")) {
                                    convertButton.setVisibility(View.VISIBLE);
                                    progressBar.setVisibility(View.INVISIBLE);
                                    convertedValue.setText(jsonObject.getString("error"));
                                } else {
                                    double price = jsonObject.getJSONObject("ticker").getDouble("price");
                                    String base = jsonObject.getJSONObject("ticker").getString("base");
                                    String target = jsonObject.getJSONObject("ticker").getString("target");
                                    convertedValue.setText(String.format("1 %s = %.2f %s", base, price, target));
                                    progressBar.setVisibility(View.INVISIBLE);
                                    convertButton.setVisibility(View.VISIBLE);
                                }
                            } catch (JSONException j) {
                                j.printStackTrace();
                            }
                        }
                    }).execute(getString(R.string.convert_api, codes.get(fromDrop.getSelectedItemPosition()), codes.get(toDrop.getSelectedItemPosition())));
                }
            }
        });


    }




}
