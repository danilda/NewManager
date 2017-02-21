package com.example.danil.newmanager.model;

import java.io.Serializable;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by danil on 19.02.2017.
 */

public class Task implements Serializable {
    private int id;
    private String title;
    private String description;
    private boolean birthday;
    private boolean important;
    private boolean repeated;
    private GregorianCalendar startTime;
    private GregorianCalendar endTime;
    private int period;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public boolean isBirthday() {
        return birthday;
    }

    public void setBirthday(boolean birthday) {
        this.birthday = birthday;
    }

    public boolean isImportant() {
        return important;
    }

    public void setImportant(boolean important) {
        this.important = important;
    }

    public boolean isRepeated() {
        return repeated;
    }

    public void setRepeated(boolean repeated) {
        this.repeated = repeated;
    }

    public GregorianCalendar getStartTime() {
        return startTime;
    }

    public void setStartTime(GregorianCalendar startTime) {
        this.startTime = startTime;
    }

    public GregorianCalendar getEndTime() {
        return endTime;
    }

    public void setEndTime(GregorianCalendar endTime) {
        this.endTime = endTime;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public Task(int id,String title, String description, boolean birthday, boolean important, boolean repeated, GregorianCalendar startTime, GregorianCalendar endTime, int period) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.birthday = birthday;
        this.important = important;
        this.repeated = repeated;
        this.startTime = startTime;
        this.endTime = endTime;
        this.period = period;
    }

    public Task(String title, String discriptions, GregorianCalendar startTime) {
        this.title = title;
        this.description = discriptions;
        this.startTime = startTime;
    }

    public Task(String title, String discriptions, boolean repeated, GregorianCalendar startTime, GregorianCalendar endTime, int period) {
        this.title = title;
        this.description = discriptions;
        this.repeated = repeated;
        this.startTime = startTime;
        this.endTime = endTime;
        this.period = period;
    }


}
