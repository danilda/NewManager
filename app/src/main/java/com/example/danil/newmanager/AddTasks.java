package com.example.danil.newmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class AddTasks extends AppCompatActivity {

    EditText title;
    EditText description;
    Spinner taskClass;
    SwitchCompat important;
    SwitchCompat repeated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tasks);
        title = (EditText) findViewById(R.id.title);
        description = (EditText) findViewById(R.id.description);
        taskClass = (Spinner) findViewById(R.id.task_class);
        important = (SwitchCompat) findViewById(R.id.important);
        repeated = (SwitchCompat) findViewById(R.id.repeated);
    }

    // TODO добавить классы задач (ДР, покупки, ежедневное, спорт , встреча)
    public boolean validation(){

    }

}
