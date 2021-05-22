package com.csy6.victimintents;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.Random;

public class SecretActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secret);
        TextView textView = findViewById(R.id.tv_code);
        textView.setText("Secret code is: "+new Random().nextInt(1000));
    }
}