package com.example.danil.newmanager;

import android.content.Intent;
import android.os.Bundle;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;


import com.example.danil.newmanager.model.Task;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by danil on 12.02.2017.
 */

public class Main extends AppCompatActivity {
    private final static String logName = "log_Main";
    final String ATTRIBUTE_NAME_ID_IMG = "id_img";
    final String ATTRIBUTE_NAME_TITLE = "title";
    final String ATTRIBUTE_NAME_DESCRIPTION = "description";
    final String ATTRIBUTE_NAME_DATE = "date";

    private List<ItemSlideMenu> listSliding;
    private SlidingMenuAdapter adapter;
    private ListView listViewSliding;
    private DrawerLayout drawerLayout;
    private RelativeLayout mainContent;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private static Class<?> layoutResID ;


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
        } else if(layoutResID.equals(TasksActivity.class)){
            setContentView(R.layout.activity_tasks);
            Log.d(logName, "TasksActivity начало! ");
        }
        Log.d(logName, "onCreate начало");
        listViewSliding = (ListView) findViewById(R.id.sliding_menu);
        Log.d(logName, "listViewSliding начало :" + listViewSliding );
        drawerLayout = (DrawerLayout) findViewById(R.id.activity_main);
        Log.d(logName, "drawerLayout начало :" + drawerLayout );
        mainContent = (RelativeLayout) findViewById(R.id.main_context);
        Log.d(logName, "mainContent начало :" + mainContent );
        listSliding = new ArrayList<>();
        listSliding.add(new ItemSlideMenu(R.mipmap.ic_launcher, "Setting" ));
        listSliding.add(new ItemSlideMenu(R.mipmap.ic_launcher, "About"));
        listSliding.add(new ItemSlideMenu(R.mipmap.ic_launcher, "Android"));
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
                if(!Main.layoutResID.equals(TasksActivity.class))
                    intent = new Intent(this, TasksActivity.class);
                break;
            case 2:
                intent = new Intent(this, TasksActivity.class);
                break;
            default:
                intent = new Intent(this, TasksActivity.class);

                break;
        }
        if(null!=intent) {
            startActivity(intent);
            this.finish();
        }
    }

    public void drawTasks(ListView into, ArrayList<Task> input){

        ArrayList<Map<String, Object>> data = new ArrayList<>(input.size());
        Map<String, Object> map;
        Task taskTmp;
        for(int i = 0 ; i < input.size(); i++){
            map = new HashMap<>();
            taskTmp = input.get(i);
            //TODO добавить картинку вместо id
            map.put(ATTRIBUTE_NAME_ID_IMG, taskTmp.getImgID());
            map.put(ATTRIBUTE_NAME_TITLE, taskTmp.getTitle());
//            map.put(ATTRIBUTE_NAME_DESCRIPTION, taskTmp.getDescription().substring(0, 200));
            map.put(ATTRIBUTE_NAME_DATE, taskTmp.getNextTime());
            data.add(map);
        }
        Log.d(logName, "data.size : " + data.size() );

        String[] from = { ATTRIBUTE_NAME_ID_IMG, ATTRIBUTE_NAME_TITLE,
                /*ATTRIBUTE_NAME_DESCRIPTION,*/ ATTRIBUTE_NAME_DATE};
        int[] to = { R.id.item_img, R.id.item_title, R.id.item_date};

        // создаем адаптер
        SimpleAdapter sAdapter = new SimpleAdapter(this, data, R.layout.task_item,
                from, to);

        // определяем список и присваиваем ему адаптер
        into.setAdapter(sAdapter);
//        into.setDividerHeight(90*data.size());

    }

}
