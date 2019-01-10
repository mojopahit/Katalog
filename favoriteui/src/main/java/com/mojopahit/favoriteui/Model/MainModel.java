package com.mojopahit.favoriteui.Model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.mojopahit.favoriteui.Database.DBContract;

import org.json.JSONException;
import org.json.JSONObject;

import static com.mojopahit.favoriteui.Database.DBContract.getColumnInt;
import static com.mojopahit.favoriteui.Database.DBContract.getColumnString;

public class MainModel implements Parcelable {

    private int id;
    private String judul, deskripsi, rilis, foto, rating;

    public MainModel() {
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

//    public MainModel(Cursor cursor){
//        this.id = getColumnInt(cursor, DBContract.MovieColumns._ID);
//        this.judul = getColumnString(cursor, DBContract.MovieColumns.TITLE_MOVIE);
//        this.deskripsi = getColumnString(cursor, DBContract.MovieColumns.OVERVIEW_MOVIE);
//        this.rilis = getColumnString(cursor, DBContract.MovieColumns.RELEASE_MOVIE);
//        this.rating = getColumnString(cursor, DBContract.MovieColumns.VOTE_AVERAGE_MOVIE);
//        this.foto = getColumnString(cursor, DBContract.MovieColumns.POSTER_PATH_MOVIE);
//    }

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

    public static final Creator<MainModel> CREATOR = new Creator<MainModel>() {
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