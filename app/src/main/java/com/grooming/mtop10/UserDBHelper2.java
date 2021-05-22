package com.grooming.mtop10;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class UserDBHelper2 extends SQLiteOpenHelper {
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
    private final Context context;

    private SQLiteDatabase database;
    public SQLiteDatabase getDatabase(){
        return database;
    }

    public void OpenWritableDB(){
        if(database != null && database.isOpen()){
            if(!database.isReadOnly()){
                return;
            }
            database.close();
        }
        database = getWritableDatabase();
    }

    public void OpenReadOnlyDB(){
        if(database!=null && database.isOpen()){
            if(database.isReadOnly()){
                return;
            }
            database.close();
        }
        database = SQLiteDatabase.openDatabase(context.getDatabasePath(DBNAME).getPath(),null, SQLiteDatabase.OPEN_READONLY);
    }

    public UserDBHelper2(@Nullable Context context) {
        super(context, DBNAME, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
        db.execSQL("INSERT INTO " + TABLE + "(" + C_NAME + "," + C_PASS + ","+C_NICK+") VALUES ('vasya2', '123','Vas1990');");
        db.execSQL("INSERT INTO " + TABLE + "(" + C_NAME + "," + C_PASS + ","+C_NICK+") VALUES ('peter2', 'qwerty','Pet00');");
        db.execSQL("INSERT INTO " + TABLE + "(" + C_NAME + "," + C_PASS + ","+C_NICK+") VALUES ('olya2', 'olya9999','Olya');");
        db.execSQL("INSERT INTO " + TABLE + "(" + C_NAME + "," + C_PASS + ","+C_NICK+") VALUES ('Ira2', '10122004','Irochcka111');");
        db.execSQL("INSERT INTO " + TABLE + "(" + C_NAME + "," + C_PASS + ","+C_NICK+") VALUES ('Ivan2', 'ivan123','VanDam222');");
    }




    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
