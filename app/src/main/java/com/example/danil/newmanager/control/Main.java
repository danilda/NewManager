package com.example.danil.newmanager.control;

import android.content.Intent;
import android.os.Bundle;


import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import com.example.danil.newmanager.R;
import com.example.danil.newmanager.model.Task;
import com.example.danil.newmanager.view.fragment.ItemAdapter;
import com.example.danil.newmanager.view.fragment.TaskItemContent;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by danil on 12.02.2017.
 */

public class Main extends AppCompatActivity {
    private final static String logName = "log_Main";
    final String ATTRIBUTE_NAME_ID_IMG = "id_img";
    final String ATTRIBUTE_NAME_TITLE = "title";
    final String ATTRIBUTE_NAME_DESCRIPTION = "description";
    final String ATTRIBUTE_NAME_DATE = "date";



    protected List<ItemSlideMenu> listSliding;
    protected SlidingMenuAdapter adapter;
    protected ListView listViewSliding;
    protected DrawerLayout drawerLayout;
    protected RelativeLayout mainContent;
    protected ActionBarDrawerToggle actionBarDrawerToggle;
    protected static Class<?> layoutResID ;

    protected ActionBar.Tab activeTab;
    protected ActionBar.Tab unactiveTab;

    public static Class<?> getLayoutResID() {
        return layoutResID;
    }

    public static void setLayoutResID(Class<?> layoutResID) {
        Main.layoutResID = layoutResID;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(layoutResID.equals(MainActivity.class)){
            setContentView(R.layout.activity_main);
            Log.d(logName, "MainActivity начало! ");
        } else if(layoutResID.equals(RepeatedTasksActivity.class)){
            setContentView(R.layout.activity_tasks);
            Log.d(logName, "RepeatedTasksActivity начало! ");
        }


        Log.d(logName, "onCreate начало");
        listViewSliding = (ListView) findViewById(R.id.sliding_menu);
        Log.d(logName, "listViewSliding начало :" + listViewSliding );
        drawerLayout = (DrawerLayout) findViewById(R.id.activity_main);
        Log.d(logName, "drawerLayout начало :" + drawerLayout );
        mainContent = (RelativeLayout) findViewById(R.id.main_context);
        Log.d(logName, "mainContent начало :" + mainContent );
        listSliding = new ArrayList<>();
        listSliding.add(new ItemSlideMenu(R.drawable.calendar_1, "Дажборд" ));
        listSliding.add(new ItemSlideMenu(R.drawable.img0, "Повторяющиеся задачи"));
        listSliding.add(new ItemSlideMenu(R.drawable.gift_1, "День Рождения"));
        listSliding.add(new ItemSlideMenu(R.drawable.online_purchase, "Покупки"));
        listSliding.add(new ItemSlideMenu(R.drawable.weightlifting_1, "Спорт"));
        listSliding.add(new ItemSlideMenu(R.drawable.inclined_bell, "Будильник"));
        adapter = new SlidingMenuAdapter(this, listSliding);
        listViewSliding.setAdapter(adapter);

        //Display icon to open/ close sliding list
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Set title
        setTitle(listSliding.get(0).getTitle());
        //item selected
//        listViewSliding.setItemChecked(0, true);
        //Close menu
        drawerLayout.closeDrawer(listViewSliding);

        //Hanlde on item click

        listViewSliding.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Set title
                setTitle(listSliding.get(position).getTitle());
                //item selected
                listViewSliding.setItemChecked(position, true);
                //Replace com.example.taskmg.fragment
                newActivity(position);
                //Close menu
                drawerLayout.closeDrawer(listViewSliding);
            }
        });

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_opened, R.string.drawer_closed){

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
            }
        };

        drawerLayout.setDrawerListener(actionBarDrawerToggle);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_refresh)
            addTask();
        return actionBarDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();

    }



    public void newActivity(int position){
        Intent intent = null;
        Log.d(logName, "position начало :" + position );
        switch (position) {
            case 0:
                if(!Main.layoutResID.equals(MainActivity.class))
                    intent = new Intent(this, MainActivity.class);
                break;
            case 1:
                if(!Main.layoutResID.equals(RepeatedTasksActivity.class))
                    intent = new Intent(this, RepeatedTasksActivity.class);
                break;
            case 2:
                if(!Main.layoutResID.equals(BirthdayActivity.class))
                intent = new Intent(this, BirthdayActivity.class);
                break;
            case 3:
                if(!Main.layoutResID.equals(PurchasesActivity.class))
                intent = new Intent(this, PurchasesActivity.class);
                break;
            case 4:
                if(!Main.layoutResID.equals(SportActivity.class))
                intent = new Intent(this, SportActivity.class);
                break;
            case 5:
                if(!Main.layoutResID.equals(NotificationActivity.class))
                intent = new Intent(this, NotificationActivity.class);
                break;
            default:


                break;
        }
        if(null!=intent) {
            startActivity(intent);
            this.finish();
        }
    }

    public void drawTasks(ListView into, List<Task> input){
        ArrayList<TaskItemContent> items = new ArrayList<>();
        for(Task i : input){
            items.add(new TaskItemContent(i));
        }
        ItemAdapter adapter = new ItemAdapter(this, items);
        ViewGroup.LayoutParams params = into.getLayoutParams();
        params.height = (int)(90*items.size()* getResources().getDisplayMetrics().density);
        into.setLayoutParams(params);
        into.setAdapter(adapter);
        into.setBackgroundColor(getResources().getColor(R.color.white));
    }


    public void addTask() {
        Intent intent = new Intent(this, AddTasks.class);
        startActivity(intent);
    }
    public void updateTask(Task currentTask) {
        Intent intent = new Intent(this, UpdateTasks.class);
        intent.putExtra("TaskId", currentTask.getId());
        startActivity(intent);
    }


}
