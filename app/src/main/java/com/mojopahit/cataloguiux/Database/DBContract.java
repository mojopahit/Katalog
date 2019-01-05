package com.mojopahit.cataloguiux.Database;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DBContract {
//     Untuk memanggil nama tabel, authority dan nama kolom tabel
    public static final String AUTHORITY = "com.mojopahit.cataloguiux";

    public static class MovieColumns implements BaseColumns {
        public static final String TBLMOVIE = "tblmovie";
        public static String ID_MOVIE = "_id";
        public static String TITLE_MOVIE = "title";
        public static String OVERVIEW_MOVIE = "overview";
        public static String RELEASE_MOVIE = "release";
        public static String VOTE_AVERAGE_MOVIE = "vote";
        public static String POSTER_PATH_MOVIE = "poster_path";

//    Digunakan untuk menggabungkan authority dan nama tabel
//    Sehingga menjadi content://com.mojopahit.cataloguiux/tblmovie
        public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
                .authority(AUTHORITY)
                .appendPath(TBLMOVIE)
                .build();
    }
// ==========================================================================
    public static String getColumnString(Cursor cursor, String columnName){
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnInt(Cursor cursor, String columnName){
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    public static long getColumnLong(Cursor cursor, String columnName){
        return cursor.getLong(cursor.getColumnIndex(columnName));
    }
}
