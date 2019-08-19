package com.example.edu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Management_screen extends AppCompatActivity {


    public static ArrayList<adaptor_class> class_name=new ArrayList<>();
    public static ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management_screen);

        listView= findViewById(R.id.list_id);
        if(class_name.isEmpty()) {

            class_name.add(0, new adaptor_class("ankit", "2018", "ok"));
            class_name.add(1, new adaptor_class("Ankit", "2018IMG", "every thing ok"));
        }

        myadaptor myad=new myadaptor(this,R.layout.class_list,class_name);
        listView.setAdapter(myad);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent k=new Intent(Management_screen.this,class_management.class);

                        k.putExtra("index_of",i);
                        startActivity(k);
            }
        });
    }
    @Override
    public void onRestart() {
        super.onRestart();

        myadaptor myad=new myadaptor(this,R.layout.class_list,class_name);
        listView.setAdapter(myad);
    }
}
