package com.example.danil.newmanager.view.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.danil.newmanager.R;
import com.example.danil.newmanager.control.Main;
import com.example.danil.newmanager.model.Task;

import java.util.ArrayList;

/**
 * Created by danil on 25.03.2017.
 */

public class ItemAdapter extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<TaskItemContent> arr;

    public  ItemAdapter(Context context, ArrayList<TaskItemContent> tasks) {
        ctx = context;
        arr = tasks;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return arr.size();
    }

    @Override
    public Object getItem(int position) {
        return arr.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.task_item, parent, false);
        }
        TaskItemContent p = getTaskItem(position);
        final Task currentTask = p.task;
        ((TextView) view.findViewById(R.id.item_title)).setText(p.title);
        ((TextView) view.findViewById(R.id.item_date)).setText(p.date);
        ((ImageView) view.findViewById(R.id.item_img)).setImageResource(p.img_id);
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ((Main)ctx).updateTask(currentTask);
                return true;
            }
        });
        return view;
    }
    TaskItemContent getTaskItem(int position) {
        return ((TaskItemContent) getItem(position));
    }


}
