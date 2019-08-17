package com.example.edu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class Management_screen extends AppCompatActivity {

    public static ArrayList<TextView> name_teacher = new ArrayList<>();
    public static ArrayList<TextView> name_batch = new ArrayList<>();
    public static ArrayList<TextView> comment = new ArrayList<>();
    public static ArrayList<CardView> card = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management_screen);


    }
}
