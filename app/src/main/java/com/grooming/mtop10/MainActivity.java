package com.grooming.mtop10;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.xml.datatype.DatatypeFactory;

public class MainActivity extends AppCompatActivity {

    public static final String AWESOME_ENCRYPTION_KEY = "DKFKSNFDKJSNFSDNF,SDFSDFD54353452##$%$^&%";

    // DO NOT USE IT IN PRODUCTION:)
    public static final String PARTNER_CRT_SHA256 = "9F:DB:27:BF:42:1B:30:8C:11:F9:92:03:9E:B7:5D:F9:35:CD:58:F7:28:F6:52:2E:7F:57:2D:13:2E:3F:F1:ED";

    public boolean checkPartner() {
        PackageManager pm = getPackageManager();
        String sign = PARTNER_CRT_SHA256.replace(":", "");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            return pm.hasSigningCertificate("com.example.app", hexToByte(sign), PackageManager.CERT_INPUT_SHA256);
        } else {
            try {
                Signature signature = pm.getPackageInfo("com.example.app", PackageManager.GET_SIGNATURES).signatures[0];
                byte[] pkgSign = MessageDigest.getInstance("SHA-256").digest(signature.toByteArray());
                return Arrays.equals(hexToByte(sign), pkgSign);

            } catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException e) {
                e.printStackTrace();
                Log.wtf("aaaa","OOOO");
            }
        }
        return false;
    }


    int checkEmulator(){
        String s = Build.BRAND.toLowerCase();
        if(s.startsWith("generic")|| s.equals("android")||s.equals("google")) return View.VISIBLE;
        return View.GONE;
    }

    public static byte[] hexToByte(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);

        setContentView(R.layout.activity_main);
//        View v = findViewById(R.id.editTextTextPersonName);
//        v.
        Log.d("task", "MTOP10: " + getTaskId());
        Log.d("guard check", "message from log.d");
        int d = Log.d("guard check", "message from log.d");
        Log.e("guard check", "d value: " + d);
        Log.e("guard check", "message from log.e");
        Log.v("guard check", "message from log.v");
        Log.i("guard check", "message from log.i");


        String test_user = "test";
        String test_password = "qwerty";
        String msg = String.format("%s:%s", test_user, test_password);
        if (BuildConfig.DEBUG) {
            Log.d("secret", msg);
            // doLogin(test_user, test_password);
        }


//        Button btn  = findViewById(R.id.button3);

//        Intent intent = new Intent();
//        intent.putExtra("uname","Vasya");
//        intent.putExtra("psw","qwerty");
//        intent.putExtra("url","httppdfdfdsfds");
//
////        intent.setComponent(new ComponentName("com.grooming.mtop10","com.grooming.mtop10.SendHttpActivity"));
//        intent.setAction("jfdgfkddkg");
//        startActivity(intent);

        saveToPrefs("Очень секретный секрет!");
//        startActivity(new Intent(this,SendHttpActivity.class));
        Log.d("ddd", AWESOME_ENCRYPTION_KEY);
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d("receiver", "dynamic receiver");

            }
        };
        IntentFilter intentFilter = new IntentFilter("MY_ACTION");
        registerReceiver(broadcastReceiver, intentFilter);
        findViewById(R.id.tv_emulator).setVisibility(checkEmulator());
    }


    void saveToPrefs(String secret) {
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        prefs.edit().putString("secret_data", secret).apply();

//        Build.getSerial()

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void startExportedActivity(View view) {


        Intent intent = new Intent();
        intent.setComponent(new ComponentName(
                "com.grooming.mtop10",
                "com.grooming.mtop10.ExportedActivity"));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("aaa", "fjgkfdkd");
        intent.putExtra("id", 13456);
        intent.putExtra("v2", "dfkl;dsf");
        intent.putExtra("p1", "parameter1");
        intent.putExtra("p2", "parameter2");

        intent.setData(Uri.parse("very secret string"));
        startActivity(intent);
        ////////////////////////
//        intent.getStringExtra("aaa");
//        startService(new Intent(this,MyIntentService.class));
    }

    public void startActivity2(View view) {
        startActivity(new Intent(this, SendHttpActivity.class));
    }

    public void showContentProvider(View view) {
        startActivity(new Intent(this, DBActivity.class));
    }


    public void db_example(View view) {
        startActivity(new Intent(this, DbUsingActivity.class));
    }

    public void files_example(View view) {
        startActivity(new Intent(this, FilesActivity.class));
    }
}