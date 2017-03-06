package com.example.danil.newmanager.control;



import android.app.ActionBar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.danil.newmanager.R;
import com.example.danil.newmanager.view.fragment.DatePicker;
import com.example.danil.newmanager.view.fragment.TimePicker;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

public class AddTasks extends AppCompatActivity {

    private final static String logName = "log_Main";
    int DIALOG_DATE = 1;
    EditText title;
    EditText description;
    Spinner taskClass;
    Spinner taskClassRepeat;
    SwitchCompat important;
    SwitchCompat repeated;
    SwitchCompat notification ;
    TextView startTimeImg;
    TextView startTimeHint;
    GregorianCalendar startTime;
    Map<Integer, Boolean> days;
    int[] daysId;
    LinearLayout weekMonthYear;
    LinearLayout repeatedLayout;

    public GregorianCalendar getStartTime() {
        if(startTime == null)
            startTime = new GregorianCalendar();
        return startTime;
    }

    public void setStartTime(GregorianCalendar startTime) {
        this.startTime = startTime;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //init view elements
        setContentView(R.layout.activity_add_tasks);
        title = (EditText) findViewById(R.id.title);
        description = (EditText) findViewById(R.id.description);
        taskClass = (Spinner) findViewById(R.id.task_class);
        taskClassRepeat = (Spinner) findViewById(R.id.task_class);
        important = (SwitchCompat) findViewById(R.id.important);
        repeated = (SwitchCompat) findViewById(R.id.repeated);
        notification = (SwitchCompat)  findViewById(R.id.notification);
        startTimeImg = (TextView) findViewById(R.id.start_time_hint);
        weekMonthYear = (LinearLayout) findViewById(R.id.week_month_year);
        repeatedLayout = (LinearLayout) findViewById(R.id.repeated_layout);
        startTimeHint = (TextView) findViewById(R.id.start_time_hint);




        //spinner for class type
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.tasks_class_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        taskClass.setAdapter(adapter);


        //spinner for class repeated type
        ArrayAdapter<CharSequence> adapterClassRepeat = ArrayAdapter.createFromResource(this,
                R.array.period_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        taskClassRepeat.setAdapter(adapterClassRepeat);


        //set visible params for view elements
        commonTaskChoise(false);



        //setting listeners
        repeated.setOnCheckedChangeListener(repeatedListener());
        taskClass.setOnItemClickListener(classTaskListener());
        taskClassRepeat.setOnItemClickListener(repeatedClassListener());




    }

    // TODO добавить класс задач будильник
    public boolean validation(){
        throw new RuntimeException();
    }


    //TODO добавить фильтрый по классам задач
    //TODO
    //TODO добавить в БД УВЕДОМЛЕНИЕ!!!!звуковое!!!
    public void setTime(View view) {
        DialogFragment timeDialog;
        DialogFragment dateDialog;
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
    public void weekDaysClick(View view){
        if(days == null){
            days = new HashMap<>();
            days.put(daysId[0], false);
            days.put(daysId[1], false);
            days.put(daysId[2], false);
            days.put(daysId[3], false);
            days.put(daysId[4], false);
            days.put(daysId[5], false);
            days.put(daysId[6], false);
        }

        TextView tx = (TextView) view;
        Log.d(logName, "Значение мапы на входе" + days.get(view.getId()));
        if(days.get(view.getId())){
            tx.setTextColor(getResources().getColor(R.color.black));
            tx.setBackgroundColor(getResources().getColor(R.color.white));
            days.put(view.getId(), false);
        } else {
            tx.setTextColor(getResources().getColor(R.color.white));
            tx.setBackgroundColor(getResources().getColor(R.color.days));
            days.put(view.getId(), true);
        }
    }


    public void drawWeek(LinearLayout view){
        daysId = new int[7];
        String[] week = {"Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Вс"};
        TextView tmp;

        LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        for(int i = 0; i < 7; i++){
            tmp = new TextView(this);
            tmp.setText(week[i]);
            tmp.setGravity(Gravity.CENTER);
            daysId[i] = View.generateViewId();
            tmp.setId(daysId[i]);
            tmp.setLayoutParams(lParams);
            tmp.getLayoutParams().height = tmp.getLayoutParams().MATCH_PARENT;
            tmp.getLayoutParams().width = tmp.getLayoutParams().WRAP_CONTENT;
            tmp.setTextSize(15* getResources().getDisplayMetrics().density);
            tmp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    weekDaysClick(v);
                }
            });
            tmp.setTextColor(getResources().getColor(R.color.black));
            tmp.setBackgroundColor(getResources().getColor(R.color.white));
            ((LinearLayout.LayoutParams) tmp.getLayoutParams()).weight = 1;
            view.addView(tmp,lParams);
        }

    }

    public void drawMonth(View view){
        
    }
    public void drawYear(View view){

    }


    public AdapterView.OnItemClickListener classTaskListener(){
        return  new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        commonTaskChoise(false);
                        break;
                    case 1:
                        break;
                    case 2:
                        //purchases equal CommonTask
                        commonTaskChoise(false);
                        break;
                    case 3:
                        //sport equal CommonTask
                        commonTaskChoise(false);
                        break;
                    case 4:
                        break;
                }
            }
        };
    }

    public void commonTaskChoise(boolean a){
        if(a){
            repeatedLayout.setVisibility(View.INVISIBLE);
            weekMonthYear.setVisibility(View.INVISIBLE);
            startTimeHint.setText("Время");
        } else {
            repeated.setVisibility(View.VISIBLE);
            repeated.setChecked(false);
            notification.setVisibility(View.VISIBLE);
            repeatedLayout.setVisibility(View.INVISIBLE);
            weekMonthYear.setVisibility(View.INVISIBLE);
            startTimeHint.setText("Дата и время");
        }
    }

    public CompoundButton.OnCheckedChangeListener repeatedListener(){
        return new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    weekMonthYear.removeAllViewsInLayout();
                    drawWeek(weekMonthYear);
                    commonTaskChoise(isChecked);
                } else {
                    commonTaskChoise(isChecked);
                }
            }
        };
    }

    public AdapterView.OnItemClickListener repeatedClassListener(){
        return  new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                weekMonthYear.removeAllViewsInLayout();
                switch (position){
                    case 0:
                        drawWeek(weekMonthYear);
                        break;
                    case 1:
                        drawMonth(weekMonthYear);
                        break;
                    case 2:
                        drawYear(weekMonthYear);
                        break;
                }
            }
        };
    }



}