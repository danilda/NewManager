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

    private String[] ARRAY_DAYS_OF_WEEK = {TaskHelper.MONDAY, TaskHelper.TUESDAY, TaskHelper.WEDNESDAY, TaskHelper.THURSDAY, TaskHelper.FRIDAY, TaskHelper.SATURDAY, TaskHelper.SUNDAY };

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
                repeatedDayAdding(task, dayByCalendar, tasksByNeededDay);
            } else {
                unrepeatedDayAdding(task, dayByCalendar, tasksByNeededDay);
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

    private void repeatedDayAdding(Task task, GregorianCalendar dayByCalendar, List<Task> tasksByNeededDay){
        if(task.getRepeatedClass() == Task.WEEK_REPEATED_CLASS)
            weekRepeatedAdding(task, dayByCalendar, tasksByNeededDay);
        if(task.getRepeatedClass() == Task.MONTH_REPEATED_CLASS)
            monthRepeatedAdding(task, dayByCalendar, tasksByNeededDay);
        if(task.getRepeatedClass() == Task.YEAR_REPEATED_CLASS)
            yearRepeatedAdding(task, dayByCalendar, tasksByNeededDay);

    }

    private void unrepeatedDayAdding(Task task, GregorianCalendar dayByCalendar, List<Task> tasksByNeededDay){
        if(isDaysEquals(task.getTime(), dayByCalendar))
            tasksByNeededDay.add(task);
    }

    private void weekRepeatedAdding(Task task, GregorianCalendar dayByCalendar, List<Task> tasksByNeededDay){
        String currentDayOfWeek = ARRAY_DAYS_OF_WEEK[dayByCalendar.get(GregorianCalendar.DAY_OF_WEEK) - 1];
        List<Integer> tasksIdsByNeededDay = TaskHelper.getRepeatedMap().get(currentDayOfWeek);
        if(tasksIdsByNeededDay.contains(task))
            tasksByNeededDay.add(task);
    }

    private void monthRepeatedAdding(Task task, GregorianCalendar dayByCalendar, List<Task> tasksByNeededDay){

    }

    private void yearRepeatedAdding(Task task, GregorianCalendar dayByCalendar, List<Task> tasksByNeededDay){

    }
}
