package com.example.edu;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

import static com.example.edu.action_screen.pref_1;

public class basic_info extends AppCompatActivity  {


    public String s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_info);

        final TextView textView=findViewById(R.id.basic_regular_timing);
        final TextView sunday=findViewById(R.id.basic_sunday_timing);
        final EditText management=findViewById(R.id.basic_management);
        final EditText teacher=findViewById(R.id.basic_teacher);
        final EditText student=findViewById(R.id.basic_student);
        Button button=findViewById(R.id.basic_regular_button);
        Button button1=findViewById(R.id.basic_sunday_button);
        Button button2=findViewById(R.id.basic_final);

        textView.setText(pref_1.getString("regular","---"));
        sunday.setText(pref_1.getString("sunday","---"));
        management.setText(pref_1.getString("management","---"));
        teacher.setText(pref_1.getString("teacher","---"));
        student.setText(pref_1.getString("student","---"));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                set_to_from(textView,"regular");
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                set_to_from(sunday,"sunday");
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child("basic_info").child("management");
                databaseReference.setValue(management.getText().toString());

                DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference().child("basic_info").child("student");
                databaseReference1.setValue(student.getText().toString());

                DatabaseReference databaseReference2=FirebaseDatabase.getInstance().getReference().child("basic_info").child("total_teacher");
                databaseReference2.setValue(teacher.getText().toString());
            }
        });
    }

    public void set_to_from(final TextView t, final String a)
    {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(basic_info.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                s= selectedHour + ":" + selectedMinute;

                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(basic_info.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        s=s+" To "+ selectedHour + ":" + selectedMinute;

                        t.setText(s);

                        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("basic_info").child(a);
                        databaseReference.setValue(s);
                    }
                }, hour, minute, false);//Yes 24 hour time
                mTimePicker.setTitle("End on");
                mTimePicker.show();
            }
        }, hour, minute, false);//Yes 24 hour time
        mTimePicker.setTitle("Starting From");
        mTimePicker.show();
    }
}