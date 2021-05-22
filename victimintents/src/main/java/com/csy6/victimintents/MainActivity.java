package com.csy6.victimintents;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(getIntent()!=null && getIntent().getParcelableExtra("intent") != null){
            startActivity((Intent)getIntent().getParcelableExtra("intent"));
        }
    }

    public void checkCode(View view) {
        String code = ((EditText) findViewById(R.id.ed_secretCode)).getText().toString();
        if("1234".equals(code)){
            startActivity(new Intent(this,SecretActivity.class));
            this.finish();
        } else {
            Toast.makeText(this,"wrong code!",Toast.LENGTH_SHORT).show();
        }
    }
}