package com.example.alarm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<AlarmTime> alarmTimeList;
    private ListView listView;
    private Button btn;
    private DB db = new DB(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView=findViewById(R.id.lst_view);
        btn=findViewById(R.id.btn_gotoAddAlarm);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AddNewAlarm.class);
                startActivity(intent);
            }
        });
        if(!db.getAllAlarm().isEmpty()) {
            alarmTimeList=db.getAllAlarm();
            CustomListAlarm customListAlarm = new CustomListAlarm(this,alarmTimeList);
            listView.setAdapter(customListAlarm);
        }
    }
    public void cancelAlert(int id){
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent myIntent = new Intent(getApplicationContext(), AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                getApplicationContext(), id, myIntent, 0);

        alarmManager.cancel(pendingIntent);
    }
}