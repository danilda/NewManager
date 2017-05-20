package com.example.danil.newmanager.control;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.danil.newmanager.R;
import com.example.danil.newmanager.model.Filter;
import com.example.danil.newmanager.model.Task;

public class NotificationActivity extends Main {

    boolean isActive = true;
    ListView notificationTask;
    private final static String LOG_NAME = "Birthday_Activity";
    Filter filter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Main.setLayoutResID(NotificationActivity.class);
        setContentView(R.layout.activity_notification);
        super.onCreate(savedInstanceState);


        filter = Filter.getInstance(this);
        notificationTask = (ListView) findViewById(R.id.notification_task_list);

        initTabs();
    }

    @Override
    protected void onResume() {
        super.onResume();
        drawTasks(notificationTask, filter.getTasksByClass(Task.NOTIFICATION_CLASS, isActive));
    }

    private ActionBar.TabListener getTabListener(final Filter filter ){
        return new ActionBar.TabListener() {

            @Override
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                isActive = tab == activeTab;
                drawTasks(notificationTask, filter.getTasksByClass(Task.NOTIFICATION_CLASS, isActive));
            }

            @Override
            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

            }

            @Override
            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

            }
        };
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
