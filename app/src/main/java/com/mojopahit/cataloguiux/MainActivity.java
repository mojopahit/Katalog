package com.mojopahit.cataloguiux;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mojopahit.cataloguiux.Adapter.AdapterFavorite;
import com.mojopahit.cataloguiux.Adapter.AdapterNow;
import com.mojopahit.cataloguiux.Adapter.AdapterUpcoming;
import com.mojopahit.cataloguiux.Fragment.FragmentFav;
import com.mojopahit.cataloguiux.Fragment.FragmentNow;
import com.mojopahit.cataloguiux.Model.MainModel;
import com.mojopahit.cataloguiux.Pager.FragmentPager;
import com.mojopahit.cataloguiux.Fragment.FragmentSearch;
import com.mojopahit.cataloguiux.Fragment.FragmentUpcoming;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Button btnCari;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnCari = findViewById(R.id.btn_cari);

        tabLayout = findViewById(R.id.tablayout_id);
        viewPager = findViewById(R.id.viewpager_id);

        // adding fragment
        FragmentPager fp = new FragmentPager(getSupportFragmentManager());
        fp.addFragment(new FragmentNow(), getResources().getString(R.string.now_playing));
        fp.addFragment(new FragmentUpcoming(), getResources().getString(R.string.upcoming));
        fp.addFragment(new FragmentFav(), getResources().getString(R.string.favorite));
        fp.addFragment(new FragmentSearch(), getResources().getString(R.string.search));
        // adapter setup
        viewPager.setAdapter(fp);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.change_id){
            Intent i = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    public static void loadData(final String url, final ArrayList<MainModel> listData, final RecyclerView recyclerView, final Activity activity, final String fragment) {
        final ProgressDialog progressDialog = new ProgressDialog(activity);
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
                    if (fragment == "fn") {
                        recyclerView.setAdapter(new AdapterNow(listData));
                    } else {
                        recyclerView.setAdapter(new AdapterUpcoming(listData));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(activity, "Error" + error.toString(), Toast.LENGTH_SHORT).show();
                loadData(url, listData, recyclerView, activity, fragment);
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(stringRequest);
    }
}
