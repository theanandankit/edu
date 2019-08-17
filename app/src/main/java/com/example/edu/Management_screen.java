package com.example.edu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Management_screen extends AppCompatActivity {


    ArrayList<adaptor_class> class_name=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management_screen);

        ListView listView= findViewById(R.id.list_id);

        class_name.add(new adaptor_class("Ankit","2018IMG","every thing ok"));
        class_name.add(new adaptor_class("Ankit","2018IMG","every thing ok"));


        myadaptor myad=new myadaptor(this,R.layout.class_list,class_name);
        listView.setAdapter(myad);
    }
}
