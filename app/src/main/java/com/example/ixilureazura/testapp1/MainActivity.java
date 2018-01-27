package com.example.ixilureazura.testapp1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        fromDrop = findViewById(R.id.fromDrop);
        toDrop = findViewById(R.id.toDrop);
        convertButton = findViewById(R.id.convertButton);
        convertedValue = findViewById(R.id.convertedValue);
        textFrom = findViewById(R.id.textFrom);
        textTo = findViewById(R.id.textTo);

        final ArrayList<String> currencyNames = new ArrayList<String>();

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
                        currencyNames.add(name + " (" + code + ") ");
                    }
                } catch (JSONException j) {
                    j.printStackTrace();
                }
            }
        }).execute(getString(R.string.currency_api));

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item, currencyNames);
        adapter.setDropDownViewResource(R.layout.spinner_item);

        fromDrop.setAdapter(adapter);
        toDrop.setAdapter(adapter);

        fromDrop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String text = fromDrop.getSelectedItem().toString();
                textFrom.setText("hi");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });


        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                convertedValue.setText("Hi");
            }
        });


    }




}
