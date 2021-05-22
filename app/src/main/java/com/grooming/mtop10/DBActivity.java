package com.grooming.mtop10;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * данная активность позволяет получить доступ к данным из самого приложения, а также через нее можно разрешить стороннему приложению получить доступ к данным контент-провайдера
 */
public class DBActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d_b);
    }


    @Override
    protected void onResume() {
        super.onResume();
//        getAllData();
//        getIDData();
//        getNicksData();
    }

    void getAllData(Uri uri){
//        Uri uri = Uri.parse("content://"+TemporaryContentProvider.AUTHORITY+"/"+UserDBHelper.TABLE);
        TextView textView = findViewById(R.id.tv_data);
        textView.setText("");
        Cursor cursor = getContentResolver().query(uri,null,null,null,null);
        while (cursor.moveToNext()){

            String name = cursor.getString(1);
            int id = cursor.getInt(0);
            String pass = cursor.getString(2);
            String nick = cursor.getString(3);
            textView.append(String.format("\n %d %s %s %s", id,name,pass,nick));
        }
        cursor.close();
    }
    void getIDData(){
        Uri uri = Uri.parse("content://"+TemporaryContentProvider.AUTHORITY+"/"+UserDBHelper.TABLE+"/2");
        TextView textView = findViewById(R.id.tv_data);
        Cursor cursor = getContentResolver().query(uri,null,null,null,null);
        while (cursor.moveToNext()){

            String name = cursor.getString(1);
            int id = cursor.getInt(0);
            String pass = cursor.getString(2);
            String nick = cursor.getString(3);
            textView.append(String.format("\n %d %s %s %s", id,name,pass,nick));
        }
        cursor.close();
    }
    void getNicksData(Uri uri){
//        Uri uri = Uri.parse("content://"+TemporaryContentProvider.AUTHORITY+"/"+UserDBHelper.TABLE+"/nicks");
        TextView textView = findViewById(R.id.tv_data);
        textView.setText("");
        Cursor cursor = getContentResolver().query(uri,null,null,null,null);
        while (cursor.moveToNext()){

//            String name = cursor.getString(1);
//            int id = cursor.getInt(0);
//            String pass = cursor.getString(2);

            String nick = cursor.getString(0);
            textView.append(String.format("\n %s",nick));
        }
        cursor.close();
    }



    public void onClick(View view) {
        //grants permission to another app...
        Intent intent = new Intent();
        intent.setData(Uri.parse("content://" + TemporaryContentProvider.AUTHORITY + "/users/nicks"));
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);


        setResult(RESULT_OK, intent);
        grantUriPermission("com.csy6",Uri.parse("content://" + TemporaryContentProvider.AUTHORITY + "/users/nicks"),Intent.FLAG_GRANT_READ_URI_PERMISSION);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 122 && resultCode == RESULT_OK){
            getAllData(data.getData());
        } else if (requestCode == 123 && resultCode == RESULT_OK){
            getNicksData(data.getData());
        }
    }

    public void showAll(View view) {
        Intent stupidlIntent = new Intent(this,TemporaryContentProvider.class);
//        stupidlIntent.setFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
//        stupidlIntent.setClassName("com.grooming.mtop10","com.grooming.mtop10.TemporaryContentProvider");
//        evilIntent.setData(Uri.parse("content://"+AUTHORITY+"/users"));
        stupidlIntent.setData(Uri.parse("content://"+TemporaryContentProvider.AUTHORITY+"/users"));
        Intent intent = new Intent();
        intent.setClassName("com.grooming.mtop10","com.grooming.mtop10.DBProxyActivity");
        intent.putExtra("TADA",stupidlIntent);
        startActivityForResult(intent,122);
    }

    public void showNicks(View view) {
        Intent stupidlIntent = new Intent(this,TemporaryContentProvider.class);
//        stupidlIntent.setFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
//        stupidlIntent.setClassName("com.grooming.mtop10","com.grooming.mtop10.TemporaryContentProvider");
//        evilIntent.setData(Uri.parse("content://"+AUTHORITY+"/users"));
        stupidlIntent.setData(Uri.parse("content://"+TemporaryContentProvider.AUTHORITY+"/users/nicks"));
        Intent intent = new Intent();
        intent.setClassName("com.grooming.mtop10","com.grooming.mtop10.DBProxyActivity");
        intent.putExtra("TADA",stupidlIntent);
        startActivityForResult(intent,123);
    }
}