package com.mojopahit.cataloguiux.Model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.mojopahit.cataloguiux.Database.DBContract;

import org.json.JSONException;
import org.json.JSONObject;

import static android.provider.BaseColumns._ID;
import static com.mojopahit.cataloguiux.Database.DBContract.MovieColumns.OVERVIEW_MOVIE;
import static com.mojopahit.cataloguiux.Database.DBContract.MovieColumns.POSTER_PATH_MOVIE;
import static com.mojopahit.cataloguiux.Database.DBContract.MovieColumns.RELEASE_MOVIE;
import static com.mojopahit.cataloguiux.Database.DBContract.MovieColumns.TITLE_MOVIE;
import static com.mojopahit.cataloguiux.Database.DBContract.MovieColumns.VOTE_AVERAGE_MOVIE;
import static com.mojopahit.cataloguiux.Database.DBContract.getColumnInt;
import static com.mojopahit.cataloguiux.Database.DBContract.getColumnString;

public class MainModel implements Parcelable {

    private int id;
    private String judul, deskripsi, rilis, foto, rating;

    public MainModel() {
    }

    public MainModel(JSONObject o) {
        try {
            int id = o.getInt("id");
            String ttl = o.getString("title");
            String des = o.getString("overview");
            String rls = o.getString("release_date");
            String img = o.getString("poster_path");
            String rtg = o.getString("vote_average");

            this.id = id;
            this.judul = ttl;
            this.deskripsi = des;
            this.rilis = rls;
            this.foto = img;
            this.rating = rtg;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public MainModel(Cursor cursor) {
        this.id = DBContract.getColumnInt(cursor, _ID);
        this.judul = DBContract.getColumnString(cursor, TITLE_MOVIE);
        this.deskripsi = DBContract.getColumnString(cursor, OVERVIEW_MOVIE);
        this.rilis = DBContract.getColumnString(cursor, RELEASE_MOVIE);
        this.rating = DBContract.getColumnString(cursor, VOTE_AVERAGE_MOVIE);
        this.foto = DBContract.getColumnString(cursor, POSTER_PATH_MOVIE);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getRilis() {
        return rilis;
    }

    public void setRilis(String rilis) {
        this.rilis = rilis;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.judul);
        dest.writeString(this.deskripsi);
        dest.writeString(this.rilis);
        dest.writeString(this.foto);
        dest.writeString(this.rating);
    }

    protected MainModel(Parcel in) {
        this.id = in.readInt();
        this.judul = in.readString();
        this.deskripsi = in.readString();
        this.rilis = in.readString();
        this.foto = in.readString();
        this.rating = in.readString();
    }

    public static final Parcelable.Creator<MainModel> CREATOR = new Parcelable.Creator<MainModel>() {
        @Override
        public MainModel createFromParcel(Parcel source) {
            return new MainModel(source);
        }

        @Override
        public MainModel[] newArray(int size) {
            return new MainModel[size];
        }
    };
}