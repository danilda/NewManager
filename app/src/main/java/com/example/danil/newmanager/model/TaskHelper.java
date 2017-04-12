package com.example.danil.newmanager.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.danil.newmanager.R;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
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

    private static SharedPreferences sPref;

    private static String[] allKeys= {"a", "b", "c", "d", "e", "f", "g", "1", "2", "3", "4",
            "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18",
            "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};

    public static final String ALL_RECORDS = "*";

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
    private static Map<String, ArrayList<Integer>> repeatedMap ;


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
        a = 0;
        //!!!!!!!!!!!!!!
        switch (a){
            case 0:
                tmp = R.drawable.img0;
        }
        return tmp;
    }

    public static void initRepeatedMap(AppCompatActivity context){
//        repeatedMap
        Log.d(LOG_NAME, "initRepeatedMap - is start");
        Map<String, String> tmp = getFromSharedPereferences(context, ALL_RECORDS);
        if(tmp == null)
            return;
        repeatedMap = new HashMap<>();
        Set<String> set = tmp.keySet();
        for(String a : set){
            String valuesInString = tmp.get(a); // string with ids e.g [123|2313]
            String[] arrStrings = valuesInString.split("|"); //array with ids e.g. [123],[2313]
            ArrayList<Integer> ids;
            if(arrStrings.length != 0) {
                ids = new ArrayList<>();
                for (String i : arrStrings) {
                    ids.add(Integer.valueOf(i));
                }
                repeatedMap.put(a,ids);
            }
        }

    }

    public static void updateRepeadetMap(AppCompatActivity context, ArrayList<Task> list){
        Log.d(LOG_NAME, "updateRepeadetMap - is start");
        removeSharedPreferences(context);
        Map<String, String> tmpMap = new HashMap<>();
        for(Task i : list){
            if(i.isRepeated()){
                String[] y = i.getRepeatedTime().split("|");//e.g.{[a],[b]}
                if(y.length > 0){
                    for(int k = 0; k < y.length; k++)
                        addInMap(y[k], Long.toString(i.getId()) ,tmpMap);
                }
            }
        }
        Set<String> set = tmpMap.keySet();
        for(String keyTmp: set){
            sPref = context.getPreferences(context.MODE_PRIVATE);
            SharedPreferences.Editor ed = sPref.edit();
            Log.d(LOG_NAME, "updateRepeadetMap - is SPref map : "+ keyTmp + " " + tmpMap.get(keyTmp));
            ed.putString(keyTmp, tmpMap.get(keyTmp));
            ed.commit();
        }

    }

    private static void removeSharedPreferences(AppCompatActivity context){
        Log.d(LOG_NAME, "removeSharedPreferences - is start");
        sPref = context.getPreferences(context.MODE_PRIVATE);
        for(String i : allKeys)
            sPref.edit().remove(i);
    }

    private static void addInMap(String key, String value, Map<String, String> map){
        Log.d(LOG_NAME, "addInMap - is start");
        String tmpVal = map.get(key);
        if(tmpVal==null){
            map.put(key,value);
        } else {
            tmpVal+= "|" + value;
            map.put(key, tmpVal);
            Log.d(LOG_NAME, "addInMap - is SPref map : "+ key + " " + tmpVal);
        }

    }
    /**
     * @param context
     * @param key - if key == '*' - it's mean getting all records
     * @return
     */
    private static Map<String, String> getFromSharedPereferences(AppCompatActivity context, String key) {
        Log.d(LOG_NAME, "getFromSharedPereferences - is start");
        sPref = context.getPreferences(context.MODE_PRIVATE);
        Map<String, String> map = new HashMap<>();

        String tmp;
        if (key.equals(ALL_RECORDS)) {
            for (String a : allKeys) {
                tmp = sPref.getString(a, null);
                if (tmp != null) {
                    map.put(a, tmp);
                }
            }
        } else {
            tmp = sPref.getString(key, null);
            if (tmp != null) {
                map.put(key, tmp);
            }
        }
        Log.d(LOG_NAME, "getFromSharedPereferences - end map.size = "+ map.size());
        if(map.size() == 0)
            return null;

        return map;

    }

    public static Map<String, ArrayList<Integer>> getRepeatedMap(){
        if(repeatedMap == null)
            throw new NullPointerException("repeatedMap в TaskHelper еще не инициализирована");
        return repeatedMap;
    }


}
