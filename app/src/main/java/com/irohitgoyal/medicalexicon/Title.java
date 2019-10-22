package com.irohitgoyal.medicalexicon;

import android.database.Cursor;

/**
 * Created by ROHIT on 3/4/2016.
 */
public class Title
{
    public int id;
    public int isFavorite;
    public String names;

    public Title(Cursor paramCursor)
    {
        this.id = paramCursor.getInt(paramCursor.getColumnIndex("_id"));
        this.names = paramCursor.getString(paramCursor.getColumnIndex("name"));
        this.isFavorite = paramCursor.getInt(paramCursor.getColumnIndex("is_favorites"));
    }
}