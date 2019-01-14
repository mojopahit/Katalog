package com.mojopahit.cataloguiux.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mojopahit.cataloguiux.MainDetail;
import com.mojopahit.cataloguiux.Model.MainModel;
import com.mojopahit.cataloguiux.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AdapterFavorite extends RecyclerView.Adapter<AdapterFavorite.VHolder> {
    private ArrayList<MainModel> listFav;
    private Activity activity;

    public AdapterFavorite(Activity activity) {
        this.activity = activity;
    }

    public void setListFav(ArrayList<MainModel> listFav) {
        this.listFav = listFav;
    }

    public VHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.desain_layout, parent, false);
        return new VHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final VHolder holder, final int i) {
        holder.tvTitle.setText(listFav.get(i).getJudul());
        holder.tvRelease.setText(listFav.get(i).getRilis());
        String pedot = listFav.get(i).getDeskripsi();
        int len = pedot.length();
        String cut;
        if (len == 0){
            cut = pedot;
        } else if (len > 0 && len <= 10){
            cut = pedot.substring(0, len);
        } else if (len > 10 && len <= 20) {
            cut = pedot.substring(0, 15);
        } else {
            cut = pedot.substring(0, 25);
        }
        String gabungDes = cut+" ...";

        holder.tvOverview.setText(gabungDes);
        String tgl = listFav.get(i).getRilis();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date d = df.parse(tgl);

            SimpleDateFormat toFormat =  new SimpleDateFormat("EEEE, dd MMM yyyy");
            String fixDate = toFormat.format(d);
            holder.tvRelease.setText(fixDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.tvRating.setText(listFav.get(i).getRating());

        Glide.with(holder.itemView.getContext()).load("http://image.tmdb.org/t/p/w185"+listFav.get(i).getFoto())
                .into(holder.ivPoster);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainModel dm = listFav.get(i);
                Intent in = new Intent(holder.itemView.getContext(), MainDetail.class);
                in.putExtra("movie", dm);
                in.putExtra("title", listFav.get(i).getJudul());
                holder.itemView.getContext().startActivity(in);
            }
        });
    }


    @Override
    public int getItemCount() {
        if (listFav == null) return 0;
        return listFav.size();
    }

    public static class VHolder extends RecyclerView.ViewHolder {
        private ImageView ivPoster;
        private TextView tvTitle, tvOverview, tvRelease, tvRating;
        private Button tbFav;

        public VHolder(@NonNull View itemView) {
            super(itemView);

            ivPoster = itemView.findViewById(R.id.imageview_id);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvOverview = itemView.findViewById(R.id.tv_overview);
            tvRelease = itemView.findViewById(R.id.tv_release);
            tvRating = itemView.findViewById(R.id.tv_rating);
            tbFav = itemView.findViewById(R.id.btn_favorite);
        }
    }
}
