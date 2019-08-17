package com.example.edu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class Management_screen extends AppCompatActivity implements View.OnClickListener {

    public static ArrayList<TextView> name_teacher = new ArrayList<>();
    public static ArrayList<TextView> name_batch = new ArrayList<>();
    public static ArrayList<TextView> comment = new ArrayList<>();
    public static ArrayList<CardView> card = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management_screen);

        name_teacher.add(0, (TextView) findViewById(R.id.tech1));
        name_teacher.add(1, (TextView) findViewById(R.id.tech3));
        name_teacher.add(2, (TextView) findViewById(R.id.tech5));
        name_teacher.add(3, (TextView) findViewById(R.id.tech6));
        name_teacher.add(4, (TextView) findViewById(R.id.tech7));
        name_teacher.add(5, (TextView) findViewById(R.id.tech8));
        name_teacher.add(6, (TextView) findViewById(R.id.tech9));
        name_teacher.add(7, (TextView) findViewById(R.id.tech10));
        name_teacher.add(8, (TextView) findViewById(R.id.tech11));
        name_teacher.add(9, (TextView) findViewById(R.id.tech12));

        name_batch.add(0, (TextView) findViewById(R.id.batch1));
        name_batch.add(1, (TextView) findViewById(R.id.batch3));
        name_batch.add(2, (TextView) findViewById(R.id.batch5));
        name_batch.add(3, (TextView) findViewById(R.id.batch6));
        name_batch.add(4, (TextView) findViewById(R.id.batch7));
        name_batch.add(5, (TextView) findViewById(R.id.batch8));
        name_batch.add(6, (TextView) findViewById(R.id.batch9));
        name_batch.add(7, (TextView) findViewById(R.id.batch10));
        name_batch.add(8, (TextView) findViewById(R.id.batch11));
        name_batch.add(9, (TextView) findViewById(R.id.batch12));

        comment.add(0, (TextView) findViewById(R.id.com1));
        comment.add(1, (TextView) findViewById(R.id.com2));
        comment.add(2, (TextView) findViewById(R.id.com5));
        comment.add(3, (TextView) findViewById(R.id.com6));
        comment.add(4, (TextView) findViewById(R.id.com7));
        comment.add(5, (TextView) findViewById(R.id.com8));
        comment.add(6, (TextView) findViewById(R.id.com9));
        comment.add(7, (TextView) findViewById(R.id.com10));
        comment.add(8, (TextView) findViewById(R.id.com11));
        comment.add(9, (TextView) findViewById(R.id.com12));

        card.add(0, (CardView) findViewById(R.id.card1));
        card.add(1, (CardView) findViewById(R.id.card3));
        card.add(2, (CardView) findViewById(R.id.card5));
        card.add(3, (CardView) findViewById(R.id.card6));
        card.add(4, (CardView) findViewById(R.id.card7));
        card.add(5, (CardView) findViewById(R.id.card8));
        card.add(6, (CardView) findViewById(R.id.card9));
        card.add(7, (CardView) findViewById(R.id.card10));
        card.add(8, (CardView) findViewById(R.id.card11));
        card.add(9, (CardView) findViewById(R.id.card12));

        card.get(0).setOnClickListener(this);
        card.get(1).setOnClickListener(this);
        card.get(2).setOnClickListener(this);
        card.get(3).setOnClickListener(this);
        card.get(4).setOnClickListener(this);
        card.get(5).setOnClickListener(this);
        card.get(6).setOnClickListener(this);
        card.get(7).setOnClickListener(this);
        card.get(8).setOnClickListener(this);
        card.get(9).setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {

        Intent k=new Intent(this,class_management.class);

          switch (view.getId())
          {
              case R.id.card1:
                  k.putExtra("teacher_name",R.id.tech1);
                  k.putExtra("batch_name",R.id.batch1);
                  k.putExtra("comment",R.id.com1);
                  startActivity(k);
          }
    }
}
