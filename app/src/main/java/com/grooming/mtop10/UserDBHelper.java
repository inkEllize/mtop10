package com.grooming.mtop10;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class UserDBHelper extends SQLiteOpenHelper {
    public static final String DBNAME = "user.db";
    public static final String TABLE = "users";
    public static final String C_ID = "_id";
    public static final String C_NAME = "name";
    public static final String C_NICK = "nick";
    public static final String C_PASS = "password";

    final static String CREATE_TABLE = " CREATE TABLE " + TABLE +
            " (" + C_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            C_NAME + " TEXT NOT NULL, " +
            C_PASS + " TEXT NOT NULL, " +
            C_NICK + " TEXT NOT NULL);";
    public UserDBHelper(@Nullable Context context) {
        super(context, DBNAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
        db.execSQL("INSERT INTO " + TABLE + "(" + C_NAME + "," + C_PASS + ","+C_NICK+") VALUES ('vasya', '123','Vas1990');");
        db.execSQL("INSERT INTO " + TABLE + "(" + C_NAME + "," + C_PASS + ","+C_NICK+") VALUES ('peter', 'qwerty','Pet00');");
        db.execSQL("INSERT INTO " + TABLE + "(" + C_NAME + "," + C_PASS + ","+C_NICK+") VALUES ('olya', 'olya9999','Olya');");
        db.execSQL("INSERT INTO " + TABLE + "(" + C_NAME + "," + C_PASS + ","+C_NICK+") VALUES ('Ira', '10122004','Irochcka111');");
        db.execSQL("INSERT INTO " + TABLE + "(" + C_NAME + "," + C_PASS + ","+C_NICK+") VALUES ('Ivan', 'ivan123','VanDam222');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
