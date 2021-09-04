package com.grooming.mtop10;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

public class DeepLinkWebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deep_link_web_view);
        Uri uri = getIntent().getData();
        Log.d("fff", "onCreate: "+ uri);
        Log.d("fff", "onCreate: "+ uri.getHost());
        WebView webView = findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);

        webView.loadUrl("https://" + uri.getHost());

    }
}