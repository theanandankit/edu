package com.example.edu;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class member_sgmid {

    public ArrayList<String> spinner_setter()
    {
        final ArrayList<String> name_list=new ArrayList<>();
        final ArrayList<String> sgmid_list=new ArrayList<>();

        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("teacher_info");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot: dataSnapshot.getChildren())
                {
                    sgmid_list.add(dataSnapshot.getValue().toString());
                    Teacher teacher=postSnapshot.getValue(Teacher.class);

                    name_list.add(teacher.getUser_name()+" ("+dataSnapshot.getValue().toString()+")");
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return name_list;

    }

}
 class Teacher
{
    private String user_name;
    private String batch;
    private String email;
    private String contact_no;
    private String actual_day;
    private String extra_day;
    private String present;
    private String type;


    public Teacher() {}
    public Teacher(String name,String email,String batch,String contact_no,String actual_day,String extra_day,String present,String type)
    {
        this.user_name=name;
        this.batch=batch;
        this.email=email;
        this.contact_no=contact_no;
        this.actual_day=actual_day;
        this.extra_day=extra_day;
        this.present=present;
        this.type=type;
    }


    public String getUser_name() {
        return user_name;
    }

    public String getEmail() {
        return email;
    }

    public String getContact_no() {
        return contact_no;
    }

    public String getActual_day() {
        return actual_day;
    }

    public String getExtra_day() {
        return extra_day;
    }

    public String getBatch() {
        return batch;
    }

    public String getPresent() {
        return present;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setContact_no(String contact_no) {
        this.contact_no = contact_no;
    }

    public void setActual_day(String actual_day) {
        this.actual_day = actual_day;
    }

    public void setExtra_day(String extra_day) {
        this.extra_day = extra_day;
    }

    public void setPresent(String present) {
        this.present = present;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
