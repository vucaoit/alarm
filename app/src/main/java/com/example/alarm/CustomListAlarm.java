package com.example.alarm;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class CustomListAlarm extends BaseAdapter {
    private List<AlarmTime> listData;
    private LayoutInflater layoutInflater;
    private Context context;
    private DB db;
    public CustomListAlarm(Context aContext,  List<AlarmTime> listData) {
        this.context = aContext;
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }
    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int i) {
        return listData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.itemt_alarm, null);
        }
        db=new DB(context);
        TextView tv_alarm = (TextView) convertView.findViewById(R.id.item_alarm);
        Button btn_add = (Button) convertView.findViewById(R.id.itemt_btn_delete);

        AlarmTime alarmTime = this.listData.get(position);
        tv_alarm.setText(convertToAlarm(alarmTime));
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.deleteAlarm(listData.get(position));
                listData.remove(position);
                notifyDataSetChanged();
            }
        });
        return convertView;
    }
    public String convertToAlarm(AlarmTime alarmTime){
        return intToTime(alarmTime.getHour())+":"+intToTime(alarmTime.getMinute());
    }
    public String intToTime(int time){
        if(time<10)return "0"+time;
        else return time+"";
    }
}
