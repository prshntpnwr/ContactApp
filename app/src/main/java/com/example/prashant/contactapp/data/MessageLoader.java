package com.example.prashant.contactapp.data;

import android.content.Context;
import android.net.Uri;
import android.support.v4.content.CursorLoader;

import com.example.prashant.contactapp.data.MessageContract.MessageEntry;

/**
 * Helper for loading a list of articles or a single article.
 */
public class MessageLoader extends CursorLoader {

    public static MessageLoader newAllMessageInstance(Context context) {
        return new MessageLoader(context, MessageEntry.CONTENT_URI);
    }

    public static MessageLoader newInstanceForItemId(Context context, long itemId) {
        return new MessageLoader(context, MessageEntry.buildUri(itemId));
    }

    private MessageLoader(Context context, Uri uri) {
        super(context, uri, Query.PROJECTION, null, null, null);
    }

    public interface Query {
        String[] PROJECTION = {
                MessageEntry.KEY_ID,
                MessageEntry.KEY_NAME,
                MessageEntry.KEY_NUMBER,
                MessageEntry.KEY_OPT,
                MessageEntry.KEY_DATE,
                MessageEntry.KEY_TIME,
        };

        int KEY_ID = 0;
        int KEY_NANME= 1;
        int KEY_NUMBER = 2;
        int KEY_OPT = 3;
        int KEY_DATE = 4;
        int KEY_TIME = 5;
    }
}