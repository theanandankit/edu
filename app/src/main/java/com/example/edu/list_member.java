package com.example.edu;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;


import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
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
    ImageButton b;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_member);
        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.general_actionbar);
        getSupportActionBar().setElevation(10);
        View view = getSupportActionBar().getCustomView();
        view.findViewById(R.id.logout).setVisibility(View.INVISIBLE);
        view.findViewById(R.id.logout).setEnabled(false);
        b=(ImageButton)view.findViewById(R.id.home);

        progressBar=findViewById(R.id.list_progress);
        progressBar.setProgress(10);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(list_member.this,action_screen.class);
                startActivity(i);
            }
        });
        TextView textView=(TextView)view.findViewById(R.id.tab_name);
        textView.setText("Members");
        Typeface typeface= ResourcesCompat.getFont(getApplicationContext(),R.font.pacifico);
        textView.setTextColor(getResources().getColor(R.color.white));
        textView.setTypeface(typeface);

        final ListView listView1 = findViewById(R.id.recyclerView);

        final ArrayList<list_member_class> member_list = new ArrayList<>();

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("teacher_info");

        Toast.makeText(getApplicationContext(), "gyh", Toast.LENGTH_LONG).show();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                progressBar.setProgress(50);

                for (final DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    if (!postSnapshot.getKey().equals("counter")) {

                        DatabaseReference reference = databaseReference.child(postSnapshot.getKey());

                        reference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                if(progressBar.getProgress()==50)
                                {
                                    progressBar.setProgress(80);
                                }
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

                progressBar.setVisibility(View.INVISIBLE);
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
                TextView name=view.findViewById(R.id.member_list_name);

                call_permission(name.getText().toString(),textView.getText().toString());

               /* Toast.makeText(getApplicationContext(), textView.getText().toString(), Toast.LENGTH_LONG).show();

                Intent i = new Intent(Intent.ACTION_CALL, Uri.fromParts("tel",textView.getText().toString(),null));


                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(list_member.this, new String[]{Manifest.permission.CALL_PHONE},1);
                }
                else
                {
                    startActivity(i);
                }*/
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
    public void call_permission(String name, final String number)
    {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.phone_popup);
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        Button cancel=dialog.findViewById(R.id.call_cancel);
        Button call=dialog.findViewById(R.id.call_confirm);
        TextView textView=dialog.findViewById(R.id.call_text);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });

        textView.setText("Do you want to call "+name+" ??");

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(), number, Toast.LENGTH_LONG).show();

                Intent i = new Intent(Intent.ACTION_CALL, Uri.fromParts("tel",number,null));


                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(list_member.this, new String[]{Manifest.permission.CALL_PHONE},1);
                }
                else
                {
                    startActivity(i);
                }

                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setAttributes(lp);

            }
    }

