package com.example.danil.newmanager;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
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


import com.example.danil.newmanager.fragment.Fragment1;
import com.example.danil.newmanager.fragment.Fragment2;
import com.example.danil.newmanager.fragment.Fragment3;
import com.example.danil.newmanager.model.ItemSlideMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by danil on 12.02.2017.
 */

public class Main extends AppCompatActivity {
    private List<ItemSlideMenu> listSliding;
    private SlidingMenuAdapter adapter;
    private ListView listViewSliding;
    private DrawerLayout drawerLayout;
    private RelativeLayout mainContent;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private static Class<?> layoutResID ;
    private final static String logName = "log_Main";

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
        listViewSliding.setItemChecked(0, true);
        //Close menu
        drawerLayout.closeDrawer(listViewSliding);

        //Display com.example.taskmg.fragment 1 when start
        if(layoutResID.equals(MainActivity.class)){
            replaceFragment(0);
        } else if(layoutResID.equals(TasksActivity.class)){
            replaceFragment(1);
        }

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

        if(actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    //Create method replace com.example.taskmg.fragment

    private void replaceFragment(int pos) {
        Log.d(logName, "replaceFragment начало :" + pos );
        Fragment fragment = null;
        switch (pos) {
            case 0:
                fragment = new Fragment1();
                break;
            case 1:
                fragment = new Fragment2();
                break;
            case 2:
                fragment = new Fragment3();
                break;
            default:
                fragment = new Fragment1();
                break;
        }

        if(null!=fragment) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.main_context, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
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

}
