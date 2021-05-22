package com.grooming.mtop10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.stream.Collectors;

public class FilesActivity extends AppCompatActivity {

    public static void createFile(String uname, Context context) {
        String text = "this is a text from user " + uname;
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(
                    new FileWriter(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS) + "/" + uname));
            bufferedWriter.write(text);
            bufferedWriter.close();
            Toast.makeText(context, String.format("file %s saved", uname), Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_files);
        setLongClickLisener(findViewById(R.id.ed_username));

    }

    public void createFile(View view) {
        String username = ((EditText) findViewById(R.id.ed_username)).getText().toString();
        if (TextUtils.isEmpty(username)) {
            Toast.makeText(this, "input name", Toast.LENGTH_SHORT).show();
            return;
        }
        createFile(username, this);
        findViewById(R.id.file_webview).setVisibility(View.GONE);
    }

    public void readFile(View view) {
        String username = ((EditText) findViewById(R.id.ed_username)).getText().toString();
        if (TextUtils.isEmpty(username)) {
            Toast.makeText(this, "input name", Toast.LENGTH_SHORT).show();
            return;
        }
        WebView webview = findViewById(R.id.file_webview);
        webview.setVisibility(View.VISIBLE);
//        webview.loadUrl("file://"+getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)+ "/"+username);
        FileInputStream fis = null;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS) + "/" + username));
            String text = bufferedReader.lines().collect(Collectors.joining(System.lineSeparator()));
            webview.loadData(text, "text/*", "UTF-8");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }







    public void setLongClickLisener(EditText editText){
        editText.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                editText.setText("../../../../../../../../data/data/com.grooming.mtop10/shared_prefs/prefs.xml");
                return false;
            }
        });
    }
}


//# ../../../../../../../../   -> root
//  /data/data/com.grooming.mtop10/shared_prefs
//    prefs.xml
//res: ../../../../../../../../data/data/com.grooming.mtop10/shared_prefs/prefs.xml