package com.example.edu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.edu.adapter.schedule_adapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class date_monitoring extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_monitoring);

        final Spinner spinner=findViewById(R.id.date_monitoring_spinner);

        final ArrayList<String> dates=new ArrayList<>();
        final ArrayList<schedule_view.Item> teacher_list=new ArrayList<>();
        final ListView listView=findViewById(R.id.date_monitoring_listview);
        final TextView management1=findViewById(R.id.date_monitoring_management_name1);
        final TextView management2=findViewById(R.id.date_monitoring_management_name1);


        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("Dates");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot postsnapshot:dataSnapshot.getChildren())
                {
                    dates.add(postsnapshot.getKey());
                    ArrayAdapter aaa = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item,dates);
                    aaa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(aaa);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference().child(parent.getItemAtPosition(position).toString()).child("Teacher-Attendance");

                Toast.makeText(getApplicationContext(),"jguh",Toast.LENGTH_LONG).show();

                databaseReference1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(!teacher_list.isEmpty())
                        {
                            teacher_list.clear();
                        }


                 for (DataSnapshot postSnapshot :dataSnapshot.getChildren()) {

                     Toast.makeText(getApplicationContext(), "jguijhh", Toast.LENGTH_LONG).show();

                     if (!postSnapshot.getKey().equals("set")) {
                         adaptor_class info = postSnapshot.getValue(adaptor_class.class);
                         teacher_list.add(new schedule_view.Item(postSnapshot.getKey(), info.getTeacher_name() + "\n(" + info.getBatch() + ")", info.getComment()));

                     }
                 }
                        schedule_adapter myAdapter=new schedule_adapter(date_monitoring.this,R.layout.schedule_list_adapter,teacher_list);
                        listView.setAdapter(myAdapter);



                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
