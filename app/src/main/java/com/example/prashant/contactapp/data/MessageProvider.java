package com.example.prashant.contactapp.data;

import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.example.prashant.contactapp.data.MessageContract.MessageEntry;

import static com.example.prashant.contactapp.data.MessageContract.MessageEntry.TABLE_NAME;

/***
 * A content provider manages access to a central repository of data.
 * build uri here and manage query function (like insert, delete, update)
 */
public class MessageProvider extends android.content.ContentProvider {

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private MessageDbHelper mOpenHelper;

    private static final int MESSAGE = 0;
    private static final int MESSAGE_ID = 1;

    private static UriMatcher buildUriMatcher() {
        //The code passed into the constructor represents the code to return for the root URI.
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = MessageContract.CONTENT_AUTHORITY;

        //for the type of URI we want to add, create a corresponding code
        //for gallery
        matcher.addURI(authority, MessageContract.PATH_MESSAGE, MESSAGE);
        matcher.addURI(authority, MessageContract.PATH_MESSAGE + "/#", MESSAGE);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new MessageDbHelper(getContext());
        return true;
    }

    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);

        switch (match) {
            case MESSAGE:
                return MessageEntry.CONTENT_ITEM_TYPE;
            case MESSAGE_ID:
                return MessageEntry.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        final SQLiteDatabase db = mOpenHelper.getReadableDatabase();

        Cursor retCursor;
        switch (sUriMatcher.match(uri)) {
            case MESSAGE:
                retCursor = db.query(TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null, null,
                        sortOrder);
                break;
            case MESSAGE_ID:
                retCursor = db.query(TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null, null,
                        sortOrder);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {

        final SQLiteDatabase db = mOpenHelper.getReadableDatabase();

        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case MESSAGE:
                long _id = db.insert(TABLE_NAME, null, values);
                if (_id > 0) {
                    returnUri = MessageEntry.buildUri(_id);
                } else {
                    throw new SQLException("Failed to add a record into " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        db.close();

        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {

        final SQLiteDatabase db = mOpenHelper.getReadableDatabase();

        final int match = sUriMatcher.match(uri);
        int rowDeleted;

        if (selection == null) selection = "1";

        switch (match) {
            case MESSAGE:
                rowDeleted = db.delete(TABLE_NAME, selection, selectionArgs);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (rowDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        final SQLiteDatabase db = mOpenHelper.getReadableDatabase();

        final int match = sUriMatcher.match(uri);
        int rowUpdated;

        switch (match) {
            case MESSAGE:
                rowUpdated = db.update(TABLE_NAME, values, selection,
                        selectionArgs);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (rowUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowUpdated;
    }

}
