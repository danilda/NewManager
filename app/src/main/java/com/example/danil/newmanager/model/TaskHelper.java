package com.example.danil.newmanager.model;

import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by danil on 20.03.2017.
 */

public class TaskHelper {
    public static String parseToRepeatedTime(GregorianCalendar time, byte timeType, List<Integer> a){
        StringBuilder sb = new StringBuilder();
        sb.append(timeType);
        for(Integer i : a){
            sb.append('|');
            sb.append(i.toString());
        }
        return sb.toString();
    }

    public static GregorianCalendar parseFromRepeatedTime(){
        return null;
    }
}
