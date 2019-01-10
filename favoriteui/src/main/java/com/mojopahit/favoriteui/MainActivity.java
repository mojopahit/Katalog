package com.mojopahit.favoriteui;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.mojopahit.favoriteui.Adapter.AdapterFavorite;
import com.mojopahit.favoriteui.Database.DBContract;

import static com.mojopahit.favoriteui.Database.DBContract.MovieColumns.CONTENT_URI;

public class MainActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor>, AdapterView.OnItemClickListener {

    private AdapterFavorite af;
    private ListView lvFavorite;

    private final int LOAD_FAVORITES_ID = 110;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle(getResources().getString(R.string.title));
        lvFavorite = findViewById(R.id.lv_favorite);
        af = new AdapterFavorite(this, null, true);

        lvFavorite.setAdapter(af);
        lvFavorite.setOnItemClickListener(this);

        getSupportLoaderManager().initLoader(LOAD_FAVORITES_ID, null, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportLoaderManager().restartLoader(LOAD_FAVORITES_ID, null, this);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
        return new CursorLoader(this, CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        af.swapCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        af.swapCursor(null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getSupportLoaderManager().destroyLoader(LOAD_FAVORITES_ID);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
        Cursor cursor = (Cursor) af.getItem(i);
        int id = cursor.getInt(cursor.getColumnIndexOrThrow(DBContract.MovieColumns._ID));
        String title = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.MovieColumns.TITLE_MOVIE));
        String overview = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.MovieColumns.OVERVIEW_MOVIE));
        String release = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.MovieColumns.RELEASE_MOVIE));
        String rating = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.MovieColumns.VOTE_AVERAGE_MOVIE));
        String poster = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.MovieColumns.POSTER_PATH_MOVIE));
        Toast.makeText(this, "Memilih Film : "+title, Toast.LENGTH_SHORT).show();
        Intent in = new Intent(MainActivity.this, MainDetail.class);
        in.putExtra("id", id);
        in.putExtra("title", title);
        in.putExtra("overview", overview);
        in.putExtra("release", release);
        in.putExtra("rating", rating);
        in.putExtra("poster", poster);
        startActivity(in);
    }
}
