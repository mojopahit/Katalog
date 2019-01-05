package com.mojopahit.cataloguiux;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mojopahit.cataloguiux.Database.FavHelper;
import com.mojopahit.cataloguiux.Model.MainModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import static com.mojopahit.cataloguiux.Database.DBContract.MovieColumns.CONTENT_URI;
import static com.mojopahit.cataloguiux.Database.DBContract.MovieColumns.ID_MOVIE;
import static com.mojopahit.cataloguiux.Database.DBContract.MovieColumns.OVERVIEW_MOVIE;
import static com.mojopahit.cataloguiux.Database.DBContract.MovieColumns.POSTER_PATH_MOVIE;
import static com.mojopahit.cataloguiux.Database.DBContract.MovieColumns.RELEASE_MOVIE;
import static com.mojopahit.cataloguiux.Database.DBContract.MovieColumns.TITLE_MOVIE;
import static com.mojopahit.cataloguiux.Database.DBContract.MovieColumns.VOTE_AVERAGE_MOVIE;

public class MainDetail extends AppCompatActivity implements View.OnClickListener {
    private FavHelper favHelper;
    ImageView ivPhoto;
    TextView tvTitle, tvRelease, tvOverview, tvRating;
    Button btnFav;

    private boolean isFavorite = false;
    public static int RESULT = 200;
    public static int DELETE = 404;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_layout);

        tvTitle = findViewById(R.id.title_detail);
        tvRating = findViewById(R.id.rating_detail);
        tvRelease = findViewById(R.id.release_detail);
        tvOverview = findViewById(R.id.overview_detail);
        ivPhoto = findViewById(R.id.photo_detail);
        btnFav = findViewById(R.id.btn_favorite);
        btnFav.setOnClickListener(this);

        favHelper = new FavHelper(this);
        favHelper.open();

        ambil();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getIntent().getStringExtra("title"));
    }

    private void ambil() {
        MainModel mm = getIntent().getParcelableExtra("movie");
        tvTitle.setText(mm.getJudul());
        tvOverview.setText(mm.getDeskripsi());
        String tgl = mm.getRilis();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date d = df.parse(tgl);

            SimpleDateFormat toFormat =  new SimpleDateFormat("EEEE, dd MMM yyyy");
            String fixDate = toFormat.format(d);
            tvRelease.setText(fixDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        tvRating.setText(mm.getRating());
        Glide.with(this)
                .load("http://image.tmdb.org/t/p/w780"+mm.getFoto())
                .into(ivPhoto);
        checkFav();
    }

    private void checkFav() {
        MainModel mm = getIntent().getParcelableExtra("movie");

        Cursor cursor = favHelper.queryByIdProvider(String.valueOf(mm.getId()));
        Log.i("Info cursor bos","Cek "+Uri.parse(CONTENT_URI+"/"+mm.getId())+", "+cursor);
        if (cursor != null) {
            if (cursor.moveToFirst()) isFavorite = true;
            Log.i("Cursor", cursor+"");
            if (isFavorite){
                btnFav.setText(getResources().getString(R.string.favtrue));
            } else {
                btnFav.setText(getResources().getString(R.string.favfalse));
            }
            cursor.close();
        } else {
            isFavorite = false;
        }
    }

    @Override
    public void onClick(View v) {
        MainModel mm = getIntent().getParcelableExtra("movie");
        if (!isFavorite){
            ContentValues cv = new ContentValues();

            cv.put(ID_MOVIE, mm.getId());
            cv.put(TITLE_MOVIE, mm.getJudul());
            cv.put(OVERVIEW_MOVIE, mm.getDeskripsi());
            cv.put(RELEASE_MOVIE, mm.getRilis());
            cv.put(VOTE_AVERAGE_MOVIE, mm.getRating());
            cv.put(POSTER_PATH_MOVIE, mm.getFoto());
            getContentResolver().insert(CONTENT_URI, cv);

            Toast.makeText(this, mm.getJudul()+" ditambahkan ke daftar favorite anda !", Toast.LENGTH_SHORT).show();
            btnFav.setText(getResources().getString(R.string.favtrue));
            isFavorite = true;
        } else {
            favHelper.delete(mm.getId());

            isFavorite = false;
            Toast.makeText(this, mm.getJudul()+" dihapus dari daftar favorite anda !", Toast.LENGTH_SHORT).show();
            btnFav.setText(getResources().getString(R.string.favfalse));
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (isFavorite) {
            setResult(RESULT);
        } else {
            setResult(DELETE);
        }
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        if (isFavorite) {
            setResult(RESULT);
        } else {
            setResult(DELETE);
        }
        finish();
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
