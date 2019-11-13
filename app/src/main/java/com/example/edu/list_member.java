package com.example.edu;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.edu.adapter.list_memebr_adapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class list_member extends AppCompatActivity {

    public static int counter;
    public static String name;
    TextView nam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_member);

        final ListView listView1 = findViewById(R.id.recyclerView);

        final ArrayList<list_member_class> member_list = new ArrayList<>();

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("teacher_info");

        Toast.makeText(getApplicationContext(), "gyh", Toast.LENGTH_LONG).show();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {satus

                for (final DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    if (!postSnapshot.getKey().equals("counter")) {

                        DatabaseReference reference = databaseReference.child(postSnapshot.getKey());

                        reference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                System.out.println(snapshot.getKey());

                                Teacher_management.Teacher teacher = snapshot.getValue(Teacher_management.Teacher.class);
                                member_list.add(new list_member_class(teacher.getUser_name(), teacher.getBatch(), teacher.getEmail(), teacher.getContact_no()));
                                list_memebr_adapter myAdapter = new list_memebr_adapter(list_member.this, R.layout.list_member_adapter, member_list);
                                listView1.setAdapter(myAdapter);

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

        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView textView = view.findViewById(R.id.member_list_contact);

                Toast.makeText(getApplicationContext(), textView.getText().toString(), Toast.LENGTH_LONG).show();

                Intent i = new Intent(Intent.ACTION_CALL, Uri.fromParts("tel",textView.getText().toString(),null));


                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(list_member.this, new String[]{Manifest.permission.CALL_PHONE},1);
                }
                else
                {
                    startActivity(i);
                }
           }
       });

    }

    public class list_member_class
    {
        String member_name;
        String member_batch;
        String member_phone_no;

        public list_member_class(String member_name, String member_batch, String member_email,String member_phone_no) {
            this.member_name = member_name;
            this.member_batch = member_batch;
            this.member_email = member_email;
            this.member_phone_no=member_phone_no;
        }

        public String getMember_email() {
            return member_email;
        }

        public void setMember_email(String member_email) {
            this.member_email = member_email;
        }

        String member_email;
        String member_type;
        int member_contact_button;

        public String getMember_name() {
            return member_name;
        }

        public void setMember_name(String member_name) {
            this.member_name = member_name;
        }

        public String getMember_batch() {
            return member_batch;
        }

        public void setMember_batch(String member_batch) {
            this.member_batch = member_batch;
        }

        public String getMember_phone_no() {
            return member_phone_no;
        }

        public void setMember_phone_no(String member_phone_no) {
            this.member_phone_no = member_phone_no;
        }

        public String getMember_type() {
            return member_type;
        }

        public void setMember_type(String member_type) {
            this.member_type = member_type;
        }

        public int getMember_contact_button() {
            return member_contact_button;
        }

        public void setMember_contact_button(int member_contact_button) {
            this.member_contact_button = member_contact_button;
        }
    }
}
