package com.grooming.mtop10;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

/**
 * неэкспортируемый провайдер, доступа к нему напрямую нет. однако к нему есть доступ через разрешения или уязвимость в DBProxyActivity
 */
public class TemporaryContentProvider extends ContentProvider {
    public static final String AUTHORITY = "com.grooming.mtop10.provider.temporarycontentprovider";
    private SQLiteDatabase db;
    static UriMatcher uriMatcher;
    static final int WHOLE_TABLE = 1;
    static final int TABLE_ROW = 2;
    static final int ONLY_NICKS = 3;
    static final int BY_NAME = 4;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, UserDBHelper2.TABLE, WHOLE_TABLE);
        uriMatcher.addURI(AUTHORITY, UserDBHelper2.TABLE + "/#", TABLE_ROW);
        uriMatcher.addURI(AUTHORITY, UserDBHelper2.TABLE + "/nicks", ONLY_NICKS);
        uriMatcher.addURI(AUTHORITY, UserDBHelper2.TABLE + "/names/*", BY_NAME);
    }

    public TemporaryContentProvider() {

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean onCreate() {
        UserDBHelper2 dbHelper = new UserDBHelper2(getContext());
        db = dbHelper.getWritableDatabase();
        return db != null;
    }
    //"content://auth/admins/1
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Log.d("cp","uri " + uri);
        switch (uriMatcher.match(uri)) {
            case WHOLE_TABLE:
                Log.d("cp","all");
                return db.query(UserDBHelper2.TABLE, projection, selection, selectionArgs, null, null, sortOrder);
            case TABLE_ROW:
                Log.d("cp","id");
                return db.query(UserDBHelper2.TABLE, projection, UserDBHelper2.C_ID+"="+uri.getLastPathSegment(),null, null, null, sortOrder);
            case ONLY_NICKS:
                Log.d("cp","nicks");
                return db.query(UserDBHelper2.TABLE, new String[]{UserDBHelper2.C_NICK}, selection, selectionArgs, null, null, sortOrder);
            case BY_NAME:
                Log.d("cp","id");

                return db.query(UserDBHelper2.TABLE, projection, UserDBHelper2.C_NAME+"='"+uri.getLastPathSegment()+"'",null, null, null, sortOrder);

            default:
                throw new IllegalArgumentException("unknown URI " + uri);
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}