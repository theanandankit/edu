package com.example.edu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class schedule_management extends AppCompatActivity {


    DatabaseReference databaseReference;
    ArrayList<String> teacher_name=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_management);

        Spinner spinner=findViewById(R.id.spinner2);
        Button button=findViewById(R.id.check_button);



        for (int a=1;a<13;a++) {

            if (!(a==2||a==4)) {
                databaseReference = FirebaseDatabase.getInstance().getReference().child("schedule_teacher").child("monday").child(String.valueOf(a));
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                     teacher_name.add(dataSnapshot.getValue().toString());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
            ArrayAdapter aa=new ArrayAdapter(schedule_management.this,android.R.layout.simple_spinner_item,teacher_name);
            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(aa);
        }
    }
}
