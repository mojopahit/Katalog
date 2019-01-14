package com.mojopahit.cataloguiux.Notification;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mojopahit.cataloguiux.BuildConfig;
import com.mojopahit.cataloguiux.MainDetail;
import com.mojopahit.cataloguiux.Model.MainModel;
import com.mojopahit.cataloguiux.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static com.mojopahit.cataloguiux.Notification.NDaily.CHANNEL_ID;
import static com.mojopahit.cataloguiux.Notification.NDaily.CHANNEL_NAME;
import static com.mojopahit.cataloguiux.Notification.NDaily.EXTRA_MESSAGE;
import static com.mojopahit.cataloguiux.Notification.NDaily.EXTRA_TYPE;

public class NToday extends BroadcastReceiver {
    private final int NOTIF_ID_REPEATING = 102;
    private String api = BuildConfig.MyApi;
    String url = "https://api.themoviedb.org/3/movie/now_playing?api_key=" + api + "&language=en-US";

    @Override
    public void onReceive(final Context context, Intent intent) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray array = jsonObject.getJSONArray("results");
                    int notifId = NOTIF_ID_REPEATING;
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject weather = array.getJSONObject(i);
                        MainModel data = new MainModel(weather);

                        String tgl_pelem = data.getRilis();
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                        String tgl_device = df.format(new Date());
                        Log.e("Tes", "Tes : " + tgl_pelem.equalsIgnoreCase(tgl_device) + "| pelem : " + tgl_pelem + " | device : " + tgl_device);
                        if (tgl_pelem.equalsIgnoreCase(tgl_device)) {
                            String title = data.getJudul();
                            title = data.getJudul();
                            showNotif(context, title, data, notifId);
                            notifId++;
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Error" + error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    public void setRepeating(Context context, String type, String time, String message) {
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, NToday.class);
        intent.putExtra(EXTRA_MESSAGE, message);
        intent.putExtra(EXTRA_TYPE, type);

        String timeArray[] = time.split(":");

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]));
        calendar.set(Calendar.SECOND, 0);

        if (calendar.before(Calendar.getInstance())) calendar.add(Calendar.DATE, 1);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, NOTIF_ID_REPEATING, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        am.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    public void cancelRepeating(Context context) {
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, NDaily.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, NOTIF_ID_REPEATING, intent, 0);

        am.cancel(pendingIntent);
    }

    private void showNotif(Context context, String title, MainModel mm, int notifId) {
        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        String message = mm.getJudul() + " " + context.getResources().getString(R.string.message_notif);
        Intent intent = new Intent(context, MainDetail.class);
        intent.putExtra("movie", mm);
        intent.putExtra("title", mm.getJudul());
        PendingIntent pendingIntent = PendingIntent.getActivity(context, notifId, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_favorite)
                .setContentTitle(title)
                .setContentText(message)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            builder.setChannelId(CHANNEL_ID);
            notificationManagerCompat.createNotificationChannel(channel);
            notificationManagerCompat.notify(notifId, builder.build());
        } else {
            notificationManagerCompat.notify(notifId, builder.build());
        }
    }
}
