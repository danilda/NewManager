package com.example.danil.newmanager;

import android.os.Bundle;
import android.view.Menu;

public class TasksActivity extends Main {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Main.setLayoutResID(TasksActivity.class);
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
}
