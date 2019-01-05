package com.mojopahit.cataloguiux.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.mojopahit.cataloguiux.Database.DBContract.MovieColumns.TBLMOVIE;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_FILM = "film";
    private static final int DB_VERSION = 1;

    private static final String CREATE_TABLE = String.format("CREATE TABLE %s" +
            " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
            " %s TEXT NOT NULL," +
            " %s TEXT NOT NULL," +
            " %s TEXT NOT NULL," +
            " %s TEXT NOT NULL," +
            " %s TEXT NOT NULL)",
            DBContract.MovieColumns.TBLMOVIE,
            DBContract.MovieColumns.ID_MOVIE,
            DBContract.MovieColumns.TITLE_MOVIE,
            DBContract.MovieColumns.OVERVIEW_MOVIE,
            DBContract.MovieColumns.RELEASE_MOVIE,
            DBContract.MovieColumns.VOTE_AVERAGE_MOVIE,
            DBContract.MovieColumns.POSTER_PATH_MOVIE);

    public DBHelper(Context context) {
        super(context, DB_FILM, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TBLMOVIE);
        onCreate(db);
    }
}
