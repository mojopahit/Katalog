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

    private ArrayList<MainModel> list = new ArrayList<>();;
    private FavHelper favHelper;
    private AdapterFavorite adapterFavorite;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.favorite_fragment, container, false);

        recyclerView = view.findViewById(R.id.recycler_fav);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapterFavorite = new AdapterFavorite(getActivity());

        favHelper = new FavHelper(getActivity());
        favHelper.open();

        new LoadFavAsync().execute();
        recyclerView.setAdapter(adapterFavorite);

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
            Log.i("FragmentFav", "FragmentFav : "+favHelper.query());
            return favHelper.query();
        }

        @Override
        protected void onPostExecute(ArrayList<MainModel> mainModels) {
            super.onPostExecute(mainModels);

            list.addAll(mainModels);
            adapterFavorite.setListFav(list);
            adapterFavorite.notifyDataSetChanged();

            if (list.size() == 0){
                Toast.makeText(getActivity(), "Nothing to display", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onResume() {
        list.clear();

        super.onResume();
        new LoadFavAsync().execute();
    }

    @Override
    public void onDestroy() {
        if (favHelper != null){
            favHelper.close();
        }
        super.onDestroy();
    }
}
