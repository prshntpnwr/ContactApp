package com.example.prashant.contactapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.prashant.contactapp.data.MessageContract.MessageEntry;

/***
 * Create database here and also manage the version and update operation functionality
 * in case we have to update the current database version
 */

public class MessageDbHelper extends SQLiteOpenHelper {

    private final String TAG = getClass().getSimpleName();

    private static final int DATABASE_VERSION = 1;

    static final String DATABASE_NAME = "messages.db";

    public MessageDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_MOVIES_TABLE = "CREATE TABLE " + MessageEntry.TABLE_NAME + "("
                + MessageEntry.KEY_PRIMARY + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + MessageEntry.KEY_ID + " INTEGER,"
                + MessageEntry.KEY_NAME + " TEXT,"
                + MessageEntry.KEY_NUMBER + " TEXT,"
                + MessageEntry.KEY_OPT + " TEXT,"
                + MessageEntry.KEY_DATE + " TEXT,"
                + MessageEntry.KEY_TIME + " TEXT" + ")";
        db.execSQL(CREATE_MOVIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MessageEntry.TABLE_NAME);
        Log.i(TAG, "Old version = " + oldVersion + " New version = " + newVersion);
        onCreate(db);
    }

}