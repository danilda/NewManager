package com.example.danil.newmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.widget.ArrayAdapter;
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
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.planets_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        taskClass.setAdapter(adapter);
    }

    // TODO добавить классы задач (ДР, покупки, ежедневное, спорт , встреча)
    public boolean validation(){
        throw new RuntimeException();
    }

}
