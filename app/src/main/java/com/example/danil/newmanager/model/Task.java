package com.example.danil.newmanager.model;

import android.util.Log;

import java.io.Serializable;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static com.example.danil.newmanager.model.DBActions.LOG_TAG;

/**
 * Created by danil on 19.02.2017.
 */

public class Task implements Serializable {

    public static final byte WEEK_REPEATED_CLASS = 0;
    public static final byte MONTH_REPEATED_CLASS = 1;
    public static final byte YEAR_REPEATED_CLASS = 2;
    public static final byte COMMON_CLASS = 0;
    public static final byte BIRTHDAY_CLASS = 1;
    public static final byte PURCHASES_CLASS = 2;
    public static final byte SPORT_CLASS = 3;
    public static final byte NOTIFICATION_CLASS = 4;
    private long id;
    private boolean active;
    private String title;
    private String description;

    /**
     * 0 - common
     * 1 - birthday
     * 2 - purchases (покупки)
     * 3 - sport
     * 4 - notificationTask (будильник)
     */
    private byte taskClass;
    private boolean important;
    private boolean notification;
    private boolean repeated;
    private byte repeatedClass;
    private String repeatedTime;
    private GregorianCalendar time;

    /**
     * 10 - important
     * 0 - common
     * 1 - birthday
     * 2 - purchases (покупки)
     * 3 - sport
     * 4 - notificationTask (будильник)
     */
    private byte imgID;


    public Task(String title, String description, byte taskClass) {
        this.title = title;
        this.description = description;
        this.taskClass = taskClass;
        this.time = new GregorianCalendar();
    }

    public Task(int id, boolean active, String title, String description, byte taskClass, boolean important,
                boolean notification, boolean repeated, byte repeatedClass, String repeatedTime, GregorianCalendar time, byte imgID) {
        this.id = id;
        this.active = active;
        this.title = title;
        this.description = description;
        this.taskClass = taskClass;
        this.important = important;
        this.notification = notification;
        this.repeated = repeated;
        this.repeatedClass = repeatedClass;
        this.repeatedTime = repeatedTime;
        this.time = time;
        if(imgID == -1){
            if(important){
                this.imgID = 10;
            } else {
                this.imgID = taskClass;
            }
        }else{
            this.imgID = imgID;
        }

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte getTaskClass() {
        return taskClass;
    }

    public void setTaskClass(byte taskClass) {
        this.taskClass = taskClass;
    }

    public boolean isImportant() {
        return important;
    }

    public void setImportant(boolean important) {
        if(important)
            this.imgID = 10;
        this.important = important;
    }

    public boolean isNotification() {
        return notification;
    }

    public void setNotification(boolean notification) {
        this.notification = notification;
    }

    public boolean isRepeated() {
        return repeated;
    }

    public void setRepeated(boolean repeated) {
        this.repeated = repeated;
    }

    public byte getRepeatedClass() {
        return repeatedClass;
    }

    public void setRepeatedClass(byte repeatedClass) {
        this.repeatedClass = repeatedClass;
    }

    public String getRepeatedTime() {
        return repeatedTime;
    }

    public void setRepeatedTime(String repeatedTime) {
        this.repeatedTime = repeatedTime;
    }

    public GregorianCalendar getTime() {
        return time;
    }

    public void setTime(GregorianCalendar time) {
        this.time = time;
    }

    public byte getImgID() {
        return imgID;
    }

    public void setImgID(byte imgID) {
        this.imgID = imgID;
    }

    public String toString(){
        return          "ID = " + id +
                        ", active = " + active +
                        ", title = " + title +
                        ", description = " + description+
                        ", class = " + taskClass+
                        ", important = " + important +
                        ", notification = " + notification +
                        ", repeated = " + repeated +
                        ", repeatedClass = " + repeatedClass +
                        ", repeatedTime = " + repeatedTime +
                        ", time = " + new SimpleDateFormat().format(getTime().getTime()) +
                        ", img_id = " + imgID;
    }
}
