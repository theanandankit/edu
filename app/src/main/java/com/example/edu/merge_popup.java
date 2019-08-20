package com.example.edu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class merge_popup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merge_popup);

        String class_name[]={"1","2","3","4","5","6","7","8","9","10","11","12"};

        Spinner spinner=findViewById(R.id.spinner1);

        ArrayAdapter aa=new ArrayAdapter(merge_popup.this,android.R.layout.simple_spinner_item,class_name);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(aa);


    }
}
