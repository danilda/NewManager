package com.example.danil.newmanager.control;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.danil.newmanager.R;
import com.example.danil.newmanager.model.DBActions;
import com.example.danil.newmanager.model.Task;

public class UpdateTasks extends TaskController {
    Task currentTask;
    DBActions db;
    SwitchCompat active;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_tasks);

        db = DBActions.getInstans(this);
        Intent intent = getIntent();
        currentTask = db.getTaskBeID(intent.getLongExtra("TaskId", -1));
        if(currentTask == null)
            this.finish();

        init();
    }

    private void init(){
        title = (EditText) findViewById(R.id.title);
        description = (EditText) findViewById(R.id.description);
        active = (SwitchCompat)  findViewById(R.id.active);
        important = (SwitchCompat) findViewById(R.id.important);
        notification = (SwitchCompat)  findViewById(R.id.notification);
        weekMonthYear = (LinearLayout) findViewById(R.id.week_month_year);
        startTimeImg = (TextView) findViewById(R.id.start_time_hint);
        startTimeHint = (TextView) findViewById(R.id.start_time_hint);
        conteinerNotification = (GridLayout) findViewById(R.id.container_notification);
        containerSetTime = (LinearLayout) findViewById(R.id.container_set_time);

        title.setText(currentTask.getTitle());
        description.setText(currentTask.getDescription());
        active.setChecked(currentTask.isActive());
        important.setChecked(currentTask.isImportant());
        notification.setChecked(currentTask.isNotification());

    }
}
