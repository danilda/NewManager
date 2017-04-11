package com.example.danil.newmanager.control;

import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.danil.newmanager.R;
import com.example.danil.newmanager.model.DBActions;
import com.example.danil.newmanager.model.Task;
import com.example.danil.newmanager.model.TaskHelper;
import com.example.danil.newmanager.view.fragment.DatePicker;
import com.example.danil.newmanager.view.fragment.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class UpdateTasks extends TaskController implements ActionsTaskForDatePicker {
    Task currentTask;
    DBActions db;
    public byte classTask;
    SwitchCompat active;
    private final static String LOG_NAME = "UpdateTasks";


    public byte getClassTask() {
        return classTask;
    }

    public boolean isRepeated(){
        return currentTask.isRepeated();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_tasks);

        db = DBActions.getInstans(this);
        Intent intent = getIntent();
        currentTask = db.getTaskBeID(intent.getLongExtra("TaskId", -1));
        if(currentTask == null)
            this.finish();

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        init();
    }

    private void init(){
        initConstFields();
        if(currentTask.isRepeated()){
            initRepeatedFields();
        } else {
            initUnrepeatedFields();
        }
    }

    public void initConstFields(){
        title = (EditText) findViewById(R.id.title_update);
        Log.d(LOG_NAME, "initConstFields title " + title);
        description = (EditText) findViewById(R.id.description_update);
        active = (SwitchCompat)  findViewById(R.id.active_update);
        important = (SwitchCompat) findViewById(R.id.important_update);
        notification = (SwitchCompat)  findViewById(R.id.notification_update);
        weekMonthYear = (LinearLayout) findViewById(R.id.week_month_year_update);
        startTimeHint = (TextView) findViewById(R.id.start_time_hint_update);
        conteinerNotification = (GridLayout) findViewById(R.id.container_notification_update);
        containerSetTime = (LinearLayout) findViewById(R.id.container_set_time_update);
        classTask = currentTask.getRepeatedClass();
        startTime = currentTask.getTime();

        Log.d(LOG_NAME, "initConstFields title " + title);
        title.setText(currentTask.getTitle());
        description.setText(currentTask.getDescription());
        active.setChecked(currentTask.isActive());
        important.setChecked(currentTask.isImportant());
        notification.setChecked(currentTask.isNotification());
    }

    public void initRepeatedFields(){
        setForHintRepeatedDate();
        String[] tmp = currentTask.getRepeatedTime().replace("|", " ").split(" ");
        if(currentTask.getRepeatedClass() == 0){
            initWeek(tmp);
        }
        if(currentTask.getRepeatedClass() == 1){

        }

    }

    public void initUnrepeatedFields(){
        SimpleDateFormat sdf = new SimpleDateFormat();
        startTimeHint.setText(sdf.format(currentTask.getTime().getTime()));
    }

    public void setForHintRepeatedDate(){
        String date = "";
        int mins = currentTask.getTime().get(GregorianCalendar.MINUTE);
        int hours = currentTask.getTime().get(GregorianCalendar.HOUR);
        date = (hours < 10? ("0")+hours : hours) + ":" + (mins < 10? ("0")+mins : mins);
        startTimeHint.setText(date);
    }

    public void initWeek(String[] days){
        drawWeek(weekMonthYear);
        for (String y : days) {
            Log.d(LOG_NAME, y + " - initWeek element of array of days");
            if (y.length() > 0 && !y.equals("|") )
                weekDaysClick((findViewById(daysId[y.charAt(0) - 97])));
        }
    }

    public void setTime(View view) {
        DialogFragment timeDialog;
        DialogFragment dateDialog;

        byte caseOfTimne = switcher(currentTask.getRepeatedClass(), currentTask.isRepeated());
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

    public void showDate(GregorianCalendar time, int id){
        time = startTime;
        StringBuilder sb = new StringBuilder();
        if(switcher(classTask, currentTask.isRepeated()) == 0 || switcher(classTask, currentTask.isRepeated()) == 2) {

            sb.append(time.get(Calendar.HOUR_OF_DAY));
            sb.append(":");
            sb.append(time.get(Calendar.MINUTE) < 10 ? "0" + time.get(Calendar.MINUTE) : time.get(Calendar.MINUTE));
            sb.append(" ");
        }
        if(switcher(classTask, currentTask.isRepeated()) == 0 || switcher(classTask, currentTask.isRepeated()) == 1) {
            sb.append(time.get(Calendar.DAY_OF_MONTH) < 10 ? "0" + time.get(Calendar.DAY_OF_MONTH) : time.get(Calendar.DAY_OF_MONTH));
            sb.append(".");
            sb.append(time.get(Calendar.MONTH) < 10 ? "0" + time.get(Calendar.MONTH) : time.get(Calendar.MONTH));
            sb.append(".");
            sb.append(time.get(Calendar.YEAR));
        }
        ((TextView)findViewById(id)).setText(sb.toString());
    }

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
        if(currentTask.isRepeated() && getNumberOfRepeate().size() < 1){
            Toast.makeText(this, "Выберите дни повторения", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public void buttonSuccessful(View v){
        if(validation()){
            currentTask.setTitle(title.getText().toString());
            currentTask.setDescription(description.getText().toString());
            currentTask.setImportant(important.isChecked());
            currentTask.setNotification(notification.isChecked());

            if(currentTask.isRepeated()){
                currentTask.setRepeatedClass(repeatedClass);
                currentTask.setRepeatedTime(TaskHelper.parseToRepeatedTime(repeatedClass, getNumberOfRepeate()));

            } else {
                currentTask.setRepeatedClass((byte)-1);
                currentTask.setRepeatedTime(null);
            }
            currentTask.setTime(startTime);
            currentTask.setActive(true);
            DBActions db = DBActions.getInstans(this);
            db.updateTask(currentTask);
            try {
                TaskHelper.updateRepeadetMap(this, db.getListTasks());
            } catch (ParseException e){
                Log.d(LOG_NAME, "ParseException в апдейте");
            }
            this.finish();
        } else {
            Toast.makeText(this, "Невалидные значение параметров!", Toast.LENGTH_LONG).show();
        }
    }

    public void buttonCancel(View v){
        this.finish();
    }

}
