package com.example.danil.newmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.danil.newmanager.model.Task;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Main {
    private final static String logName = "log_Main_Activity";
    private LinearLayout nearTasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Main.setLayoutResID(MainActivity.class);
        super.onCreate(savedInstanceState);
        nearTasks = (LinearLayout) findViewById(R.id.nearTasks);
//        View v = (View) findViewById(R.layout.task_item);
//        nearTasks.addView();

        ArrayList<Task> arr = new ArrayList<>();
        arr.add(new Task("Го бегать", "Хочу не быть жирным", new GregorianCalendar()));
        arr.add(new Task("Го бегать", "Хочу не быть жирным", new GregorianCalendar()));
        arr.add(new Task("Го бегать", "Хочу не быть жирным", new GregorianCalendar()));
        arr.add(new Task("Го бегать", "Хочу не быть жирным", new GregorianCalendar()));
        arr.add(new Task("Го бегать", "Хочу не быть жирным", new GregorianCalendar()));
        arr.add(new Task("Го бегать", "Хочу не быть жирным", new GregorianCalendar()));
        arr.add(new Task("Го бегать", "Хочу не быть жирным", new GregorianCalendar()));
        Log.d(logName, "arr size = " + arr.size());

        drawTasks((ListView) findViewById(R.id.soon_tasks), arr);
        // массивы данных

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }


}
