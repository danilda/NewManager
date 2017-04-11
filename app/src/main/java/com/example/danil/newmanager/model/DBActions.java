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
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by danil on 19.02.2017.
 */

public class DBActions {

    public static String LOG_TAG = "BD actions";
    private ArrayList<Task> allTasks;
    private static DBActions currentInst;

    DBHelper dbHelper;
    SQLiteDatabase db;

    private DBActions(Context context){
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
        try {
            allTasks = getAllTasks();
        } catch (ParseException e) {
            Log.e(LOG_TAG, "ParseException", e);
        }
    }

    public static DBActions getInstans(Context context){
        if(currentInst == null)
            currentInst = new DBActions(context);
        return currentInst;
    }

    //TODO Написать Анумерацию с полями DB (подправить класс и статус(активынй/неактивный))
    public long insert(Task task){
        ContentValues cv = new ContentValues();
        cv.put("active", task.isActive()?1:0);
        cv.put("title", task.getTitle());
        cv.put("description", task.getDescription());
        cv.put("class", task.getTaskClass());
        cv.put("important", task.isImportant()?1:0);
        cv.put("notification", task.isNotification()?1:0);
        cv.put("repeated", task.isRepeated()?1:0);
        cv.put("repeated_class", task.getRepeatedClass());
        cv.put("repeated_time", task.getRepeatedTime());
        cv.put("time", (new SimpleDateFormat()).format(task.getTime().getTime()));
        cv.put("img_id", task.getImgID());

        long rowID = db.insert("TasksTable", null, cv);
        task.setId(rowID);
        Log.d(LOG_TAG, "row inserted, ID = " + rowID);

        return rowID;
    }

    public void deleteAll(){
        int clearCount = db.delete("TasksTable", null, null);
        Log.d(LOG_TAG, "deleted rows count = " + clearCount);
    }

    private ArrayList<Task> getAllTasks() throws ParseException {
        Log.d(LOG_TAG, "--- Rows in mytable: ---");
        Cursor c = db.query("TasksTable", null, null, null, null, null, null);
        ArrayList<Task> list = new ArrayList<>();

        while (c.moveToNext()){
            Log.d(LOG_TAG, "--- проход по курсору ---");
            list.add(makeTask(c));
        }
        c.close();
        return list;
    }

    public ArrayList<Task> getListTasks() throws ParseException {
        if(allTasks == null)
            throw new RuntimeException("getListTasks возвращает null!");
        checkForActive(false); // should be changed after creation settings
        return allTasks;
    }

    public Task makeTask(Cursor c) throws ParseException {
        int idColIndex = c.getColumnIndex("id");
        int activeColIndex = c.getColumnIndex("active");
        int titleColIndex = c.getColumnIndex("title");
        int descriptionColIndex = c.getColumnIndex("description");
        int taskClassColIndex = c.getColumnIndex("class");
        int importantColIndex = c.getColumnIndex("important");
        int notificationColIndex = c.getColumnIndex("notification");
        int repeatedColIndex = c.getColumnIndex("repeated");
        int repeatedClassColIndex = c.getColumnIndex("repeated_class");
        int repeatedTimeColIndex = c.getColumnIndex("repeated_time");
        int timeColIndex = c.getColumnIndex("time");
        int imgIDColIndex = c.getColumnIndex("img_id");

        int id = c.getInt(idColIndex);
        boolean active = c.getInt(activeColIndex) == 1;
        String title = c.getString(titleColIndex);
        String description = c.getString(descriptionColIndex);
        byte taskClass = (byte) c.getInt(taskClassColIndex);
        boolean important = c.getInt(importantColIndex) == 1;
        boolean notification = c.getInt(notificationColIndex) == 1;
        boolean repeated = c.getInt(repeatedColIndex) == 1;
        byte repeatedClass = (byte) c.getInt(repeatedClassColIndex);
        String repeatedTime = c.getString(repeatedTimeColIndex);
        String stringTime = c.getString(timeColIndex);
        GregorianCalendar time = new GregorianCalendar();
        if(!repeated) {
            time.setTime((new SimpleDateFormat()).parse(stringTime));
        } else {

        }


        byte imgID = (byte)c.getInt(imgIDColIndex);
            Task task = new Task(id, active,title, description, taskClass, important,
                    notification, repeated, repeatedClass, repeatedTime, time, imgID);
        Log.d(LOG_TAG,
                "ID = " + id +
                "active = " + active +
                ", title = " + title +
                ", description = " + description+
                ", class = " + taskClass+
                ", important = " + important +
                ", notification = " + notification +
                ", repeated = " + repeated +
                ", repeatedClass = " + repeatedClass +
                ", repeatedTime = " + repeatedTime +
                ", time = " + stringTime +
                ", img_id = " + imgID);
        return task;
    }


    class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            // конструктор суперкласса
            super(context, "myDB", null, 2);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.d(LOG_TAG, "--- onCreate database ---");
            // создаем таблицу с полями
            db.execSQL("create table TasksTable ("
                    + "id integer primary key autoincrement,"
                    + "active NUMERIC,"
                    + "title text,"
                    + "description text,"
                    + "class NUMERIC,"
                    + "important NUMERIC,"
                    + "notification NUMERIC,"
                    + "repeated NUMERIC,"
                    + "repeated_class NUMERIC,"
                    + "repeated_time TEXT,"
                    + "time TEXT,"
                    + "img_id NUMERIC );");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

    public Task getTaskBeID(long id){
        for(Task a: allTasks){
            if(a.getId() == id)
                return a;
        }
        return null;
    }

    public void updateTask(Task task){
        ContentValues cv = new ContentValues();
        cv.put("active", task.isActive()?1:0);
        cv.put("title", task.getTitle());
        cv.put("description", task.getDescription());
        cv.put("class", task.getTaskClass());
        cv.put("important", task.isImportant()?1:0);
        cv.put("notification", task.isNotification()?1:0);
        cv.put("repeated", task.isRepeated()?1:0);
        cv.put("repeated_class", task.getRepeatedClass());
        cv.put("repeated_time", task.getRepeatedTime());
        cv.put("time", (new SimpleDateFormat()).format(task.getTime().getTime()));
        cv.put("img_id", task.getImgID());
        int updCount = db.update("TasksTable", cv, "id = ?",
                new String[] {Long.toString(task.getId())});

        Log.d(LOG_TAG, "updateTask rows were updated : "+ updCount);
    }

    public void deleteTask(Task task){
        int delCount = db.delete("TasksTable", "id = " + task.getId(), null);
        Log.d(LOG_TAG, "deleted rows count = " + delCount);
    }

    /**
     * @param doDelete is temporarily "false", waiting for creation settings
     */
    public void checkForActive(boolean doDelete){
        for(Task task : allTasks){
            if(task.isRepeated()){
                if(task.getTime().getTime().compareTo(new Date()) < 0 ) {
                    if(doDelete){
                        deleteTask(task);
                    } else {
                        task.setActive(false);
                        updateTask(task);
                    }
                }
            }
        }
    }
}
