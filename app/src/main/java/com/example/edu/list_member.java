package com.example.edu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class list_member extends AppCompatActivity {

    public static int counter;
    public static String name;
    TextView nam;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_member);

        nam=findViewById(R.id.list_member);

        final DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("teacher_info").child("counter");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                counter=dataSnapshot.getValue().hashCode();

                Toast.makeText(list_member.this,String.valueOf(counter),Toast.LENGTH_LONG).show();

                for (int x=0;x<counter;x++)
                {
                    DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference().child("teacher_info").child(String.valueOf(x)).child("user").child("user_name");

                    databaseReference1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            name= name+dataSnapshot.getValue().toString();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                nam.setText(name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
