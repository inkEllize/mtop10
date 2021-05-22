package com.grooming.mtop10;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.grooming.mtop10.databinding.ActivityExportedBinding;

public class ExportedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityExportedBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_exported);
        binding.setIntent(getIntent());
        String s = getIntent().getStringExtra("key");
//        startActivityForResult(new Intent("IMAGE"),123);
//        startActivityForResult(new Intent("MP3"),122);

//
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri uri = data.getData();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}