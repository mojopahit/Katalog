package com.mojopahit.favoriteui.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mojopahit.favoriteui.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.mojopahit.favoriteui.Database.DBContract.MovieColumns.OVERVIEW_MOVIE;
import static com.mojopahit.favoriteui.Database.DBContract.MovieColumns.POSTER_PATH_MOVIE;
import static com.mojopahit.favoriteui.Database.DBContract.MovieColumns.RELEASE_MOVIE;
import static com.mojopahit.favoriteui.Database.DBContract.MovieColumns.TITLE_MOVIE;
import static com.mojopahit.favoriteui.Database.DBContract.MovieColumns.VOTE_AVERAGE_MOVIE;
import static com.mojopahit.favoriteui.Database.DBContract.getColumnString;

public class AdapterFavorite extends CursorAdapter {
    private Context context;

    public AdapterFavorite(Context context, Cursor c, boolean autoQuery) {
        super(context, c, autoQuery);
        this.context = context;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View v = LayoutInflater.from(context).inflate(R.layout.desain_layout, parent, false);
        return v;
    }

    @Override
    public Cursor getCursor() {
        return super.getCursor();
    }

    @Override
    public void bindView(View itemView, Context context, Cursor cursor) {
        if (cursor != null){
            ImageView ivPoster = itemView.findViewById(R.id.imageview_id);
            TextView tvTitle = itemView.findViewById(R.id.tv_title);
            TextView tvOverview = itemView.findViewById(R.id.tv_overview);
            TextView tvRelease = itemView.findViewById(R.id.tv_release);
            TextView tvRating = itemView.findViewById(R.id.tv_rating);

            tvTitle.setText(getColumnString(cursor, TITLE_MOVIE));
            String overview = getColumnString(cursor, OVERVIEW_MOVIE);
            int len = overview.length();
            String cut;
            if (len == 0){
                cut = overview;
            } else if (len > 0 && len <= 10){
                cut = overview.substring(0, len);
            } else if (len > 10 && len <= 20) {
                cut = overview.substring(0, 15);
            } else {
                cut = overview.substring(0, 25);
            }
            String gabungDes = cut+" ...";
            tvOverview.setText(gabungDes);

            String tgl = getColumnString(cursor, RELEASE_MOVIE);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date d = df.parse(tgl);

                SimpleDateFormat toFormat =  new SimpleDateFormat("EEEE, dd MMM yyyy");
                String fixDate = toFormat.format(d);
                tvRelease.setText(fixDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            tvRating.setText(getColumnString(cursor, VOTE_AVERAGE_MOVIE));
            Glide.with(context).load("http://image.tmdb.org/t/p/w185"+getColumnString(cursor, POSTER_PATH_MOVIE))
                    .into(ivPoster);
        }
    }
}
