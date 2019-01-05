package com.mojopahit.cataloguiux.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mojopahit.cataloguiux.Adapter.AdapterSearch;
import com.mojopahit.cataloguiux.BuildConfig;
import com.mojopahit.cataloguiux.Model.MainModel;
import com.mojopahit.cataloguiux.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FragmentSearch extends Fragment implements View.OnClickListener {
    private RecyclerView recyclerView;
    private ArrayList<MainModel> listData;
    private String judul;
    private String api = BuildConfig.MyApi;
    private Button btnCari;
    private EditText etCari;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.search_fragment, container, false);

        btnCari = v.findViewById(R.id.btn_cari);
        etCari = v.findViewById(R.id.et_cari);

        listData = new ArrayList<>();
        recyclerView = v.findViewById(R.id.recycler_cari);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        btnCari.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_cari){
            judul = etCari.getText().toString();
            if (TextUtils.isEmpty(judul)){
                listData.clear();
                getAllMovie();
            } else {
                listData.clear();
                getSearchMovie(judul);
            }
        }
    }

    private void getSearchMovie(String judul){
        String url = "https://api.themoviedb.org/3/search/movie?api_key="+api+"&language=en-US&query="+judul;
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Tunggu sebentar...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray array = jsonObject.getJSONArray("results");
                    for (int i = 0; i < array.length(); i++){
                        JSONObject weather = array.getJSONObject(i);
                        MainModel data = new MainModel(weather);
                        listData.add(data);
                    }
                    recyclerView.setAdapter(new AdapterSearch(listData));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Error :" + error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    private void getAllMovie(){
        String url = "https://api.themoviedb.org/3/discover/movie?api_key="+api+"&sort_by=popularity.asc";
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Tunggu sebentar...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray array = jsonObject.getJSONArray("results");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject weather = array.getJSONObject(i);
                        MainModel data = new MainModel(weather);
                        listData.add(data);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                recyclerView.setAdapter(new AdapterSearch(listData));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Error :" + error.toString(), Toast.LENGTH_SHORT).show();
                getAllMovie();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }
}
