package com.example.edu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import static com.example.edu.Management_screen.class_name;

public class class_management extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_management);

        class_name.set(0,new adaptor_class("kum","kl","kl"));
    }
}