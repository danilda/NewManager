package com.example.danil.newmanager.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.danil.newmanager.R;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class TaskHelper {

    private static final String LOG_NAME = "*--TaskHelper--*";
    private static final String SHARED_PEFERENCES = "H_P";
    public static final String MONDAY = "a";
    public static final String TUESDAY = "b";
    public static final String WEDNESDAY = "c";
    public static final String THURSDAY = "d";
    public static final String FRIDAY = "e";
    public static final String SATURDAY = "f";
    public static final String SUNDAY = "g";

    /**
     * a - Monday
     * b - Tuesday
     * c - Wednesday
     * d - Thursday
     * e - Friday
     * f - Saturday
     * g - Sunday
     *
     * Number mean equal day of month
     */
    private static Map<String, List<Long>> repeatedMap ;


    public static String parseToRepeatedTime(byte timeType, List<Integer> list){
        StringBuilder sb = new StringBuilder();
        for(Integer i : list)
            Log.d(LOG_NAME, "list val = " + i.byteValue());
        if(timeType == 0){
            char a = 'a';
            for(Integer i : list){
                sb.append('|');
                sb.append((char)(a+i.byteValue()));
            }
        } else if(timeType == 1) {
            for(Integer i : list){
                sb.append('|');
                sb.append(i.toString());
            }
        } else {
            throw new RuntimeException("Парсим годичное повторение");
        }
        Log.d(LOG_NAME, "parseToRepeatedTime result = " + sb.toString());
        return sb.toString();
    }

    public static GregorianCalendar parseFromRepeatedTime(){
        return null;
    }

    public static int getImageById(byte a){
        Log.d(LOG_NAME, "getImageById - is start");
        int tmp = -1;
        //!!!!!!!!!!!!!!
//        a = 0;

        //!!!!!!!!!!!!!!
        switch (a){
            case 0:
                tmp = R.drawable.calendar_1;
                break;
            case 1:
                tmp = R.drawable.gift_1;
                break;
            case 2:
                tmp = R.drawable.online_purchase;
                break;
            case 3:
                tmp = R.drawable.weightlifting_1;
                break;
            case 4:
                tmp = R.drawable.inclined_bell;
                break;
            case 10:
                tmp = R.drawable.exclamation_mark;
                break;
            default:
                tmp = R.drawable.img0;
        }
        return tmp;
    }

    public static void initRepeatedMap(List<Task> allTasks){
//        repeatedMap
        Log.d(LOG_NAME, "initRepeatedMap - is start");
        repeatedMap = new HashMap<>();
        for(Task task : allTasks){
            if(task.isRepeated()){
                putInRepeatedMap(task);
            }
        }
    }

    private static void putInRepeatedMap(Task task){
        String repeatedDays = task.getRepeatedTime();
        String[] daysByOneDay = repeatedDays.replace("|", " ").split(" "); // [a|e|f] ---> [a e f] ---> [a] [e] [f]
        for(String day : daysByOneDay){
            day = day.trim();
            if(!day.equals("") && !day.equals("|")){
                List<Long> valueOfNeededDay = repeatedMap.get(day);
                if(valueOfNeededDay == null)
                    valueOfNeededDay = new LinkedList<>();
                valueOfNeededDay.add(task.getId());
                Log.d(LOG_NAME, "putInRepeatedMap key " + day +" value list size " + valueOfNeededDay.size() + " current value " + task.getId() );
                repeatedMap.put(day, valueOfNeededDay);
            }
        }
    }


    public static Map<String, List<Long>> getRepeatedMap(Context context){
        try {
            initRepeatedMap(DBActions.getInstans(context).getListTasks());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return repeatedMap;
    }


}
