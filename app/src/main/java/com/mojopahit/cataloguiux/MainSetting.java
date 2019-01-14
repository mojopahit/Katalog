package com.mojopahit.cataloguiux;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import com.mojopahit.cataloguiux.Notification.NDaily;
import com.mojopahit.cataloguiux.Notification.NToday;
import com.mojopahit.cataloguiux.Preference.PReminder;

import java.util.Objects;

import static com.mojopahit.cataloguiux.Notification.NDaily.TYPE_REPEATING;

public class MainSetting extends AppCompatActivity implements View.OnClickListener {
    private Button btnBatal, btnSimpan;
    private Switch swDaily, swToday;
    private boolean isDaily, isToday;
    private PReminder pr;
    private NDaily daily;
    private NToday today;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_setting);

        daily = new NDaily();
        today = new NToday();

        btnBatal = findViewById(R.id.btn_batal);
        btnSimpan = findViewById(R.id.btn_simpan);
        btnSimpan.setOnClickListener(this);
        btnBatal.setOnClickListener(this);

        swDaily = findViewById(R.id.sw_daily);
        swToday = findViewById(R.id.sw_today);

        pr = new PReminder(this);
        cek();

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.setting));
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_batal) {
            finish();
        } else if (v.getId() == R.id.btn_simpan) {
            isDaily = swDaily.isChecked();
            if (isDaily) {
                pr.setDaily(isDaily);
                daily.setRepeating(this, TYPE_REPEATING, "07:00", getResources().getString(R.string.daily_reminder));
            } else {
                pr.setDaily(isDaily);
                daily.cancelRepeating(this);
            }

            isToday = swToday.isChecked();
            if (isToday) {
                pr.setToday(isToday);
                today.setRepeating(this, TYPE_REPEATING, "08:00", "today");
            } else {
                pr.setToday(isToday);
                today.cancelRepeating(this);
            }
            Toast.makeText(this, getResources().getString(R.string.save_setting), Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void cek() {
        if (pr.isDaily()) {
            swDaily.setChecked(true);
        } else {
            swDaily.setChecked(false);
        }

        if (pr.isToday()) {
            swToday.setChecked(true);
        } else {
            swToday.setChecked(false);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
