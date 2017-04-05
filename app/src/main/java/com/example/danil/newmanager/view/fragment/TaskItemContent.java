package com.example.danil.newmanager.view.fragment;

import android.util.Log;

import com.example.danil.newmanager.model.Task;
import com.example.danil.newmanager.model.TaskHelper;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

/**
 * Created by danil on 25.03.2017.
 */

public class TaskItemContent {
    private static final String LOG_NAME = "TaskItemContent";
    String title;
    String date;
    int img_id;

    public TaskItemContent(Task i) {

        SimpleDateFormat sdf = new SimpleDateFormat();
        title = i.getTitle();
        if(!i.isRepeated()){
            date = sdf.format(i.getTime().getTime());
        } else {
            String[] allDays = {"Пт","Вт","Ср","Чт","Пт","Сб","Вс"};
            date = "";
            String[] tmp = i.getRepeatedTime().replace("|", " ").split(" ");
            Log.d(LOG_NAME, "Array of string " + i.getRepeatedTime());
            for (int y = 0; y < tmp.length; y++)
                Log.d(LOG_NAME, "Array of string " + y + " " + tmp[y]);

            if(i.getRepeatedClass() == 0)
                for (String y : tmp) {
                    Log.d(LOG_NAME, "Array of string " + y);
                    if (y.length() > 0 && !y.equals("|") )
                        date += y.charAt(0) == tmp[1].charAt(0)?allDays[y.charAt(0) - 97]:
                                (", "+allDays[y.charAt(0) - 97]);
                }
            if(i.getRepeatedClass() == 1){
                for (String y : tmp){
                    if(y.length() > 0 && !y.equals("|") )
                        date += y.equals(tmp[1])?y : (", "+y);

                }

            }
            int mins = i.getTime().get(GregorianCalendar.MINUTE);
            int hours = i.getTime().get(GregorianCalendar.HOUR);
            date += " \n"+ (hours < 10? ("0")+hours : hours) + ":" + (mins < 10? ("0")+mins : mins);



        }

        img_id = TaskHelper.getImageById(i.getImgID());
    }
}
