package com.example.edu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class myadaptor extends ArrayAdapter<adaptor_class> {

    ArrayList<adaptor_class> list_class= new ArrayList<>();

    public myadaptor(Context context, int resource) {
        super(context, resource);

    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.class_list, null);
        TextView teacher = (TextView) v.findViewById(R.id.tech1);
        teacher.setText(list_class.get(position).getTeacher_name());
        TextView batch = (TextView) v.findViewById(R.id.batch1);
        batch.setText(list_class.get(position).getTeacher_name());
        TextView comment = (TextView) v.findViewById(R.id.com1);
        comment.setText(list_class.get(position).getTeacher_name());
        return v;

    }
}
