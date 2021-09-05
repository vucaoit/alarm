package com.example.alarm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class AddNewAlarm extends AppCompatActivity {
private EditText edt_hour,edt_minute;
private Button btn_setAlarm,btn_back;
private AlarmTime alarmTime = new AlarmTime();
private DB db = new DB(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_alarm);
        setIdElement();
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddNewAlarm.this,MainActivity.class);
                startActivity(intent);
            }
        });
        btn_setAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isEmpty()){
                    int gio=Integer.parseInt(edt_hour.getText().toString());
                    int phut=Integer.parseInt(edt_minute.getText().toString());
                    if(isTrueHour(gio)&&isTrueMinute(phut)){
                        alarmTime.setHour(gio);
                        alarmTime.setMinute(phut);
                        if(!db.isExist(alarmTime)){
                            long result = db.addAlarm(alarmTime);
                            if(result>=0){
                                startAlert(alarmTime.getHour(),alarmTime.getMinute());
                                alert("Add Alarm Success");
                            }
                        }
                        else alert("Thời gian này đã có trong danh sách");
                    }
                    else {
                        if(!isTrueHour(gio)){
                            alert("Giờ không hợp lệ, 0<=Giờ<24");
                        }
                        if(!isTrueMinute(phut)){
                            alert("Phút không hợp lệ, 0<=Phút<60");
                        }
                    }
                }
                else alert("Vui lòng nhập vào thời gian báo thức");
            }
        });
    }
    public boolean isEmpty(){
        if(edt_hour.getText().toString().isEmpty()||edt_minute.getText().toString().isEmpty()||edt_hour.getText().toString().equals("")||edt_minute.getText().toString().equals(""))return true;
        return false;
    }
    public boolean isTrueHour(int hour){
        if(hour>=0&&hour<24){
            return true;
        }
        return false;
    }
    public boolean isTrueMinute(int minute){
        if(minute>=0&&minute<60){
            return true;
        }
        return false;
    }
    public void setIdElement(){
        btn_back=findViewById(R.id.btn_back);
        edt_hour=findViewById(R.id.inp_gio);
        edt_minute=findViewById(R.id.inp_phut);
        btn_setAlarm=findViewById(R.id.btn_datBaoThuc);
    }
    public void startAlert(int hour,int minute){
        Intent intent = new Intent(this, AlarmReceiver.class);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        intent.putExtra("extra","play");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(), Integer.parseInt(hour+""+minute), intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        System.out.println(calendar.getTimeInMillis()-System.currentTimeMillis());
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY, pendingIntent);
    }
    public void alert(String alert){
        Toast.makeText(this,alert,Toast.LENGTH_LONG).show();
    }
}