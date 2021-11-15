package com.grooming.mtop10;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.concurrent.Executor;

import javax.xml.datatype.DatatypeFactory;

public class MainActivity extends AppCompatActivity {
    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;

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
                Log.wtf("aaaa", "OOOO");
            }
        }
        return false;
    }


    int checkEmulator() {
        String s = Build.BRAND.toLowerCase();
        if (s.startsWith("generic") || s.equals("android") || s.equals("google"))
            return View.VISIBLE;
        return View.GONE;
    }

    boolean checkSU() {
        File file = new File("/system/xbin/su");
        return file.exists();
    }

    boolean canList() {
        File file = new File("/system");
        try {
            for (String s : file.list()) {
                Log.d("check list dir", "canList: " + s);
            }
            return true;
        } catch (SecurityException e) {
            Log.d("check list dir error ", "canList: " + e.toString());
        }
        return false;
    }

    void checkPackages() {
        PackageManager pm = getPackageManager();
        for (PackageInfo packageInfo : pm.getInstalledPackages(PackageManager.GET_META_DATA)) {
            Log.d("packages", "checkPackages: " + packageInfo.packageName);
        }

    }

    void checkWithShell(){
        String[] command = new String[] {"pm","list","packages"};
        BufferedReader bf;
        BufferedReader bfe;
        try {
           Process p = Runtime.getRuntime().exec(command);
           bf = new BufferedReader(new InputStreamReader(p.getInputStream()));
           bfe = new BufferedReader(new InputStreamReader(p.getErrorStream()));
           String line;
           while ((line = bf.readLine())!=null){
               Log.d("shell", "checkWithShell: " + line);
           }
            while ((line = bfe.readLine())!=null){
                Log.d("shell error", "checkWithShell: " + line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("shell", "checkWithShell: " + e);
        } catch (SecurityException e){
            Log.d("shell", "checkWithShell: " + e);
        }
    }


    void listProcess() {
        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        //        activityManager.getRunningServices()
        for (ActivityManager.RunningAppProcessInfo info : activityManager.getRunningAppProcesses()) {
            Log.d("processes", "listProcess: " + info.processName);
        }
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
        prepareBio();
        checkPackages();
        if (checkSU())
            findViewById(R.id.tv_issu).setVisibility(View.VISIBLE);
        else findViewById(R.id.tv_issu).setVisibility(View.GONE);
        canList();
        listProcess();
        checkWithShell();
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

    void prepareBio() {
        executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(MainActivity.this,
                executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode,
                                              @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(getApplicationContext(),
                        "Authentication error: " + errString, Toast.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void onAuthenticationSucceeded(
                    @NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(getApplicationContext(),
                        "Authentication succeeded!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(getApplicationContext(), "Authentication failed",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric login for my app")
                .setSubtitle("Log in using your biometric credential")
                .setNegativeButtonText("Use account password")
//                .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG| BiometricManager.Authenticators.DEVICE_CREDENTIAL)
                .build();

    }

    public void fingeClick(View view) {

        biometricPrompt.authenticate(promptInfo/*, here a CryptoObject needed*/);


    }

    public void toXML(View view) {
        startActivity(new Intent(this, XMLloadActivity.class));
    }
}