package com.example.danil.newmanager.model;

import android.content.Context;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by danil on 11.04.2017.
 */

public class Filter {

    private List<Task> allTasks;
    private static Filter currentInstans;

    private Filter(Context context){
        try {
            allTasks = DBActions.getInstans(context).getListTasks();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static Filter getInstans(Context context){
        if(currentInstans == null)
            currentInstans = new Filter(context);
        return currentInstans;
    }

    public List<Task> getTasksByDay(Date day){
        GregorianCalendar dayByCalendar = new GregorianCalendar();
        dayByCalendar.setTime(day);
        List<Task> tasksByNeededDay = new LinkedList<>();
        for(Task task: allTasks){
            if(task.isRepeated()){
                repeadedDayAdding(task, dayByCalendar, tasksByNeededDay);
            } else {
                unrepeadedDayAdding(task, dayByCalendar, tasksByNeededDay);

            }

        }
        return null;
    }

    private boolean isDaysEquals(GregorianCalendar firstDay, GregorianCalendar secondDay){
        int year = GregorianCalendar.YEAR;
        int month = GregorianCalendar.MONTH;
        int day = GregorianCalendar.DAY_OF_MONTH;
        boolean result = true;
        if(firstDay.get(year) != secondDay.get(year))
            result = false;
        if(firstDay.get(month) != secondDay.get(month))
            result = false;
        if(firstDay.get(day) != secondDay.get(day))
            result = false;
        return result;
    }

    private void repeadedDayAdding(Task task, GregorianCalendar dayByCalendar, List<Task> tasksByNeededDay){
        if(task.getRepeatedClass() == task.WEEK_REPEATED_CLASS)
            weekRepeatedAdding(task, dayByCalendar, tasksByNeededDay);
        if(task.getRepeatedClass() == task.MONTH_REPEATED_CLASS)
            monthRepeatedAdding(task, dayByCalendar, tasksByNeededDay);
        if(task.getRepeatedClass() == task.YEAR_REPEATED_CLASS)
            yearRepeatedAdding(task, dayByCalendar, tasksByNeededDay);

    }

    private void unrepeadedDayAdding(Task task, GregorianCalendar dayByCalendar, List<Task> tasksByNeededDay){
        if(isDaysEquals(task.getTime(), dayByCalendar))
            tasksByNeededDay.add(task);
    }

    private void weekRepeatedAdding(Task task, GregorianCalendar dayByCalendar, List<Task> tasksByNeededDay){

    }

    private void monthRepeatedAdding(Task task, GregorianCalendar dayByCalendar, List<Task> tasksByNeededDay){

    }

    private void yearRepeatedAdding(Task task, GregorianCalendar dayByCalendar, List<Task> tasksByNeededDay){

    }
}
