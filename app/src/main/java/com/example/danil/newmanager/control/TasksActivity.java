package com.example.danil.newmanager.control;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.danil.newmanager.R;

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
