package com.mojopahit.cataloguiux.Fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mojopahit.cataloguiux.Adapter.AdapterFavorite;
import com.mojopahit.cataloguiux.Database.FavHelper;
import com.mojopahit.cataloguiux.Model.MainModel;
import com.mojopahit.cataloguiux.R;

import java.util.ArrayList;


public class FragmentFav extends Fragment {
    private View view;
    RecyclerView recyclerView;

    private ArrayList<MainModel> list;
    private FavHelper favHelper;
    private AdapterFavorite adapterFavorite;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.favorite_fragment, container, false);

        recyclerView = view.findViewById(R.id.recycler_fav);
        return view;
    }

    private class LoadFavAsync extends AsyncTask<Void, Void, ArrayList<MainModel>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (list.size() > 0){
                list.clear();
            }
        }

        @Override
        protected ArrayList<MainModel> doInBackground(Void... voids) {
            return favHelper.query();
        }

        @Override
        protected void onPostExecute(ArrayList<MainModel> mainModels) {
            super.onPostExecute(mainModels);

            list.addAll(mainModels);
            adapterFavorite.notifyDataSetChanged();
            adapterFavorite.setListFav(list);

            if (list.size() == 0){
                Toast.makeText(getActivity(), "Nothing to display", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Length : " + list.size(), Toast.LENGTH_SHORT);
            }
        }
    }

    @Override
    public void onResume() {
        favHelper = new FavHelper(getActivity());
        favHelper.open();

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        list = new ArrayList<>();

        adapterFavorite = new AdapterFavorite(getActivity());
        recyclerView.setAdapter(adapterFavorite);

        new LoadFavAsync().execute();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (favHelper != null){
            favHelper.close();
        }
    }


}
