package com.mojopahit.cataloguiux;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mojopahit.cataloguiux.Database.FavHelper;
import com.mojopahit.cataloguiux.Model.MainModel;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static com.mojopahit.cataloguiux.ImageBannerWidget.EXTRA_ITEM;

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private ArrayList<MainModel> data = new ArrayList<>();
    private Context context;
    private int mAppWidgetId;
    private FavHelper favHelper;

    public StackRemoteViewsFactory(Context context, Intent intent) {
        this.context = context;
        this.mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {
        data.clear();
        favHelper = new FavHelper(context);
        favHelper.open();
        data = favHelper.query();
    }

    @Override
    public void onDataSetChanged() {
        data.clear();
        favHelper = new FavHelper(context);
        favHelper.open();
        data = favHelper.query();
    }

    @Override
    public void onDestroy() {
        favHelper.close();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_item);
        try {
            Bitmap bitmap = Glide.with(context)
                    .asBitmap()
                    .load("http://image.tmdb.org/t/p/w185" + data.get(position).getFoto())
                    .apply(new RequestOptions().fitCenter())
                    .submit()
                    .get();
            rv.setImageViewBitmap(R.id.imageView, bitmap);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Bundle extras = new Bundle();
        extras.putInt(EXTRA_ITEM, position);
        Intent i = new Intent();
        i.putExtras(extras);

        rv.setOnClickFillInIntent(R.id.imageView, i);
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
