package com.csr1.showsclipboard;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    ClipboardManager clipboardManager;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.tv_clip);
        clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        clipboardManager.addPrimaryClipChangedListener(new ClipboardManager.OnPrimaryClipChangedListener() {
            @Override
            public void onPrimaryClipChanged() {
                String s = clipboardManager.getPrimaryClip().getItemAt(0).coerceToHtmlText(MainActivity.this);
                textView.append("\n"+s);
            }
        });
    }

    public void onClick(View view) {
        String s = clipboardManager.getPrimaryClip().getItemAt(0).coerceToText(MainActivity.this).toString();
        textView.append("\n"+s);
    }

}