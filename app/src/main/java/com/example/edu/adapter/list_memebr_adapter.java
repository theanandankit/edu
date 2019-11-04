package com.example.edu.adapter;
import com.example.edu.R;
import com.example.edu.list_member;
import com.example.edu.schedule_view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class list_memebr_adapter extends ArrayAdapter<list_member.list_member_class> {

    ArrayList<list_member.list_member_class> list_member_list = new ArrayList<>();

    public list_memebr_adapter(Context value_context, int textViewResourceId, ArrayList<list_member.list_member_class> object)
    {

        super(value_context,textViewResourceId,object);

        list_member_list=object;

    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.list_member_adapter, null);
        TextView list_email = (TextView) v.findViewById(R.id.member_list_email);
        TextView  list_name= (TextView) v.findViewById(R.id.member_list_name);
        TextView list_roll = (TextView) v.findViewById(R.id.member_list_roll);
        TextView list_contact=v.findViewById(R.id.member_list_contact);
        list_name.setText(list_member_list.get(position).getMember_name());
        list_email.setText(list_member_list.get(position).getMember_email());
        list_roll.setText(list_member_list.get(position).getMember_batch());
        list_contact.setText(list_member_list.get(position).getMember_phone_no());
        return v;
    }
}
