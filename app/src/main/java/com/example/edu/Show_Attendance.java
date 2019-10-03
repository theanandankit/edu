package com.example.edu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Show_Attendance extends AppCompatActivity {
    ListView list;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show__attendance);
        list=(ListView)findViewById(R.id.list);
        Date todaysDate = new Date();
        String date = "";
        DateFormat sdf1 = new SimpleDateFormat("dd-MMM-yyyy");
        date= sdf1.format(todaysDate).toString();
        reference= FirebaseDatabase.getInstance().getReference().child(date);
       for(int a=1;a<=12;a++)
       {
           if(a!=2 || a!=4)
           {
               reference.child(String.valueOf(a)).addValueEventListener(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError databaseError) {

                   }
               });
           }
       }

    }
}
