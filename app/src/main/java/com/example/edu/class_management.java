package com.example.edu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Switch;

import static com.example.edu.Management_screen.class_name;

public class class_management extends AppCompatActivity {

    CardView subtitute;
    Spinner spinner;
    String name[]=new String[]{"Amit","Ankit","Kamal","Divyansh","Shivam","Aayush"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_management);

        subtitute=findViewById(R.id.subtitute);
        subtitute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch (view.getId())
                {
                    case R.id.subtitute:
                        AlertDialog.Builder builder=new AlertDialog.Builder(class_management.this);
                        View mview=getLayoutInflater().inflate(R.layout.subtitute_popup,null);
                        builder.setView(mview);
                        AlertDialog dialog=builder.create();
                        dialog.show();
                }
            }
        });

    }
}