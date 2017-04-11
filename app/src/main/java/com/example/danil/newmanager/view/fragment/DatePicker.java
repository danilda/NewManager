package com.example.danil.newmanager.view.fragment;

/**
 * Created by danil on 25.02.2017.
 */

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.Button;

import com.example.danil.newmanager.control.ActionsTaskForDatePicker;
import com.example.danil.newmanager.R;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class DatePicker extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    private GregorianCalendar date;
    private ActionsTaskForDatePicker addTasks;

    public GregorianCalendar getDate() {
        return date;
    }

    public void setDate(GregorianCalendar date) {
        this.date = date;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // определяем текущую дату
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // создаем DatePickerDialog и возвращаем его
        Dialog picker = new DatePickerDialog(getActivity(), this,
                year, month, day);
        picker.setTitle(getResources().getString(R.string.choose_date));

        return picker;
    }
    @Override
    public void onStart() {
        super.onStart();
        // добавляем кастомный текст для кнопки
        Button nButton =  ((AlertDialog) getDialog())
                .getButton(DialogInterface.BUTTON_POSITIVE);
        nButton.setText(getResources().getString(R.string.ready));
    }

    @Override
    public void onDateSet(android.widget.DatePicker datePicker, int year,
                          int month, int day) {
        addTasks = (ActionsTaskForDatePicker) getActivity();
        addTasks.setDate(addTasks.getStartTime(), year, month, day);
        if(addTasks.switcher(addTasks.getClassTask(), addTasks.isRepeated()) == 1)
            addTasks.showDate(addTasks.getStartTime(),
                    addTasks.getClass().equals("AddTasks")?R.id.start_time_hint:R.id.start_time_hint_update);
    }
}