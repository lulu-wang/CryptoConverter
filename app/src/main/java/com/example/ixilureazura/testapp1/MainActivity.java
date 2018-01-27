package com.example.ixilureazura.testapp1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Spinner fromDrop;
    Spinner toDrop;
    Button convertButton;
    TextView convertedValue;
    String apiString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        fromDrop = findViewById(R.id.fromDrop);
        toDrop = findViewById(R.id.toDrop);
        convertButton = findViewById(R.id.convertButton);
        convertedValue = findViewById(R.id.convertedValue);



        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                convertedValue.setText("Hi");
            }
        });
    }




}
