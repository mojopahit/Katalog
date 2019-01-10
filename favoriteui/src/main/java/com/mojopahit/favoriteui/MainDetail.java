package com.mojopahit.favoriteui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class MainDetail extends AppCompatActivity{
    ImageView ivPhoto;
    TextView tvTitle, tvRelease, tvOverview, tvRating;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_layout);

        tvTitle = findViewById(R.id.title_detail);
        tvRating = findViewById(R.id.rating_detail);
        tvRelease = findViewById(R.id.release_detail);
        tvOverview = findViewById(R.id.overview_detail);
        ivPhoto = findViewById(R.id.photo_detail);

        tvTitle.setText(getIntent().getStringExtra("title"));
        tvOverview.setText(getIntent().getStringExtra("overview"));
        tvRating.setText(getIntent().getStringExtra("rating"));

        String tgl = getIntent().getStringExtra("release");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date d = df.parse(tgl);

            SimpleDateFormat toFormat =  new SimpleDateFormat("EEEE, dd MMM yyyy");
            String fixDate = toFormat.format(d);
            tvRelease.setText(fixDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Glide.with(this)
                .load("http://image.tmdb.org/t/p/w780"+getIntent().getStringExtra("poster"))
                .into(ivPhoto);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getIntent().getStringExtra("title"));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
