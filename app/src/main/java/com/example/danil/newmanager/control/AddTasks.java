package com.example.danil.newmanager.control;



import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.danil.newmanager.R;
import com.example.danil.newmanager.model.DBActions;
import com.example.danil.newmanager.model.Task;
import com.example.danil.newmanager.model.TaskHelper;
import com.example.danil.newmanager.view.fragment.DatePicker;
import com.example.danil.newmanager.view.fragment.TimePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class AddTasks extends AppCompatActivity {

    private final static String logName = "log_add_task";
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
    GridLayout conteinerRepeatedClass;
    GridLayout conteinerRepeated;
    GridLayout conteinerNotification;
    LinearLayout containerSetTime;
    byte classTask;
    byte repeatedClass = -1;

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

        //hide autoinput(убрать клавиатуру)
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        //init view elements
        setContentView(R.layout.activity_add_tasks);
        title = (EditText) findViewById(R.id.title);
        description = (EditText) findViewById(R.id.description);
        taskClass = (Spinner) findViewById(R.id.task_class);
        taskClassRepeat = (Spinner) findViewById(R.id.task_class_repeat);
        important = (SwitchCompat) findViewById(R.id.important);
        repeated = (SwitchCompat) findViewById(R.id.repeated);
        notification = (SwitchCompat)  findViewById(R.id.notification);
        startTimeImg = (TextView) findViewById(R.id.start_time_hint);
        weekMonthYear = (LinearLayout) findViewById(R.id.week_month_year);
        startTimeHint = (TextView) findViewById(R.id.start_time_hint);
        conteinerRepeatedClass = (GridLayout) findViewById(R.id.container_repeated_class);
        conteinerRepeated = (GridLayout) findViewById(R.id.container_repeated);
        conteinerNotification = (GridLayout) findViewById(R.id.container_notification);
        containerSetTime = (LinearLayout) findViewById(R.id.container_set_time);



        //spinner for class type
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.tasks_class_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        taskClass.setAdapter(adapter);


        //spinner for class repeated type
        ArrayAdapter<CharSequence> adapterClassRepeat = ArrayAdapter.createFromResource(this,
                R.array.period_array, android.R.layout.simple_spinner_item);
        adapterClassRepeat.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        taskClassRepeat.setAdapter(adapterClassRepeat);


        //set visible params for view elements
        commonTaskChoise(false);



        //setting listeners
        repeated.setOnCheckedChangeListener(repeatedListener());
        taskClass.setOnItemSelectedListener(classTaskListener());
        taskClassRepeat.setOnItemSelectedListener(repeatedClassListener());



    }

    // TODO добавить класс задач будильник
    public boolean validation(){
        if(title.getText().toString().isEmpty()){
            Toast.makeText(this, "Не указанно название задачи!", Toast.LENGTH_LONG).show();
            return false;
        }
        if(title.getText().toString().length() > 50 ){
            Toast.makeText(this, "Слишком длинное название задачи!", Toast.LENGTH_LONG).show();
            return false;
        }
        if(description.getText().toString().length() > 300){
            Toast.makeText(this, "Описание задачи слишком большое!", Toast.LENGTH_LONG).show();
            return false;
        }
        if(startTime.compareTo(new GregorianCalendar()) < 0){
            Toast.makeText(this, "Некорректно указана дата или время!", Toast.LENGTH_LONG).show();
            return false;
        }
        if(repeated.isChecked() && getNumberOfRepeate().size() < 1){
            Toast.makeText(this, "Выберите дни повторения", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }


    //TODO добавить фильтрый по классам задач
    //TODO
    //TODO добавить в БД УВЕДОМЛЕНИЕ!!!!звуковое!!!

    /**
     * setTime need for setting time of task.
     * For repeated set: Date/time
     * For unrepeated set : date
     * @param view
     */
    public void setTime(View view) {
        DialogFragment timeDialog;
        DialogFragment dateDialog;

        int caseOfTimne = switcher();
        if(caseOfTimne == 0) {
            dateDialog = new DatePicker();
            timeDialog = new TimePicker();
            timeDialog.show(getSupportFragmentManager(), "timePicker");
            dateDialog.show(getSupportFragmentManager(), "datePicker");
        } else  if(caseOfTimne == 1) {
            dateDialog = new DatePicker();
            dateDialog.show(getSupportFragmentManager(), "datePicker");
        } else {
            timeDialog = new TimePicker();
            timeDialog.show(getSupportFragmentManager(), "timePicker");
        }
    }

    /**
    * return type of tasks
     * if task like common task - true
     * else - false
     * 0 - show date + time
     * 1 - show date
     * 2 - show time
    * */
    public byte switcher(){
        if(classTask == 1) {
            return 1;
        } else if(repeated.isChecked()) {
            return 2;
        } else {
            return 0;
        }
    }

    public void setDate(GregorianCalendar date, int year, int month, int day){
        date.set(Calendar.YEAR, year);
        date.set(Calendar.MONTH, month);
        date.set(Calendar.DAY_OF_MONTH, day);
        startTime = date;
    }

    public void setTime(GregorianCalendar time, int hour, int min){
        time.set(Calendar.HOUR_OF_DAY , hour);
        time.set(Calendar.MINUTE , min);
        startTime = time;
    }

    public void showDate(GregorianCalendar time, int id){
        time = startTime;
        StringBuilder sb = new StringBuilder();
        if(switcher() == 0 || switcher() == 2) {

            sb.append(time.get(Calendar.HOUR_OF_DAY));
            sb.append(":");
            sb.append(time.get(Calendar.MINUTE) < 10 ? "0" + time.get(Calendar.MINUTE) : time.get(Calendar.MINUTE));
            sb.append(" ");
        }
        if(switcher() == 0 || switcher() == 1) {
            sb.append(time.get(Calendar.DAY_OF_MONTH) < 10 ? "0" + time.get(Calendar.DAY_OF_MONTH) : time.get(Calendar.DAY_OF_MONTH));
            sb.append(".");
            sb.append(time.get(Calendar.MONTH) < 10 ? "0" + time.get(Calendar.MONTH) : time.get(Calendar.MONTH));
            sb.append(".");
            sb.append(time.get(Calendar.YEAR));
        }
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


    //draw methods

    public void drawWeek(LinearLayout view){
        Log.d(logName, "используем drawWeek");
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
            tmp.getLayoutParams().height = tmp.getLayoutParams().WRAP_CONTENT;
            tmp.getLayoutParams().width = tmp.getLayoutParams().WRAP_CONTENT;
            tmp.setTextSize(10* getResources().getDisplayMetrics().density);
            tmp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    weekDaysClick(v);
                }
            });
            tmp.setTextColor(getResources().getColor(R.color.black));
            tmp.setBackgroundColor(getResources().getColor(R.color.white));
            ((LinearLayout.LayoutParams) tmp.getLayoutParams()).weight = 1;
            Log.d(logName, "используем drawWeek - добавляем день недели");
            view.addView(tmp,lParams);
        }

    }

    public void drawMonth(View view){
        
    }
    public void drawYear(View view){

    }


    //Listeners methods

    public AdapterView.OnItemSelectedListener classTaskListener(){
        Log.d(logName, "используем classTaskListener - выбор типа задачи");
        return  new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                classTask = (byte)position;
                cleanHide();
                switch (position){
                    case 0:
                        commonTaskChoise(false);
                        break;
                    case 1:
                        birthday();
                        break;
                    case 2:
                        purchases();
                        break;
                    case 3:
                        sport();
                        break;
                    case 4:
                        notificationTask();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        };
    }


    //todo обязательно исправить обнулять значение класса повторения
    public CompoundButton.OnCheckedChangeListener repeatedListener(){
        Log.d(logName, "используем repeatedListener - выбор повторяемое/неповторяемое");
        return new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    weekMonthYear.removeAllViewsInLayout();
                    drawWeek(weekMonthYear);//todo тут <----
                    weekMonthYear.setVisibility(View.VISIBLE);
                    conteinerRepeatedClass.setVisibility(View.VISIBLE);
                    startTimeHint.setText("Время");
                } else {
                    weekMonthYear.setVisibility(View.GONE);
                    conteinerRepeatedClass.setVisibility(View.GONE);
                }
            }
        };
    }

    public AdapterView.OnItemSelectedListener repeatedClassListener(){
        Log.d(logName, "используем repeatedClassListener - выбор типа повторения ");
        return  new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                weekMonthYear.removeAllViewsInLayout();
                repeatedClass = (byte)position;
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

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }


        };
    }

    public CompoundButton.OnCheckedChangeListener notificationListener(){
        return new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        };
    }


    /**
     * Class of a task methods
     */
    public void commonTaskChoise(boolean a){
        Log.d(logName, "используем commonTaskChoise - выбор обычной задачи ");

        if(a){
            Log.d(logName, "используем commonTaskChoise - повторяемое");
            startTimeHint.setText("Время");
        } else {
            Log.d(logName, "используем commonTaskChoise - неповторяемое");
            repeated.setChecked(false);
            conteinerRepeatedClass.setVisibility(View.GONE);
            weekMonthYear.setVisibility(View.GONE);
            startTimeHint.setText("Дата и время");
        }
    }

    public void cleanHide(){
        conteinerNotification.setVisibility(View.VISIBLE);
        conteinerRepeated.setVisibility(View.VISIBLE);
        containerSetTime.setVisibility(View.VISIBLE);
        conteinerRepeatedClass.setVisibility(View.VISIBLE);
    }


    /**
     * Class of a task methods
     */
    public void birthday(){
        startTimeHint.setText("Дата");
        conteinerRepeated.setVisibility(View.GONE);
        conteinerRepeatedClass.setVisibility(View.GONE);
        weekMonthYear.setVisibility(View.GONE);
    }

    /**
     * Class of a task methods
     */
    public void notificationTask(){
        startTimeHint.setText("Дата и время");
        conteinerNotification.setVisibility(View.GONE);
        conteinerRepeatedClass.setVisibility(View.GONE);
    }

    /**
     * Class of a task methods
     *
     * temporally solution
     */
    public void purchases(){
        conteinerRepeated.setVisibility(View.GONE);
        conteinerRepeatedClass.setVisibility(View.GONE);
        weekMonthYear.setVisibility(View.GONE);
        containerSetTime.setVisibility(View.GONE);

    }

    /**
     * Class of a task methods
     *
     * temporally solution
     */
    public void sport(){
        commonTaskChoise(false);
    }

    public void buttonSuccessful(View v){
        if(validation()){
            Task task = new Task(title.getText().toString(), description.getText().toString(), classTask);
            task.setImportant(important.isChecked());
            if(classTask == 4 ){
                task.setNotification(true);
            } else {
                task.setNotification(notification.isChecked());
            }
            task.setRepeated(repeated.isChecked());
            if(repeated.isChecked()){
                task.setRepeatedClass(repeatedClass);

                task.setRepeatedTime(TaskHelper.parseToRepeatedTime(startTime, repeatedClass, getNumberOfRepeate()));
            } else {
                task.setRepeatedClass((byte)-1);
                task.setRepeatedTime(null);
            }
            task.setActive(true);
            DBActions db = new DBActions(this);
//            db.insert(task);
        } else {
            Toast.makeText(this, "Невалидные значение параметров!", Toast.LENGTH_LONG).show();
        }
    }

    public void buttonCancel(View v){
        this.finish();
    }

    public List<Integer> getNumberOfRepeate(){
        List<Integer> tmp = new LinkedList<>();
        Iterator iterator = days.keySet().iterator();
        while (iterator.hasNext()){
            int tmp2 = (Integer) iterator.next();
            if(days.get(tmp2)){
                tmp.add(tmp2);
            }
        }
        return (tmp.size() != 0)?tmp:null;
    }

}