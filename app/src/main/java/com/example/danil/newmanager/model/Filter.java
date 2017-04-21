package com.example.danil.newmanager.model;

import android.content.Context;
import android.util.Log;

import java.text.ParseException;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by danil on 11.04.2017.
 * dad
 */

public class Filter {

    private List<Task> allTasks;
    private static Filter currentInstance;
    private static Context context;
    private String LOG_NAME = "Filter_LOG";
    private String[] ARRAY_DAYS_OF_WEEK = {TaskHelper.MONDAY, TaskHelper.TUESDAY, TaskHelper.WEDNESDAY, TaskHelper.THURSDAY, TaskHelper.FRIDAY, TaskHelper.SATURDAY, TaskHelper.SUNDAY };

    private Filter(Context context){
        Filter.context = context;
        try {
            allTasks = DBActions.getInstans(context).getListTasks();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static Filter getInstance(Context context){
        if(currentInstance == null)
            currentInstance = new Filter(context);
        return currentInstance;
    }


    public List<Task> getImportantTasks(){
        refreshList(context);
        List<Task> importantTasks = new LinkedList<>();
        for(Task task: allTasks)
            if(task.isImportant())
                importantTasks.add(task);
        return importantTasks;
    }

    public  List<Task> getTasksByClass(byte taskClass){
        refreshList(context);
        List<Task> tasksByClass = new LinkedList<>();
        for(Task task: allTasks){
            if(task.getTaskClass() == taskClass)
                tasksByClass.add(task);
        }
        return tasksByClass;
    }

    public  List<Task> getTasksByClass(byte taskClass, boolean isActive){
        refreshList(context);
        List<Task> tasksByClass = getTasksByClass(taskClass);
        removeRepeatedAndUnrepeatedTasks(tasksByClass, isActive);
        return tasksByClass;
    }

    /**
     *
     * @param repeated if repeated == true --> repeated if repeated == false --> unrepeated
     * @return
     */
    public List<Task> getTaskByRepeated(boolean repeated){
        refreshList(context);
        List<Task> tasksByClass = new LinkedList<>();
        for(Task task: allTasks){
            if(task.isRepeated() == repeated)
                tasksByClass.add(task);
        }
        return tasksByClass;
    }

    public List<Task> getTaskByRepeated(boolean repeated, boolean isActive){
        List<Task> tasksByClass = getTaskByRepeated(repeated);
        removeRepeatedAndUnrepeatedTasks(tasksByClass, isActive);
        return tasksByClass;
    }

    public List<Task> getTasksByDay(Calendar day, Context context){
        Filter.context = context;
        refreshList(context);
        List<Task> tasksByNeededDay = new LinkedList<>();
        for(Task task: allTasks){
            if(task.isRepeated()){
                repeatedDayAdding(task, day, tasksByNeededDay);
            } else {
                unrepeatedDayAdding(task, day, tasksByNeededDay);
            }
        }
        for(Task task : tasksByNeededDay)
            Log.d(LOG_NAME, task.toString());
        return tasksByNeededDay;
    }

    public List<Task> getTasksByDay(Calendar day, Context context,  boolean isActive){
        refreshList(context);
        List<Task> tasksByNeededDay = getTasksByDay(day, context);
        removeRepeatedAndUnrepeatedTasks(tasksByNeededDay, isActive);
        return tasksByNeededDay;
    }

    private void removeRepeatedAndUnrepeatedTasks(List<Task> neededTasks, boolean isActive){
        List<Task> removeTask = new LinkedList<>();
        for(Task task : neededTasks)
            if(task.isActive() != isActive)
                removeTask.add(task);
        for(Task task : removeTask)
            neededTasks.remove(task);
    }


    private void repeatedDayAdding(Task task, Calendar dayByCalendar, List<Task> tasksByNeededDay){
        if(task.getRepeatedClass() == Task.WEEK_REPEATED_CLASS)
            weekRepeatedAdding(task, dayByCalendar, tasksByNeededDay);
        if(task.getRepeatedClass() == Task.MONTH_REPEATED_CLASS)
            monthRepeatedAdding(task, dayByCalendar, tasksByNeededDay);
        if(task.getRepeatedClass() == Task.YEAR_REPEATED_CLASS)
            yearRepeatedAdding(task, dayByCalendar, tasksByNeededDay);
    }

    private void unrepeatedDayAdding(Task task, Calendar dayByCalendar, List<Task> tasksByNeededDay){
        if(isDaysEquals(task.getTime(), dayByCalendar))
            tasksByNeededDay.add(task);
    }

    private void weekRepeatedAdding(Task task, Calendar dayByCalendar, List<Task> tasksByNeededDay){
        String currentDayOfWeek = ARRAY_DAYS_OF_WEEK[getDayOfWeekStartFromMonday(dayByCalendar)];
        List<Long> idsByNeededDay = TaskHelper.getRepeatedMap(context).get(currentDayOfWeek);
        if(idsByNeededDay != null)
            if(idsByNeededDay.contains(Long.valueOf(String.valueOf(task.getId()))))
                tasksByNeededDay.add(task);
        if(idsByNeededDay != null)
            for(Long i : idsByNeededDay)
                Log.d(LOG_NAME, "id " + i + "Number of day " + dayByCalendar.get(Calendar.DAY_OF_WEEK) + " current day in arr" + currentDayOfWeek + " repeated time in task " + task.getRepeatedTime());
    }

    private void monthRepeatedAdding(Task task, Calendar dayByCalendar, List<Task> tasksByNeededDay){
        //todo will add this method
    }

    private void yearRepeatedAdding(Task task, Calendar dayByCalendar, List<Task> tasksByNeededDay){
        //todo will add this method
    }


    private boolean isDaysEquals(Calendar firstDay, Calendar secondDay){
        int year = Calendar.YEAR;
        int month = Calendar.MONTH;
        int day = Calendar.DAY_OF_MONTH;
        boolean result = true;
        if(firstDay.get(year) != secondDay.get(year))
            result = false;
        if(firstDay.get(month) != secondDay.get(month))
            result = false;
        if(firstDay.get(day) != secondDay.get(day))
            result = false;
        return result;
    }

    private byte getDayOfWeekStartFromMonday(Calendar dayByCalendar){
        byte dayNumber = (byte) dayByCalendar.get(Calendar.DAY_OF_WEEK);
        if(--dayNumber == 0)
            dayNumber = 7;
        return --dayNumber;
    }

    private void refreshList(Context context){
        try {
            allTasks = DBActions.getInstans(context).getListTasks();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
