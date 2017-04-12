package com.example.danil.newmanager.model.Filters;

import android.content.Context;

import com.example.danil.newmanager.model.DBActions;
import com.example.danil.newmanager.model.Task;
import com.example.danil.newmanager.model.TaskHelper;

import java.text.ParseException;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by danil on 11.04.2017.
 * dad
 */

public class FilterByDay {

    private List<Task> allTasks;
    private static FilterByDay currentInstance;

    private String[] ARRAY_DAYS_OF_WEEK = {TaskHelper.MONDAY, TaskHelper.TUESDAY, TaskHelper.WEDNESDAY, TaskHelper.THURSDAY, TaskHelper.FRIDAY, TaskHelper.SATURDAY, TaskHelper.SUNDAY };

    private FilterByDay(Context context){
        try {
            allTasks = DBActions.getInstans(context).getListTasks();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static FilterByDay getInstance(Context context){
        if(currentInstance == null)
            currentInstance = new FilterByDay(context);
        return currentInstance;
    }


    public List<Task> getImportantTasks(){
        List<Task> importantTasks = new LinkedList<>();
        for(Task task: allTasks)
            if(task.isImportant())
                importantTasks.add(task);
        return importantTasks;
    }

    public  List<Task> getTasksByClass(byte taskClass){
        List<Task> tasksByClass = new LinkedList<>();
        for(Task task: allTasks){
            if(task.getTaskClass() == taskClass)
                tasksByClass.add(task);
        }
        return tasksByClass;
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
        return tasksByNeededDay;
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
        if(tasksIdsByNeededDay.contains(Integer.valueOf(String.valueOf(task.getId()))))
            tasksByNeededDay.add(task);
    }

    private void monthRepeatedAdding(Task task, GregorianCalendar dayByCalendar, List<Task> tasksByNeededDay){
        //todo will add this method
    }

    private void yearRepeatedAdding(Task task, GregorianCalendar dayByCalendar, List<Task> tasksByNeededDay){
        //todo will add this method
    }

}
