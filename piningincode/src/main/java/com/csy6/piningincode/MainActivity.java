package com.csy6.piningincode;

import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.csy6.piningincode.R;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.CertificatePinner;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * с версии 24 АПИ приложения по умолчанию не доверяют сертификатам на устройстве. Разработчик может сделать пининг в коде.
 *
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
//        Base64.encode()
//        String s = getString(R.string.)
//        tv_answer.setVisibility();
    }

    public Request makeRequest(){
        Request request = new Request.Builder()
                .url(URL)
                .build();
        return request;
    }


//11:63:0F:C4:27:E5:5A:FB:65:CF:8F:79:B7:F2:78:4F:E3:E1:AF:15 - burps
    //EWMPxCflWvtlz495t/J4T+PhrxU=
    //y7iiBZmicrnRgIhWxM+aqVAvJng=
    public void get(View view) {
        if(client == null) {
            CertificatePinner certificatePinner = new CertificatePinner.Builder()

//                    .add("api.bittrex.com","sha1/EWMPxCflWvtlz495t/J4T+PhrxU=")
//                    .add("api.bittrex.com","sha1/y7iiBZmicrnRgIhWxM+aqVAvJng=")
                    .add("api.bittrex.com","sha1/oGVC7DZs7c5IQE71jLPctp1Yie0=") // bittrex
                    .build();
            client = new OkHttpClient();
            client.setCertificatePinner(certificatePinner);
        }

        client.newCall(makeRequest()).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                tv_answer.append("\n"+e.getMessage());
                Log.d("fail","cert: "+e.getMessage());
            }

            @Override
            public void onResponse(Response response) throws IOException {
                tv_answer.append("\n"+response.body().string());
                Log.d("ok:","cert: "+response.body());
            }
        });
    }

}