package com.example.edu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class Management_screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management_screen);

        ArrayList<TextView> name_teacher= new ArrayList<>();

        name_teacher.add(0, (TextView) findViewById(R.id.tech1));


        name_teacher.get(0).setText("Ankit kumar");
    }
}
