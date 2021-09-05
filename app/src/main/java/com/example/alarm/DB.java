package com.example.alarm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DB extends SQLiteOpenHelper {
    private static final String TAG = "SQLite";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "ALARMDB";

    // Table name: Note.
    private static final String TABLE_ALARM = "alarm";

    private static final String COLUMN_TIME_ID ="id";
    private static final String COLUMN_TIME_HOUR ="hour";
    private static final String COLUMN_TIME_MINUTE = "minute";
    public DB(Context context)  {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.i(TAG, "DB.onCreate ... ");
        // Script.
        String script = "CREATE TABLE " + TABLE_ALARM + "("
                + COLUMN_TIME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," + COLUMN_TIME_HOUR + " INTEGER,"
                + COLUMN_TIME_MINUTE + " INTEGER" + ")";
        // Execute Script.
        sqLiteDatabase.execSQL(script);
    }
    public long addAlarm(AlarmTime alarmTime) {
        Log.i(TAG, "DB.addAlarm ... " + alarmTime.toString());

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TIME_HOUR, alarmTime.getHour());
        values.put(COLUMN_TIME_MINUTE, alarmTime.getMinute());

        // Inserting Row
        long result=db.insert(TABLE_ALARM, null, values);
        // Closing database connection
        db.close();
        return result;
    }
    public AlarmTime getAlarm(int id) {
        Log.i(TAG, "DB.getAlarm ... " + id);

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_ALARM, new String[] { COLUMN_TIME_ID,
                        COLUMN_TIME_HOUR, COLUMN_TIME_MINUTE }, COLUMN_TIME_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        AlarmTime alarmTime = new AlarmTime(Integer.parseInt(cursor.getString(0)),
                Integer.parseInt(cursor.getString(1)), Integer.parseInt(cursor.getString(2)));
        // return note
        return alarmTime;
    }
    public boolean isExist(AlarmTime alarmTime) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_ALARM+" WHERE "+COLUMN_TIME_HOUR+" =? AND "+COLUMN_TIME_MINUTE+"=?",
                new String[]{String.valueOf(alarmTime.getHour()),String.valueOf(alarmTime.getMinute())});
        boolean exist = (cursor.getCount() != 0);
        cursor.close();
        return exist;
    }
    public List<AlarmTime> getAllAlarm() {
        Log.i(TAG, "DB.getAllAlarm ... " );

        List<AlarmTime> alarmTimeList = new ArrayList<AlarmTime>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_ALARM;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                AlarmTime alarmTime = new AlarmTime();
                alarmTime.setId(Integer.parseInt(cursor.getString(0)));
                alarmTime.setHour(Integer.parseInt(cursor.getString(1)));
                alarmTime.setMinute(Integer.parseInt(cursor.getString(2)));
                // Adding note to list
                alarmTimeList.add(alarmTime);
            } while (cursor.moveToNext());
        }
        // return note list
        return alarmTimeList;
    }
    public void deleteAlarm(AlarmTime alarmTime) {
        Log.i(TAG, "DB.DeleteAlarm ... " + alarmTime.toString() );

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ALARM, COLUMN_TIME_ID + " = ?",
                new String[] { String.valueOf(alarmTime.getId()) });
        db.close();
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
