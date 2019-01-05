package com.mojopahit.cataloguiux.Adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class AdapterSearch extends RecyclerView.Adapter<AdapterSearch.VHolder> {
    private ArrayList<MainModel> mainModels;

    public AdapterSearch(ArrayList<MainModel> mainModels) {
        this.mainModels = mainModels;
    }

    @NonNull
    @Override
    public VHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.desain_layout, viewGroup,false);
        return new VHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final VHolder holder, int i) {
        final int pos = i;
        holder.tvTitle.setText(mainModels.get(i).getJudul());
        holder.tvRating.setText(mainModels.get(i).getRating());
        String pedot = mainModels.get(i).getDeskripsi();
        int len = pedot.length();
        String cut;
        if (len == 0){
            cut = pedot;
        } else if (len > 0 && len <= 10){
            cut = pedot.substring(0, len);
        } else if (len > 10 && len <= 20) {
            cut = pedot.substring(0, 15);
        } else {
            cut = pedot.substring(0, 21);
        }
        String gabungDes = cut+" ...";

        holder.tvOverview.setText(gabungDes);
        String tgl = mainModels.get(i).getRilis();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date d = df.parse(tgl);

            SimpleDateFormat toFormat =  new SimpleDateFormat("EEEE, dd MMM yyyy");
            String fixDate = toFormat.format(d);
            holder.tvRelease.setText(fixDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Glide.with(holder.itemView.getContext()).load("http://image.tmdb.org/t/p/w185"+mainModels.get(i).getFoto())
                .into(holder.ivPoster);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainModel dm = mainModels.get(pos);
                Intent i = new Intent(holder.itemView.getContext(), MainDetail.class);
                i.putExtra("movie", dm);
                i.putExtra("title", mainModels.get(pos).getJudul());
                holder.itemView.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mainModels.size();
    }

    public static class VHolder extends RecyclerView.ViewHolder {
        private ImageView ivPoster;
        private TextView tvTitle, tvOverview, tvRelease, tvRating;

        public VHolder(@NonNull View itemView) {
            super(itemView);

            ivPoster = itemView.findViewById(R.id.imageview_id);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvOverview = itemView.findViewById(R.id.tv_overview);
            tvRelease = itemView.findViewById(R.id.tv_release);
            tvRating = itemView.findViewById(R.id.tv_rating);
        }
    }
}
