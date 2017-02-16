package com.example.danil.newmanager.fragment;

/**
 * Created by danil on 11.02.2017.
 */


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import com.example.danil.newmanager.R;


public class Fragment1 extends Fragment {
    CalendarView calendarView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment1, container, false);
        return rootView;

    }
}