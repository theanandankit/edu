package com.example.edu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class member_monitoring extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_monitoring);

        Spinner spinner=findViewById(R.id.member_monitoring_spinner);

        ArrayAdapter aaa = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item,admin_management.member_list);
        aaa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(aaa);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                final String id=admin_management.member_list_object.get_uid(adapterView.getItemAtPosition(i).toString());

                DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("teacher_info").child(id);

                databaseReference.addValueEventListener(new ValueEventListener() {


                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        Teacher_management.Teacher monitoring_list=dataSnapshot.getValue(Teacher_management.Teacher.class);

                        TextView name=findViewById(R.id.member_management_teacher_user_name);
                        TextView batch=findViewById(R.id.member_management_teacher_user_batch);
                        TextView contact=findViewById(R.id.member_management_teacher_user_phone);
                        TextView email=findViewById(R.id.member_management_teacher_user_email);
                        TextView total=findViewById(R.id.member_management_total);
                        TextView present=findViewById(R.id.member_management_present);
                        TextView extra=findViewById(R.id.member_management_extra);

                        name.setText(monitoring_list.getUser_name());
                        batch.setText(monitoring_list.getBatch());
                        contact.setText(monitoring_list.getContact_no());
                        email.setText(monitoring_list.getEmail());

                        total.setText(monitoring_list.getActual_day());
                        present.setText(monitoring_list.getPresent());
                        extra.setText(monitoring_list.getExtra_day());

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                    });

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
