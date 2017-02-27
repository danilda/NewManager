package com.example.danil.newmanager;



import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.danil.newmanager.view.fragment.DatePicker;
import com.example.danil.newmanager.view.fragment.TimePicker;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class AddTasks extends AppCompatActivity {
    int DIALOG_DATE = 1;
    EditText title;
    EditText description;
    Spinner taskClass;
    SwitchCompat important;
    SwitchCompat repeated;
    TextView startTimeImg;
    GregorianCalendar startTime;
    GregorianCalendar endTime;

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
        startTimeImg = (TextView) findViewById(R.id.start_time_hint);
    }

    // TODO добавить класс задач будильник
    public boolean validation(){
        throw new RuntimeException();
    }


    //TODO сделать повторяющиеся задачи без временных рамок и по дням недели;
    //TODO можно добавить еще один список выбора как для класса задач в котором будет повторяется по дням недели, ежедневно,
    //TODO одинаковый промежуток времени(а тут подумать, что бы каждый день, но с 9 и до 21 с одинаковым временным промежутком)
    //TODO
    //TODO подправить внешний вид установки даты
    public void setTime(View view) {

        DialogFragment timeDialog;
        DialogFragment dateDialog;
        switch (view.getId()){
            case R.id.start_time_hint :
                timeDialog = new TimePicker();
                timeDialog.show(getSupportFragmentManager(), "timePicker");
                //*****
                startTime = ((TimePicker)timeDialog).getTime();
                dateDialog = new DatePicker();
                dateDialog.show(getSupportFragmentManager(), "datePicker");
                //*****
                startTime.set(Calendar.YEAR, ((DatePicker)dateDialog).getDate().get(Calendar.YEAR));
                startTime.set(Calendar.MONTH, ((DatePicker)dateDialog).getDate().get(Calendar.MONTH));
                startTime.set(Calendar.DAY_OF_MONTH, ((DatePicker)dateDialog).getDate().get(Calendar.DAY_OF_MONTH));
                break;
            case R.id.end_time_hint :
                timeDialog = new TimePicker();
                timeDialog.show(getSupportFragmentManager(), "timePicker");
                //*****
                endTime = ((TimePicker)timeDialog).getTime();
                dateDialog = new DatePicker();
                dateDialog.show(getSupportFragmentManager(), "datePicker");
                //*****
                endTime.set(Calendar.YEAR, ((DatePicker)dateDialog).getDate().get(Calendar.YEAR));
                endTime.set(Calendar.MONTH, ((DatePicker)dateDialog).getDate().get(Calendar.MONTH));
                endTime.set(Calendar.DAY_OF_MONTH, ((DatePicker)dateDialog).getDate().get(Calendar.DAY_OF_MONTH));
                break;
        }


    }

}