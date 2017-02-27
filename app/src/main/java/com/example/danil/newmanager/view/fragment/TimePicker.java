package com.example.danil.newmanager.view.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.*;
import android.widget.DatePicker;

import com.example.danil.newmanager.R;

import java.util.Calendar;

/**
 * Created by danil on 26.02.2017.
 */

public class TimePicker extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener{
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Dialog picker = new TimePickerDialog(getActivity(), this, 0,0, true );
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
    public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {
        TextView tv = (TextView) getActivity().findViewById(R.id.start_time_hint);
        tv.setText(tv.getText().toString() + " "+ hourOfDay + ":" + (minute==0?"00":minute) );
    }
}
