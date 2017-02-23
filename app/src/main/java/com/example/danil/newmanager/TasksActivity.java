package com.example.danil.newmanager;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class TasksActivity extends Main {

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Main.setLayoutResID(TasksActivity.class);
        super.onCreate(savedInstanceState);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTask();
            }
        });
    }




}
