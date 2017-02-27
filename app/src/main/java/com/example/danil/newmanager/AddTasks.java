package com.example.danil.newmanager;



import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
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

    private final static String logName = "log_Main";
    int DIALOG_DATE = 1;
    EditText title;
    EditText description;
    Spinner taskClass;
    SwitchCompat important;
    SwitchCompat repeated;
    TextView startTimeImg;
    GregorianCalendar startTime;
    GregorianCalendar endTime;
    int currentView;

    public int getCurrentView() {
        return currentView;
    }

    public void setCurrentView(int currentView) {
        this.currentView = currentView;
    }

    public GregorianCalendar getStartTime() {
        if(startTime == null)
            startTime = new GregorianCalendar();
        return startTime;
    }

    public void setStartTime(GregorianCalendar startTime) {
        this.startTime = startTime;
    }

    public GregorianCalendar getEndTime() {
        if(endTime == null)
            endTime = new GregorianCalendar();
        return endTime;
    }

    public void setEndTime(GregorianCalendar endTime) {
        this.endTime = endTime;
    }

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
        currentView = view.getId();

        timeDialog = new TimePicker();
        timeDialog.show(getSupportFragmentManager(), "timePicker");
        dateDialog = new DatePicker();
        dateDialog.show(getSupportFragmentManager(), "datePicker");
    }

    public void setDate(GregorianCalendar date, int year, int month, int day){
        date.set(Calendar.YEAR, year);
        date.set(Calendar.MONTH, month);
        date.set(Calendar.DAY_OF_MONTH, day);
    }
    public void setTime(GregorianCalendar time, int hour, int min){
        time.set(Calendar.HOUR_OF_DAY , hour);
        time.set(Calendar.MINUTE , min);
    }
    public void showDate(GregorianCalendar time, int id){
        StringBuilder sb = new StringBuilder();
        sb.append(time.get(Calendar.DAY_OF_MONTH)<10?"0" + time.get(Calendar.DAY_OF_MONTH):time.get(Calendar.DAY_OF_MONTH));
        sb.append(".");
        sb.append(time.get(Calendar.MONTH)<10?"0" + time.get(Calendar.MONTH):time.get(Calendar.MONTH));
        sb.append(".");
        sb.append(time.get(Calendar.YEAR));
        sb.append(" ");
        sb.append(time.get(Calendar.HOUR_OF_DAY));
        sb.append(":");
        sb.append((int)time.get(Calendar.MINUTE)<10?"0" + time.get(Calendar.MINUTE):time.get(Calendar.MINUTE));
        ((TextView)findViewById(id)).setText(sb.toString());
    }


}