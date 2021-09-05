package com.example.alarm;

public class AlarmTime {
    private int id,hour,minute;
    public AlarmTime(){
        this.hour=0;
        this.minute=0;
    }
    public AlarmTime(int hour,int minute){
        this.hour=hour;
        this.minute=minute;
    }
    public AlarmTime(int id, int hour,int minute){
        this.id=id;
        this.hour=hour;
        this.minute=minute;
    }
    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "AlarmTime{" +
                "id=" + id +
                ", hour=" + hour +
                ", minute=" + minute +
                '}';
    }
}
