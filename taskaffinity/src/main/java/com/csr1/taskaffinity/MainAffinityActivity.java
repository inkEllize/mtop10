package com.csr1.taskaffinity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainAffinityActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("task", "malware: " + getTaskId());
        moveTaskToBack(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.activity_main_affinity);
    }
}