package com.csy6.simplepining;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * простейший пининг от гугла, разработчик указывает файл сертификата в конфигурационном файле xml/<filename.xml> и ссылается на конфиг в манифесте
 */
public class MainActivity extends AppCompatActivity {
    public static final String URL = "https://api.bittrex.com/api/v1.1/public/getticker?market=BTC-LTC";
    OkHttpClient client;
    TextView tv_answer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_answer = findViewById(R.id.tv_answer);
        String s = getString(R.string.fake);
    }

    public Request makeRequest(){
        Request request = new Request.Builder()
                .url(URL)
                .build();
        return request;
    }

    public void get(View view) {
        if(client == null) client = new OkHttpClient();
        client.newCall(makeRequest()).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                tv_answer.append("\n"+e.getMessage());
            }

            @Override
            public void onResponse(Response response) throws IOException {
                tv_answer.append("\n"+response.body().string());
            }
        });
    }

}