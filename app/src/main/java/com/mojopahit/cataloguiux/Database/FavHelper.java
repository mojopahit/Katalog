package com.mojopahit.cataloguiux.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.mojopahit.cataloguiux.Model.MainModel;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.mojopahit.cataloguiux.Database.DBContract.MovieColumns.OVERVIEW_MOVIE;
import static com.mojopahit.cataloguiux.Database.DBContract.MovieColumns.POSTER_PATH_MOVIE;
import static com.mojopahit.cataloguiux.Database.DBContract.MovieColumns.RELEASE_MOVIE;
import static com.mojopahit.cataloguiux.Database.DBContract.MovieColumns.TBLMOVIE;
import static com.mojopahit.cataloguiux.Database.DBContract.MovieColumns.TITLE_MOVIE;
import static com.mojopahit.cataloguiux.Database.DBContract.MovieColumns.VOTE_AVERAGE_MOVIE;

public class FavHelper {
    private static String DB_TABLE = TBLMOVIE;
    private Context c;
    private DBHelper dbHelper;

    private SQLiteDatabase database;

    public FavHelper(Context c) {
        this.c = c;
    }

    public FavHelper open() throws SQLException {
        dbHelper = new DBHelper(c);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dbHelper.close();
    }

    public ArrayList<MainModel> query(){
        ArrayList<MainModel> arrayList = new ArrayList<MainModel>();
        Cursor cursor = database.query(DB_TABLE,
                null,
                null,
                null,
                null,
                null,_ID+" DESC",null);
        cursor.moveToFirst();
        MainModel mm;
        if (cursor.getCount() > 0){
            do {
                mm = new MainModel();
                mm.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                mm.setJudul(cursor.getString(cursor.getColumnIndexOrThrow(TITLE_MOVIE)));
                mm.setDeskripsi(cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW_MOVIE)));
                mm.setRilis(cursor.getString(cursor.getColumnIndexOrThrow(RELEASE_MOVIE)));
                mm.setRating(cursor.getString(cursor.getColumnIndexOrThrow(VOTE_AVERAGE_MOVIE)));
                mm.setFoto(cursor.getString(cursor.getColumnIndexOrThrow(POSTER_PATH_MOVIE)));

                arrayList.add(mm);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insert(MainModel mm){
        ContentValues data = new ContentValues();
        data.put(TITLE_MOVIE, mm.getJudul());
        data.put(OVERVIEW_MOVIE, mm.getDeskripsi());
        data.put(RELEASE_MOVIE, mm.getRilis());
        data.put(VOTE_AVERAGE_MOVIE, mm.getRating());
        data.put(POSTER_PATH_MOVIE, mm.getFoto());
        return database.insert(DB_TABLE, null, data);
    }

    public int delete(int id){
        return database.delete(DB_TABLE, _ID+"='"+id+"'", null);
    }

    public Cursor queryByProvider(){
        return database.query(DB_TABLE,
                null,
                null,
                null,
                null,
                null,
                _ID+" DESC");
    }

    public Cursor queryByIdProvider(String id){
        return database.query(DB_TABLE,null
                ,_ID + " = ?"
                , new String[]{id},
                null,
                null,
                null);
    }

    public long insertProvider(ContentValues values){
        Log.e("aaaa", ""+values);
        return database.insert(DB_TABLE,null,values);
    }

    public int updateProvider(String id,ContentValues values){
        return database.update(DB_TABLE,values,_ID +" = ?",new String[]{id} );
    }

    public int deleteProvider(String id){
        return database.delete(DB_TABLE,_ID + " = ?", new String[]{id});
    }
}
