package com.example.danil.newmanager.control;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import com.example.danil.newmanager.R;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by danil on 31.03.2017.
 */

public class TaskController extends AppCompatActivity {
    private final static String logName = "Task_Controller_Log";

    public EditText title;
    public EditText description;
    public Spinner taskClass;
    public Spinner taskClassRepeat;
    public SwitchCompat important;
    public SwitchCompat repeated;
    public SwitchCompat notification ;
    public TextView startTimeImg;
    public TextView startTimeHint;
    public GregorianCalendar startTime;
    public LinearLayout weekMonthYear;
    public GridLayout conteinerRepeatedClass;
    public GridLayout conteinerRepeated;
    public GridLayout conteinerNotification;
    public LinearLayout containerSetTime;

    Map<Integer, Boolean> days;
    byte repeatedClass = -1;
    int[] daysId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    /**
     * return type of tasks
     * if task like common task - true
     * else - false
     * 0 - show date + time
     * 1 - show date
     * 2 - show time
     * */
    public byte switcher(int classTask, boolean repeated){
        if(classTask == 1) {
            return 1;
        } else if(repeated) {
            return 2;
        } else {
            return 0;
        }
    }



    public void drawMonth(View view){

    }
    public void drawYear(View view){

    }

    public void drawWeek(LinearLayout view){
        Log.d(logName, "используем drawWeek");
        daysId = new int[7];
        String[] week = {"Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Вс"};
        TextView tmp;

        LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        for(int i = 0; i < 7; i++){
            tmp = new TextView(this);
            tmp.setText(week[i]);
            tmp.setGravity(Gravity.CENTER);
            daysId[i] = View.generateViewId();
            tmp.setId(daysId[i]);
            tmp.setLayoutParams(lParams);
            tmp.getLayoutParams().height = tmp.getLayoutParams().WRAP_CONTENT;
            tmp.getLayoutParams().width = tmp.getLayoutParams().WRAP_CONTENT;
            tmp.setTextSize(10* getResources().getDisplayMetrics().density);
            tmp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    weekDaysClick(v);
                }
            });
            tmp.setTextColor(getResources().getColor(R.color.black));
            tmp.setBackgroundColor(getResources().getColor(R.color.white));
            ((LinearLayout.LayoutParams) tmp.getLayoutParams()).weight = 1;
            Log.d(logName, "используем drawWeek - добавляем день недели");
            view.addView(tmp,lParams);
        }

    }

    public void weekDaysClick(View view){
        if(days == null){
            days = new HashMap<>();
            days.put(daysId[0], false);
            days.put(daysId[1], false);
            days.put(daysId[2], false);
            days.put(daysId[3], false);
            days.put(daysId[4], false);
            days.put(daysId[5], false);
            days.put(daysId[6], false);
        }

        TextView tx = (TextView) view;
        Log.d(logName, "Значение мапы на входе" + days.get(view.getId()));
        if(days.get(view.getId())){
            tx.setTextColor(getResources().getColor(R.color.black));
            tx.setBackgroundColor(getResources().getColor(R.color.white));
            days.put(view.getId(), false);
        } else {
            tx.setTextColor(getResources().getColor(R.color.white));
            tx.setBackgroundColor(getResources().getColor(R.color.days));
            days.put(view.getId(), true);
        }
    }

    public List<Integer> getNumberOfRepeate(){
        List<Integer> tmp = new LinkedList<>();
        Iterator iterator = days.keySet().iterator();
        while (iterator.hasNext()){
            int tmp2 = (Integer) iterator.next();
            if(days.get(tmp2)){
                tmp.add(getDaysIndex(tmp2));
            }
        }
        return (tmp.size() != 0)?tmp:null;
    }

    private int getDaysIndex(int id){
        for(int i = 0; i < 7; i++)
            if(daysId[i] == id)
                return i;
        throw new IndexOutOfBoundsException("ID дня вне рамок ");
    }

    public GregorianCalendar getStartTime() {
        if(startTime == null)
            startTime = new GregorianCalendar();
        return startTime;
    }


    public void setStartTime(GregorianCalendar startTime) {
        this.startTime = startTime;
    }

    public void setDate(GregorianCalendar date, int year, int month, int day){
        date.set(Calendar.YEAR, year);
        date.set(Calendar.MONTH, month);
        date.set(Calendar.DAY_OF_MONTH, day);
        startTime = date;
    }

    public void setTime(GregorianCalendar time, int hour, int min){
        time.set(Calendar.HOUR_OF_DAY , hour);
        time.set(Calendar.MINUTE , min);
        startTime = time;
    }

}
