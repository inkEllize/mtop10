package com.grooming.mtop10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class DBProxyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d_b_proxy);
        if(getIntent() != null){
            Intent evilIntent = (Intent) getIntent().getParcelableExtra("TADA");
            setResult(RESULT_OK,evilIntent);
            finish();
        }
    }
}