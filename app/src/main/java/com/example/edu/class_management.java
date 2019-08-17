package com.example.edu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class class_management extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_management);

        TextView s=findViewById(R.id.s);

        Intent myintent=getIntent();

        int name=myintent.getIntExtra("Teacher_name",000);

        TextView ss=findViewById(name);

        ss.setText("Ankit");

    }
}