package com.example.edu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.edu.R;
import com.example.edu.schedule_view;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class schedule_adapter extends ArrayAdapter<schedule_view.Item> {

    ArrayList<schedule_view.Item> schedule_list = new ArrayList<>();

    public schedule_adapter(Context value_context, int textViewResourceId, ArrayList<schedule_view.Item> object)
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
        v = inflater.inflate(R.layout.schedule_list_adapter, null);
        TextView list_class = (TextView) v.findViewById(R.id.schedule_adapter_class);
        TextView  list_name= (TextView) v.findViewById(R.id.schedule_adapter_name);
        TextView list_subject = (TextView) v.findViewById(R.id.schedule_adapter_subject);
        list_class.setText(schedule_list.get(position).getClass_name());
        list_name.setText(schedule_list.get(position).getStudent_name());
        list_subject.setText(schedule_list.get(position).getSubject_name());
        return v;
    }
}

