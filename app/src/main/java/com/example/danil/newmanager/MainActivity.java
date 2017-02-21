package com.example.danil.newmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Main {

    private LinearLayout nearTasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Main.setLayoutResID(MainActivity.class);
        super.onCreate(savedInstanceState);
        nearTasks = (LinearLayout) findViewById(R.id.nearTasks);
//        View v = (View) findViewById(R.layout.task_item);
//        nearTasks.addView();


        // массивы данных

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }


}
