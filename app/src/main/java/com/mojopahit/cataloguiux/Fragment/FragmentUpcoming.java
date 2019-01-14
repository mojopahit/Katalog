package com.mojopahit.cataloguiux.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.mojopahit.cataloguiux.Adapter.AdapterUpcoming;
import com.mojopahit.cataloguiux.BuildConfig;
import com.mojopahit.cataloguiux.Model.MainModel;
import com.mojopahit.cataloguiux.R;

import java.util.ArrayList;

import static com.mojopahit.cataloguiux.MainActivity.loadData;

public class FragmentUpcoming extends Fragment {
    private View view;
    private ArrayList<MainModel> listData;
    private String api = BuildConfig.MyApi;
    RecyclerView recyclerView;
    String url = "https://api.themoviedb.org/3/movie/upcoming?api_key="+api+"&language=en-US";
    String fragment = "fu";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.upcoming_fragment, container, false);

        listData = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recycler_up);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        loadData(url, listData, recyclerView, getActivity(), fragment);

        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelableArrayList("upcoming", new ArrayList<>(listData));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            ArrayList<MainModel> upcoming = savedInstanceState.getParcelableArrayList("upcoming");
            recyclerView.setAdapter(new AdapterUpcoming(upcoming));
        }
    }
}
