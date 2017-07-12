package com.example.prashant.contactapp.data;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class MessageContract {

    // The "Content authority" is a name for the entire content provider, similar to the
    // relationship between a domain name and its website.  A convenient string to use for the
    // content authority is the package name for the app, which is guaranteed to be unique on the
    // device.
    public static final String CONTENT_AUTHORITY = "com.example.prashant.contactapp";

    // Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
    // the content provider.
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_MESSAGE = "messages";

    public static final class MessageEntry implements BaseColumns {

        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.com.example.prashant.contactapp.messages";
        static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.com.example.prashant.contactapp.messages";

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MESSAGE).build();

        public static final String TABLE_NAME = "message";

        public static final String KEY_PRIMARY = "primary_key";
        public static final String KEY_ID = "id";
        public static final String KEY_NAME = "name";
        public static final String KEY_NUMBER = "number";
        public static final String KEY_OPT = "otp";
        public static final String KEY_DATE = "date";
        public static final String KEY_TIME = "time";

        public static Uri buildUri(long id) {
            //ContentUris.withAppendedId() is a helper method to create an id-based URI
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
