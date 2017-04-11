package com.example.danil.newmanager.control;

import java.util.GregorianCalendar;

/**
 * Created by danil on 10.04.2017.
 */

public interface ActionsTaskForDatePicker {
    public void setDate(GregorianCalendar date, int year, int month, int day);
    public GregorianCalendar getStartTime();
    public byte getClassTask();
    public boolean isRepeated();
    public void showDate(GregorianCalendar time, int id);
    public byte switcher(int classTask, boolean repeated);
    public void setTime(GregorianCalendar time, int hour, int min);
}
