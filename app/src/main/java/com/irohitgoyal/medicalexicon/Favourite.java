package com.irohitgoyal.medicalexicon;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

/**
 * Created by ROHIT on 3/5/2016.
 */
public class Favourite{
        public static boolean toggleFavourite(Context context, int detailId) {
            ContentResolver resolver = context.getContentResolver();
            Uri uri = ContentUris.withAppendedId(ContentManager.Favorite.CONTENT_FAVORITE_URI, (long) detailId);
            boolean ret = false;
            Cursor cursor = resolver.query(uri, null, null, null, null);
            if(cursor.moveToFirst()) {
                if(cursor.getInt(cursor.getColumnIndex("is_favorites")) > 0) {
                    resolver.delete(uri, null, null);
                } else {
                    resolver.insert(uri, null);
                    ret = true;
                }
            }
            cursor.close();
            return ret;
        }
}