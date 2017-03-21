package com.example.danil.newmanager.model;

import java.io.Serializable;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by danil on 19.02.2017.
 */

public class Task implements Serializable {

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
        //временно
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
                imgID = 10;
            } else {
                imgID = taskClass;
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

    public int getTaskClass() {
        return taskClass;
    }

    public void setTaskClass(byte taskClass) {
        this.taskClass = taskClass;
    }

    public boolean isImportant() {
        return important;
    }

    public void setImportant(boolean important) {
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
}
