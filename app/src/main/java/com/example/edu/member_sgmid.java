package com.example.edu;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class member_sgmid {

    member_sgmid()
    {

    }

    public ArrayList<String> spinner_setter()
    {
        final ArrayList<String> name_list=new ArrayList<>();
        final ArrayList<String> sgmid_list=new ArrayList<>();

        final DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("teacher_info");


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (final DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    if (!postSnapshot.getKey().equals("counter")) {

                        sgmid_list.add(postSnapshot.getKey());
                        DatabaseReference reference = databaseReference.child(postSnapshot.getKey());
                        reference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                System.out.println(snapshot.getKey());

                                Teacher_management.Teacher teacher=snapshot.getValue(Teacher_management.Teacher.class);
                                name_list.add(teacher.getUser_name()+" "+postSnapshot.getKey());

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return name_list;
    }

    public String get_uid(String name)
    {
       int i=name.lastIndexOf(" ");
       String n=name.substring(0,i);
       String uid=name.substring(i+1);
       return uid;
    }
}