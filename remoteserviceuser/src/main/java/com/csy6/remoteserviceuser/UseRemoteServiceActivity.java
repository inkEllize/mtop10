package com.csy6.remoteserviceuser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.grooming.mtop10.IGetDataFromDB;

import java.util.ArrayList;
import java.util.List;

public class UseRemoteServiceActivity extends AppCompatActivity {
    boolean isBound = false;
    IGetDataFromDB binder = null;
    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            isBound = true;
            binder = IGetDataFromDB.Stub.asInterface(service);
            Toast.makeText(UseRemoteServiceActivity.this, "activity connected to service", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound = false;
            connection = null;
            Toast.makeText(UseRemoteServiceActivity.this, "activity disconnected from service", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onDisconnect(View view) {
        if(isBound){
            unbindService(connection);
        }
    }

    public void onConnect(View view) {
        if (!isBound) {
            Intent intent = new Intent();
            intent.setClassName("com.grooming.mtop10", "com.grooming.mtop10.PublicSrvice");
            bindService(intent, connection, BIND_AUTO_CREATE);
        }
    }

    public void onGetData(View view) {
        if(isBound && binder != null){
            try {
                TextView textView = findViewById(R.id.tv_data);
                List<String> arrayList = binder.getData("names");
                for(String s:arrayList){
                    textView.append("\n"+s);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}