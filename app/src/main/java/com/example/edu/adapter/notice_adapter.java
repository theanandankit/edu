package com.example.edu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.edu.R;
import com.example.edu.action_screen;
import com.example.edu.action_screen.notice;
import com.example.edu.schedule_view;

import java.util.ArrayList;

public class notice_adapter extends ArrayAdapter<notice> {

    ArrayList<notice> schedule_list = new ArrayList<>();

    public notice_adapter(Context value_context, int textViewResourceId, ArrayList<notice> object)
    {

        super(value_context,textViewResourceId,object);

        schedule_list=object;

    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.notice_view, null);
        TextView notice_date = (TextView) v.findViewById(R.id.notice_text);
        TextView  notice_message= (TextView) v.findViewById(R.id.notice_message);
        notice_date.setText(schedule_list.get(position).getDate());
        notice_message.setText(schedule_list.get(position).getMessage());
        return v;
    }
}
