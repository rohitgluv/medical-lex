package com.irohitgoyal.medicalexicon;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.zip.ZipInputStream;

/**
 * Created by ROHIT on 3/3/2016.
 */
public class DatabaseHelper {
    private static DatabaseHelper dbHelper;
    private String DB_NAME = "db.db";
    private String DB_PATH = "/data/data/com.irohitgoyal.medicalexicon/databases/";
    private final Context context;
    private SQLiteDatabase sqlDatabase;

    private DatabaseHelper(Context paramContext) {
        this.context = paramContext;
        if (paramContext != null) {
            File localFile = this.context.getExternalFilesDir(null);
            if (localFile != null) {
                this.DB_PATH = localFile.getPath();
            }
        }
    }


    private void unZipCopy()
            throws IOException {
        InputStream localInputStream = this.context.getResources().openRawResource(R.raw.db);
        ZipInputStream localZipInputStream = new ZipInputStream(new BufferedInputStream(localInputStream));
        FileOutputStream localFileOutputStream = new FileOutputStream(new File(this.DB_PATH, this.DB_NAME).getAbsolutePath());

        try {
            if (localZipInputStream.getNextEntry() != null) {
                byte[] arrayOfByte = new byte[1024];
                for (; ; ) {
                    int i = localZipInputStream.read(arrayOfByte);
                    if (i == -1) {
                        break;
                    }
                    localFileOutputStream.write(arrayOfByte, 0, i);
                }
            }
            localZipInputStream.close();
        } finally {
            localZipInputStream.close();
            localFileOutputStream.flush();
            localFileOutputStream.close();
            localInputStream.close();
        }
        localFileOutputStream.flush();
        localFileOutputStream.close();
        localInputStream.close();
    }


    public static DatabaseHelper getInstance(Context paramContext) {
        try {
            if (dbHelper == null) {
                dbHelper = new DatabaseHelper(paramContext.getApplicationContext());
            }
            return dbHelper;
        } finally {
        }
    }

    public Uri addToFavourite(int paramInt) {
        String str = "_id=" + Integer.toString(paramInt);
        ContentValues localContentValues = new ContentValues();
        localContentValues.put("is_favorites", Integer.valueOf(1));
        this.sqlDatabase.update("dictionary", localContentValues, str, null);
        return ContentUris.withAppendedId(ContentManager.Favorite.CONTENT_FAVORITE_URI, paramInt);
    }

    public void createDataBase()
            throws IOException {
        File localFile1;
        try {
            localFile1 = this.context.getExternalFilesDir(null);
            if (localFile1 == null) {
                throw new Error("Error can't get - getExternalFilesDir");
            }
        } finally {
        }
        localFile1.mkdirs();
        if ((localFile1.mkdirs()) || (localFile1.isDirectory())) {
            File localFile2 = new File(localFile1, this.DB_NAME);
            if (!localFile2.exists()) {
                localFile2.createNewFile();
            }
            try {
                unZipCopy();
                return;
            } catch (IOException localIOException) {
                throw new Error("Error copying database", localIOException);
            }
        }
        throw new Error("Error can't create path");
    }

    public int delFavorite(int paramInt) {
        String str = "_id=" + Integer.toString(paramInt);
        ContentValues localContentValues = new ContentValues();
        localContentValues.put("is_favorites", Integer.valueOf(0));
        return this.sqlDatabase.update("dictionary", localContentValues, str, null);
    }

    public Cursor getAllTitles(String paramString, boolean paramBoolean) {
        String str1 = "SELECT _id, name, is_favorites FROM dictionary";
        if ((!paramBoolean) && (paramString != null) && (!paramString.isEmpty())) {
            String[] arrayOfString = paramString.trim().split(" ");
            if (arrayOfString.length > 0) {
                str1 = str1 + " WHERE ";
                int i = arrayOfString.length;
                for (int j = 0; j < i; j++) {
                    String str2 = arrayOfString[j];
                    if (!str2.equals(arrayOfString[0])) {
                        str1 = str1 + " AND ";
                    }
                    str1 = str1 + " name  like " + DatabaseUtils.sqlEscapeString(new StringBuilder().append("%").append(str2.toLowerCase(Locale.getDefault())).append("%").toString());
                }
            }
        }
        if (paramBoolean) {
            str1 = str1 + " WHERE is_favorites>0";
        }
        if (this.sqlDatabase == null) {
            return null;
        }
        return this.sqlDatabase.rawQuery(str1, null);
    }

    public Cursor getDetail(int paramInt) {
        String str = "SELECT * FROM dictionary WHERE _id=" + String.valueOf(paramInt);
        return this.sqlDatabase.rawQuery(str, null);
    }

    public Cursor getDetailByUrl(int paramInt) {
        String str = "SELECT * FROM dictionary WHERE NameUr=" + String.valueOf(paramInt);
        return this.sqlDatabase.rawQuery(str, null);
    }

    public Cursor getFavorite(int paramInt, String[] paramArrayOfString) {
        if ((paramArrayOfString == null) || (paramArrayOfString.length == 0)) {
            paramArrayOfString = new String[]{"is_favorites"};
        }
        String str = "SELECT " + TextUtils.strJoin(paramArrayOfString, ", ") + " FROM " + "dictionary" + " WHERE " + "_id" + "=" + String.valueOf(paramInt) + " ORDER BY " + "name";
        return this.sqlDatabase.rawQuery(str, null);
    }

    public SQLiteDatabase openDataBase()
            throws SQLException {
        try {
            if ((this.sqlDatabase == null) || (!this.sqlDatabase.isOpen())) {
                this.sqlDatabase = SQLiteDatabase.openDatabase(new File(this.context.getExternalFilesDir(null), this.DB_NAME).getAbsolutePath(), null, 0);
            }
            SQLiteDatabase localSQLiteDatabase = this.sqlDatabase;
            return localSQLiteDatabase;
        } finally {
        }
    }

    public void close() {
        try {
            if (this.sqlDatabase != null) {
                this.sqlDatabase.close();
            }
            return;
        } finally {
        }
    }

    public String testDataBase() {
            openDataBase();
        String str1 = "Test";
        try {
            Cursor localCursor = this.sqlDatabase.rawQuery("SELECT _id, name, url, definition, symptoms, causes, riskfactors, complications, preparingappointment, testsdiagnosis, treatment, lifestyleremedies, is_favorites FROM dictionary ORDER BY _id DESC LIMIT 1", null);
            if (localCursor.moveToFirst()) {
                boolean bool;
                do {
                    str1 = "";
                    bool = localCursor.moveToNext();
                } while (bool);
            }
            return str1;
        } catch (Exception localException) {
            String str2 = localException.getMessage();
            return str2;
        } finally {
            close();
        }
    }
}