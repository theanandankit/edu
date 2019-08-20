package com.example.edu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

public class subtitue_popup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subtitue_popup);

        final EditText name_teacher=findViewById(R.id.name);
        final EditText batch_teacher=findViewById(R.id.Batch);

        final CheckBox check_member=findViewById(R.id.check_member);
        final CheckBox check_not_member=findViewById(R.id.check_not_member);

        final String name[]={"Amit","Aayush","Ankit","Divyansh","kamal","Shivam","Vishnu"};

        final Spinner spinner=findViewById(R.id.spinner);

        ArrayAdapter aa=new ArrayAdapter(subtitue_popup.this,android.R.layout.simple_spinner_item,name);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(aa);

        check_member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(check_member.isChecked())
                {
                    spinner.setEnabled(true);
                    name_teacher.setEnabled(false);
                    batch_teacher.setEnabled(false);
                    check_not_member.setChecked(false);
                }
            }
        });
        check_not_member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(check_not_member.isChecked())
                {
                    spinner.setEnabled(false);
                    name_teacher.setEnabled(true);
                    batch_teacher.setEnabled(true);
                    check_member.setChecked(false);
                }
            }
        });
    }
}
