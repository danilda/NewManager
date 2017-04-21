package com.example.danil.newmanager.control;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.danil.newmanager.R;
import com.example.danil.newmanager.model.Filter;

public class RepeatedTasksActivity extends Main {


    boolean isActive = true;
    ListView repeatedTask;
    private final static String LOG_NAME = "Tasks_Activity";
    Filter filter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Main.setLayoutResID(RepeatedTasksActivity.class);
        super.onCreate(savedInstanceState);

        filter = Filter.getInstance(this);
        repeatedTask = (ListView) findViewById(R.id.repeated_task_list);

        initTabs();

        drawTasks(repeatedTask, filter.getTaskByRepeated(true, true) );

    }

    private ActionBar.TabListener getTabListener(final Filter filter ){
        return new ActionBar.TabListener() {

            @Override
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                isActive = tab == activeTab;
                drawTasks(repeatedTask, filter.getTaskByRepeated(true, isActive));
            }

            @Override
            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

            }

            @Override
            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        drawTasks(repeatedTask, filter.getTaskByRepeated(true, isActive));
    }

    private void initTabs(){
        ActionBar bar = getSupportActionBar();
        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        activeTab = bar.newTab();
        activeTab.setText("Активные");
        activeTab.setTabListener(getTabListener(filter));
        bar.addTab(activeTab);

        unactiveTab = bar.newTab();
        unactiveTab.setText("Неактивные");
        unactiveTab.setTabListener(getTabListener(filter));
        bar.addTab(unactiveTab);
    }


}
