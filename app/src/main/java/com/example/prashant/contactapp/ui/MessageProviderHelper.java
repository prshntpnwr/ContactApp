package com.example.prashant.contactapp.ui;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;

import com.example.prashant.contactapp.data.MessageContract.MessageEntry;
import com.example.prashant.contactapp.objects.MessageDetails;

public class MessageProviderHelper {

    public static MessageDetails getMovieFromDatabase(Activity activity, String ID) {
        MessageDetails message = null;
        Uri contentUri = MessageEntry.CONTENT_URI;
        Cursor c = activity.getContentResolver().query(contentUri, null, null, null, null);
        if (c.moveToFirst()) {
            do {
                if (ID.equals(c.getString(c.getColumnIndex(MessageEntry.KEY_ID)))) {
                    message = new MessageDetails(Integer.valueOf(c.getString(c.getColumnIndex(MessageEntry.KEY_ID))),
                            c.getString(c.getColumnIndex(MessageEntry.KEY_NAME)),
                            c.getString(c.getColumnIndex(MessageEntry.KEY_NUMBER)),
                            c.getString(c.getColumnIndex(MessageEntry.KEY_OPT)),
                            c.getString(c.getColumnIndex(MessageEntry.KEY_DATE)),
                            c.getString(c.getColumnIndex(MessageEntry.KEY_TIME)));
                    break;
                }

            } while (c.moveToNext());
        }
        c.close();
        return message;
    }

}
