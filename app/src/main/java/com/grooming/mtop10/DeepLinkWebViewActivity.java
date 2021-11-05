package com.grooming.mtop10;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class DeepLinkWebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deep_link_web_view);
        WebView webView = findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        String ck = "somecookie=AAAAAAA";
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setCookie("https://ellizeapps.com/cook", ck);
        cookieManager.setAcceptCookie(true);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {

                return super.shouldOverrideUrlLoading(view, request);
            }
        });
        Uri uri = getIntent().getData();
        if (uri != null) {
            Log.d("fff", "onCreate: " + uri);
            Log.d("fff", "onCreate: " + uri.getHost());

            webView.loadUrl("https://" + uri.getHost()+uri.getPath());
        } else {
            Toast.makeText(this, "no uri data", Toast.LENGTH_SHORT).show();
        }

    }
}