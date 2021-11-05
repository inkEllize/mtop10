package com.grooming.mtop10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

public class DeepLinksActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deep_links);
        TextView tv = findViewById(R.id.tv_deeplnk_content);
        Intent intent = getIntent();
        if (intent.getData() != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("data:" + intent.getData())
                    .append("\nscheme:" + intent.getData().getScheme())
                    .append("\nhost:" + intent.getData().getHost())
                    .append("\npath:"+ intent.getData().getPath());


            for (String segment : intent.getData().getPathSegments()) {
                stringBuilder.append("\t\t\nsegment: " + segment);
            }

            tv.setText(stringBuilder);
        } else {
            tv.setText("no uri data\n");
        }
//        WebView webView = findViewById(R.id.webview);
//        webView.loadUrl(intent.getData().);
    }
}