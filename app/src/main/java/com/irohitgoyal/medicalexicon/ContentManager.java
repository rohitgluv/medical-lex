package com.irohitgoyal.medicalexicon;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by ROHIT on 3/4/2016.
 */
public class ContentManager {
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://com.irohitgoyal.medicalexicon.db");
    public static final String CONTENT_AUTHORITY = "com.irohitgoyal.medicalexicon.db";
    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/detail";
    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/titles";
    public static final String FAVORITES_FILTER = "favorites";
    public static final String PATH_DETAIL = "detail";
    public static final String PATH_DETAIL_BY_URL = "detail_by_url";
    public static final String PATH_FAVORITE = "favorite";
    public static final String PATH_FAVORITES = "favorites";
    public static final String PATH_SEARCH = "search";
    public static final String PATH_TITLES = "titles";
    public static final String SEARCH_FILTER = "filter";

    public static class Detail
            implements ContentManager.DictionaryColumns, BaseColumns
    {
        public static final Uri CONTENT_DETAIL_URI = ContentManager.BASE_CONTENT_URI.buildUpon().appendPath(PATH_DETAIL).build();
        public static final Uri CONTENT_DETAIL_URI_BY_URL = ContentManager.BASE_CONTENT_URI.buildUpon().appendPath(PATH_DETAIL_BY_URL).build();
    }

    static interface DictionaryColumns
    {
        public static final String COLUMN_CAUSES = "causes";
        public static final String COLUMN_COMPLICATIONS = "complications";
        public static final String COLUMN_DEFINITION = "definition";
        public static final String COLUMN_IS_FAVORITE = "is_favorites";
        public static final String COLUMN_LIFESTYLEREMEDIES = "lifestyleremedies";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_PREPARINGAPPOINTMENT = "preparingappointment";
        public static final String COLUMN_RISKFACTORS = "riskfactors";
        public static final String COLUMN_SYMPTOMS = "symptoms";
        public static final String COLUMN_TESTSDIAGNOSIS = "testsdiagnosis";
        public static final String COLUMN_TREATMENT = "treatment";
        public static final String COLUMN_URL = "url";
    }

    public static class Favorite
            implements ContentManager.FavoriteColumns, BaseColumns
    {
        public static final Uri CONTENT_FAVORITE_URI = ContentManager.BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVORITE).build();
    }

    static abstract interface FavoriteColumns
    {
        public static final String COLUMN_FOR_ORDER = "name";
        public static final String COLUMN_IS_FAVORITE = "is_favorites";
    }

    public static abstract interface Tables
    {
        public static final String Favorites = "dictionary";
        public static final String RefContent = "dictionary";
    }

    public static class Titles
            implements ContentManager.DictionaryColumns, BaseColumns
    {
        public static final Uri CONTENT_FAVORITES_URI = ContentManager.BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVORITES).build();
        public static final Uri CONTENT_SEARCH_URI;
        public static final Uri CONTENT_TITLES_URI = ContentManager.BASE_CONTENT_URI.buildUpon().appendPath(PATH_TITLES).build();

        static
        {
            CONTENT_SEARCH_URI = ContentManager.BASE_CONTENT_URI.buildUpon().appendPath(PATH_SEARCH).build();
        }
    }
}

