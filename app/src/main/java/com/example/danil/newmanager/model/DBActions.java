package com.example.danil.newmanager.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;

/**
 * Created by danil on 19.02.2017.
 */

public class DBActions {

    public static String LOG_TAG = "BD actions";

    DBHelper dbHelper;
    SQLiteDatabase db;

    public DBActions(Context context){
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    //TODO Написать Анумерацию с полями DB (подправить класс и статус(активынй/неактивный))
    public void insert(Task task){
        ContentValues cv = new ContentValues();
        cv.put("active", task.isActive());
        cv.put("title", task.getTitle());
        cv.put("description", task.getDescription());
        cv.put("class", task.getTaskClass());
        cv.put("important", task.isImportant()?1:0);
        cv.put("repeated", task.isRepeated()?1:0);
        cv.put("startTime", (new SimpleDateFormat()).format(task.getStartTime()));
        if(task.isRepeated()) {
            cv.put("EndTime", (new SimpleDateFormat()).format(task.getEndTime()));
            cv.put("period", task.getPeriod());
        }
        long rowID = db.insert("TasksTable", null, cv);
        Log.d(LOG_TAG, "row inserted, ID = " + rowID);

    }
    public void deleteAll(){
        int clearCount = db.delete("TasksTable", null, null);
        Log.d(LOG_TAG, "deleted rows count = " + clearCount);
    }

    public ArrayList<Task> getAllTasks(){
        Log.d(LOG_TAG, "--- Rows in mytable: ---");
        // делаем запрос всех данных из таблицы mytable, получаем Cursor
        Cursor c = db.query("TasksTable", null, null, null, null, null, null);
        ArrayList<Task> list;
        // ставим позицию курсора на первую строку выборки
        // если в выборке нет строк, вернется false
        if (c.moveToFirst()) {
            list = new ArrayList<>();
            // определяем номера столбцов по имени в выборке
            int idColIndex = c.getColumnIndex("id");
            int activeColIndex = c.getColumnIndex("id");
            int titleColIndex = c.getColumnIndex("title");
            int descriptionColIndex = c.getColumnIndex("description");
            int taskClassColIndex = c.getColumnIndex("class");
            int importantColIndex = c.getColumnIndex("important");
            int repeatedColIndex = c.getColumnIndex("repeated");
            int startTimelIndex = c.getColumnIndex("startTime");
            int endTimeColIndex = c.getColumnIndex("endTime");
            int periodColIndex = c.getColumnIndex("period");
            int imgIDColIndex = c.getColumnIndex("img_id");
            do {
                int id = c.getInt(idColIndex);
                boolean active = (c.getInt(activeColIndex) == 1);
                String title = c.getString(titleColIndex);
                String description = c.getString(descriptionColIndex);
                String taskClass = c.getString(taskClassColIndex);
                boolean important = (c.getInt(importantColIndex) == 1);
                boolean repeated = (c.getInt(repeatedColIndex) == 1);
                GregorianCalendar startTime = null;
                GregorianCalendar endTime = null;
                int period = 0;
                try {
                    startTime =  new GregorianCalendar();
                    startTime.setTime((new SimpleDateFormat()).parse(c.getString(startTimelIndex)));//могут быть ошибки парса
                    if(repeated){
                        endTime =  new GregorianCalendar();
                        endTime.setTime((new SimpleDateFormat()).parse(c.getString(endTimeColIndex)));
                        period = c.getInt(periodColIndex);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                int imgID = c.getInt(imgIDColIndex);
                Task task = new Task(id, title, description, taskClass, important, repeated, startTime, endTime, period, imgID);
                task.setActive(active);
                list.add(task);
                // получаем значения по номерам столбцов и пишем все в лог
                Log.d(LOG_TAG,
                        "ID = " + id +
                        "active = " + active +
                        ", title = " + title +
                        ", description = " + description+
                        ", class = " + taskClass+
                        ", important = " + important +
                        ", repeated = " + repeated +
                        ", startTime = " + startTime +
                        ", endTime = " + endTime +
                        ", period = " + period +
                        ", img_id = " + imgID);
                // переход на следующую строку
                // а если следующей нет (текущая - последняя), то false - выходим из цикла
            } while (c.moveToNext());
        } else
            Log.d(LOG_TAG, "0 rows");
        c.close();
        return null;
    }


    class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            // конструктор суперкласса
            super(context, "myDB", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.d(LOG_TAG, "--- onCreate database ---");
            // создаем таблицу с полями
            db.execSQL("create table TasksTable ("
                    + "id integer primary key autoincrement,"
                    + "active,"
                    + "title text,"
                    + "description text,"
                    + "class TEXT,"
                    + "important NUMERIC,"
                    + "repeated NUMERIC,"
                    + "startTime TEXT,"
                    + "endTime TEXT,"
                    + "period NUMERIC"
                    + "img_id NUMERIC"+ ");");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
