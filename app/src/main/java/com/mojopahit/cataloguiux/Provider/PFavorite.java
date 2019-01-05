package com.mojopahit.cataloguiux.Provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.mojopahit.cataloguiux.Database.FavHelper;

import static com.mojopahit.cataloguiux.Database.DBContract.AUTHORITY;
import static com.mojopahit.cataloguiux.Database.DBContract.MovieColumns.CONTENT_URI;
import static com.mojopahit.cataloguiux.Database.DBContract.MovieColumns.TBLMOVIE;

public class PFavorite extends ContentProvider {
    private static final int FAVORITE = 1;
    private static final int FAVORITE_ID = 2;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(AUTHORITY, TBLMOVIE, FAVORITE);
        uriMatcher.addURI(AUTHORITY, TBLMOVIE+" /#", FAVORITE_ID);
    }

    private FavHelper favHelper;

    @Override
    public boolean onCreate() {
        favHelper = new FavHelper(getContext());
        favHelper.open();
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor cursor;
        switch (uriMatcher.match(uri)){
            case FAVORITE:
                cursor = favHelper.queryByProvider();
                break;
            case FAVORITE_ID:
                cursor = favHelper.queryByIdProvider(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }

        if (cursor != null){
            cursor.setNotificationUri(getContext().getContentResolver(),uri);
        }
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long added;

        switch (uriMatcher.match(uri)){
            case FAVORITE:
                added = favHelper.insertProvider(values);
                Log.i("Insert Data", "Insert data added : "+added);
                break;
            default:
                added = 0;
                break;
        }

        if (added > 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return Uri.parse(CONTENT_URI+"/"+added);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int delet;
        switch (uriMatcher.match(uri)){
            case FAVORITE_ID:
                delet = favHelper.deleteProvider(uri.getLastPathSegment());
                break;
            default:
                delet = 0;
                break;
        }

        if (delet > 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return delet;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int update;
        switch (uriMatcher.match(uri)){
            case FAVORITE_ID:
                update = favHelper.deleteProvider(uri.getLastPathSegment());
                break;
            default:
                update = 0;
                break;
        }

        if (update > 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return update;
    }
}
