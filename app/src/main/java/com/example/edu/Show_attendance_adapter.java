package com.example.edu;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;

import java.util.ArrayList;

public class Show_attendance_adapter extends ArrayAdapter {
    private ArrayList<adaptor_class> list;
    Context context;
    private static class ViewHolder {
        TextView Name;
        TextView uid;
        TextView batch;
        TextView class1;
        TextView comment;

    }
    public Show_attendance_adapter(ArrayList objects, Context context) {
        super(context,R.layout.show_attendance_item,objects);
        this.context=context;
        list = objects;

    }
    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public adaptor_class getItem(int position) {
        return list.get(position);
    }
    @Override
    public View getView(int position, View convertView,  ViewGroup parent) {

        Show_attendance_adapter.ViewHolder viewHolder;
        final View result;

        if (convertView == null) {
            viewHolder = new Show_attendance_adapter.ViewHolder();
            //Typeface typeface = getResources().getFont(R.font.myfont);
            //or to support all versions use
            // Typeface typeface = ResourcesCompat.getFont(context, R.font.aclonica);

            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.class_list, parent, false);
            viewHolder.Name = (TextView) convertView.findViewById(R.id.teach1);
            Typeface typeface=ResourcesCompat.getFont(context,R.font.aclonica);
            viewHolder.Name.setTypeface(typeface);
            viewHolder.Name.setTextColor(Color.BLACK);
            Typeface typeface1=ResourcesCompat.getFont(context,R.font.above);

            viewHolder.uid = (TextView)convertView.findViewById(R.id.Uid);
            viewHolder.batch=(TextView)convertView.findViewById(R.id.batch);
            viewHolder.class1=(TextView)convertView.findViewById(R.id.classes);
            viewHolder.comment=(TextView)convertView.findViewById(R.id.com1);
            viewHolder.comment.setTypeface(typeface1);
            viewHolder.comment.setTextColor(Color.BLACK);
            //viewHolder.substitute=(EditText)convertView.findViewById(R.id.substitute);

            result=convertView;
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (Show_attendance_adapter.ViewHolder) convertView.getTag();
            result=convertView;
        }

        adaptor_class item = getItem(position);


        viewHolder.Name.setText(item.teacher_name);
        viewHolder.uid.setText(item.uid);
        viewHolder.batch.setText(item.batch);
        viewHolder.class1.setText(item.class1);
        viewHolder.comment.setText(item.comment);
        /** if(item.checked)
         viewHolder.substitute.setVisibility(View.INVISIBLE);
         else
         viewHolder.substitute.setVisibility(View.VISIBLE);*/


        return result;
    }

}
