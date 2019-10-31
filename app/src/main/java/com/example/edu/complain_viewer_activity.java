package com.example.edu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.edu.adapter.list_adapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.edu.admin_management.member_list;

public class complain_viewer_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complain_viewer_activity);

        final ArrayList<String> comlain=new ArrayList<>();
        final RecyclerView recyclerView=findViewById(R.id.complain_recycle_view);

        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("complain");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dsp : dataSnapshot.getChildren())
                {
                    comlain.add(String.valueOf(dsp.getValue()));
                }

                recyclerView.setLayoutManager(new LinearLayoutManager(complain_viewer_activity.this));

                list_adapter adapter = new list_adapter(complain_viewer_activity.this,comlain);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
