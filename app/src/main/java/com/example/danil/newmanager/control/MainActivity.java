package com.example.danil.newmanager.control;


import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;

import com.example.danil.newmanager.R;
import com.example.danil.newmanager.model.DBActions;
import com.example.danil.newmanager.model.Filter;
import com.example.danil.newmanager.model.Task;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class MainActivity extends Main {
    private final static String logName = "log_Main_Activity";
    private LinearLayout nearTasks;
    private MaterialCalendarView calendarView;
    private ScrollView scrollView;
    private ListView tasksContainer;
    public final static String FILE_NAME = "filename";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Main.setLayoutResID(MainActivity.class);
        super.onCreate(savedInstanceState);
        nearTasks = (LinearLayout) findViewById(R.id.nearTasks);
        tasksContainer = (ListView) findViewById(R.id.soon_tasks);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
//        View v = (View) findViewById(R.layout.task_item);
//        nearTasks.addView();
        calendarView = (MaterialCalendarView) findViewById(R.id.calendarView);
        calendarView.setSelectedDate(new GregorianCalendar());

        calendarView.setOnDateChangedListener(getOnDateSelectedListener(this));

        DBActions db = DBActions.getInstans(this);
        ArrayList<Task> arr = new ArrayList<>();
        try {
            arr = db.getListTasks();
        } catch (ParseException e) {
            Log.d(logName, "[Error] ParseException!!!!!!!!!!!!");
        }
//        arr.add(new Task("Го бегать", "Хочу не быть жирным", (byte) 1));
        Log.d(logName, "arr size = " + arr.size());
        drawTasks(tasksContainer, arr);
        // массивы данных
        scrollView.fullScroll(ScrollView.FOCUS_UP);
        scrollView.pageScroll(View.FOCUS_UP);
        scrollView.setVerticalScrollbarPosition(View.FOCUS_UP);

        startService(new Intent(this, NotificationService.class));

    }

    private OnDateSelectedListener getOnDateSelectedListener(final Main context) {
        return new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                drawCurrentDate(context, date);
            }
        };
    }

    private void drawCurrentDate(final Main context, CalendarDay date){
        Filter taskFilter = Filter.getInstance(context);
        List<Task> tasksForDrawing = taskFilter.getTasksByDay(date.getCalendar(), context);
        for(Task task : tasksForDrawing)
            Log.d(logName, task.toString());
        context.drawTasks(tasksContainer,tasksForDrawing);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        super.onResume();
        drawCurrentDate(this, calendarView.getCurrentDate());
    }


}
