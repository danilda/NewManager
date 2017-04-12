package com.example.danil.newmanager.control;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;

import com.example.danil.newmanager.R;
import com.example.danil.newmanager.model.DBActions;
import com.example.danil.newmanager.model.Task;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.GregorianCalendar;

public class MainActivity extends Main {
    private final static String logName = "log_Main_Activity";
    private LinearLayout nearTasks;
    private MaterialCalendarView calendarView;
    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Main.setLayoutResID(MainActivity.class);
        super.onCreate(savedInstanceState);
        nearTasks = (LinearLayout) findViewById(R.id.nearTasks);
//        View v = (View) findViewById(R.layout.task_item);
//        nearTasks.addView();
        calendarView = (MaterialCalendarView) findViewById(R.id.calendarView);
        calendarView.setSelectedDate(new GregorianCalendar());

        scrollView = (ScrollView) findViewById(R.id.scrollView);


        DBActions db = DBActions.getInstans(this);
        ArrayList<Task> arr = new ArrayList<>();
        try {
            arr = db.getListTasks();
        } catch (ParseException e) {
            Log.d(logName, "[Error] ParseException!!!!!!!!!!!!");
        }
        arr.add(new Task("Го бегать", "Хочу не быть жирным", (byte) 1));


        Log.d(logName, "arr size = " + arr.size());

        drawTasks((ListView) findViewById(R.id.soon_tasks), arr);
        // массивы данных
        scrollView.fullScroll(ScrollView.FOCUS_UP);
        scrollView.pageScroll(View.FOCUS_UP);
        scrollView.setVerticalScrollbarPosition(View.FOCUS_UP);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

}
