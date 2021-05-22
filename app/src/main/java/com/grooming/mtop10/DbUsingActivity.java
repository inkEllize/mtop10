package com.grooming.mtop10;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class DbUsingActivity extends AppCompatActivity {
    TextView tvForData;
    EditText edName;
    RadioGroup radioGroup;
    TextView tvHint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db_using);
        tvForData = findViewById(R.id.tv_dbdata);
        edName = findViewById(R.id.ed_searchname);
        edName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    searchByName(v);
                }
                return false;
            }
        });
        extractAllData();
        radioGroup = findViewById(R.id.rg_cases);
        tvHint = findViewById(R.id.tv_hint);
        tvForData.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                tvHint.setVisibility(View.VISIBLE);
                return false;
            }
        });
    }


    void extractAllData() {
        UserDBHelper2 helper2 = new UserDBHelper2(this);
        helper2.OpenWritableDB();
        SQLiteDatabase database = helper2.getDatabase();
        Cursor cursor = database.query(UserDBHelper2.TABLE, null, null, null, null, null, null);
        tvForData.setText("");
        if (cursor != null && cursor.moveToFirst()) {
            tvForData.append(getCursorStrings(cursor));
            while (cursor.moveToNext()) {
                tvForData.append("\n");
                tvForData.append(getCursorStrings(cursor));
            }
        } else {
            tvForData.setText("no data");
        }
        cursor.close();
    }

    void extractByNameData(String name, int case_) {
        UserDBHelper2 helper2 = new UserDBHelper2(this);
        helper2.OpenWritableDB();
        SQLiteDatabase database = helper2.getDatabase();
//        Cursor cursor = database.query(UserDBHelper2.TABLE,null,"name = ?",new String[]{name},null,null,null);
        try {
            Cursor cursor;
            if (case_ == R.id.rb_bad)

                cursor = database.query(UserDBHelper2.TABLE, null, String.format("name='%s'", name), null, null, null, null);

            else if (case_ == R.id.rb_verybad)

                cursor = database.rawQuery("SELECT * FROM users WHERE name = '" + name + "'", null);


            else if(case_ == R.id.rb_norm)

                cursor = database.query(UserDBHelper2.TABLE, null, "name=?", new String[]{name}, null, null, null);

            else {
                Uri uri = Uri.parse("content://"+TemporaryContentProvider.AUTHORITY+"/"+UserDBHelper.TABLE+"/"+name);
                cursor = getContentResolver().query(uri,null,null,null,null);
            }

            tvForData.setText("");
            if (cursor != null && cursor.moveToFirst()) {
                tvForData.append(getCursorStrings(cursor));
                while (cursor.moveToNext()) {
                    tvForData.append("\n");
                    tvForData.append(getCursorStrings(cursor));
                }
            } else {
                tvForData.setText(String.format("no data for name %s", name));
            }
            cursor.close();
        } catch (Exception e) {
            Toast.makeText(this, "error:" + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        }


    }


    String getCursorStrings(Cursor cursor) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < cursor.getColumnCount(); i++) {
            builder.append(cursor.getString(i)).append(" ");
        }
        return builder.toString();
    }

    public void searchByName(View view) {
        String search = edName.getText().toString();
        extractByNameData(search, radioGroup.getCheckedRadioButtonId());
    }

}