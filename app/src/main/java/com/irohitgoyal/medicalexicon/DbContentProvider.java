package com.irohitgoyal.medicalexicon;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

/**
 * Created by ROHIT on 3/9/2016.
 */
public class DbContentProvider extends ContentProvider
{
    private static final int DETAIL = 2;
    private static final int DETAIL_BY_URL = 6;
    private static final int FAVORITE = 5;
    private static final int FAVORITES = 4;
    private static final int SEARCH = 3;
    private static final int TITLES = 1;
    private static final UriMatcher s_UriMatcher = buildUriMatcher() ;
    DatabaseHelper m_contentDatabase;

    private static UriMatcher buildUriMatcher()
    {
        UriMatcher localUriMatcher = new UriMatcher(-1);
        localUriMatcher.addURI("com.irohitgoyal.medicalexicon.db", "titles", 1);
        localUriMatcher.addURI("com.irohitgoyal.medicalexicon.db", "detail/*", 2);
        localUriMatcher.addURI("com.irohitgoyal.medicalexicon.db", "detail_by_url/*", 6);
        localUriMatcher.addURI("com.irohitgoyal.medicalexicon.db", "search", 3);
        localUriMatcher.addURI("com.irohitgoyal.medicalexicon.db", "favorites", 4);
        localUriMatcher.addURI("com.irohitgoyal.medicalexicon.db", "favorite/*", 5);
        return localUriMatcher;
    }

    public int delete(Uri paramUri, String paramString, String[] paramArrayOfString)
    {
        this.m_contentDatabase.openDataBase();
        switch (s_UriMatcher.match(paramUri))
        {
            case 5: {
                int i = Integer.parseInt(paramUri.getLastPathSegment());
                return this.m_contentDatabase.delFavorite(i);
            }
        }

        return 0;
    }

    public String getType(Uri paramUri)
    {
        switch (s_UriMatcher.match(paramUri)) {
            case 1:return "vnd.android.cursor.dir/titles";
            case 3:
            case 4:

                return "vnd.android.cursor.item/detail";
        }
        throw new UnsupportedOperationException("Unknown uri: " + paramUri);
    }

    public Uri insert(Uri paramUri, ContentValues paramContentValues)
    {
        this.m_contentDatabase.openDataBase();
        switch (s_UriMatcher.match(paramUri))
        {
            case 5: {
                int i = Integer.parseInt(paramUri.getLastPathSegment());
                return this.m_contentDatabase.addToFavourite(i);
            }
        }
        return null;
    }

    public boolean onCreate()
    {
        this.m_contentDatabase = DatabaseHelper.getInstance(getContext());
        return true;
    }

    public Cursor query(Uri paramUri, String[] paramArrayOfString1, String paramString1, String[] paramArrayOfString2, String paramString2)
    {
        this.m_contentDatabase.openDataBase();
        switch (s_UriMatcher.match(paramUri)) {

            case 1:
            case 3:
            case 4:
                String str = paramUri.getQueryParameter("filter");
                Boolean localBoolean = Boolean.valueOf("true".equals(paramUri.getQueryParameter("favorites")));
                return this.m_contentDatabase.getAllTitles(str, localBoolean.booleanValue());
            case 2:
                int k = Integer.parseInt(paramUri.getLastPathSegment());
                return this.m_contentDatabase.getDetail(k);
            case 6:
                int j = Integer.parseInt(paramUri.getLastPathSegment());
                return this.m_contentDatabase.getDetailByUrl(j);
            case 5:
                int i = Integer.parseInt(paramUri.getLastPathSegment());
                return this.m_contentDatabase.getFavorite(i, paramArrayOfString1);
        }

        return null;
    }

    public int update(Uri paramUri, ContentValues paramContentValues, String paramString, String[] paramArrayOfString)
    {
        return 0;
    }
}
