package com.example.edu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;

import android.os.Bundle;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class schedule_management extends AppCompatActivity {

    CardView cardView1,cardView3,cardView5,cardView6,cardView7,cardView8,cardView9,cardView10,cardView11,cardView12;
    DatabaseReference databaseReference;
    ArrayList<String> teacher_name=new ArrayList<>();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_management);

        teacher_name.add("Monday");
        teacher_name.add("Tuesday");
        teacher_name.add("Wednesday");
        teacher_name.add("Thursday");
        teacher_name.add("Friday");
        teacher_name.add("Saturday");





        Spinner spinner=findViewById(R.id.spinner2);
        Button button=findViewById(R.id.check_button);


        findids();

        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showCustomDialog(R.id.teacher1,R.id.sub1);

            }
        });

        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCustomDialog(R.id.teacher3,R.id.sub3);
            }
        });
        cardView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCustomDialog(R.id.teacher5,R.id.sub5);
            }
        });

        cardView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCustomDialog(R.id.teacher6,R.id.sub6);
            }
        });
        cardView7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCustomDialog(R.id.teacher7,R.id.sub7);
            }
        });
        cardView8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCustomDialog(R.id.teacher8,R.id.sub8);
            }
        });
        cardView9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCustomDialog(R.id.teacher9,R.id.sub9);
            }
        });
        cardView10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCustomDialog(R.id.teacher10,R.id.sub10);
            }
        });
        cardView11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCustomDialog(R.id.teacher11,R.id.sub11);
            }
        });
        cardView12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCustomDialog(R.id.teacher12,R.id.sub12);
            }
        });




                databaseReference = FirebaseDatabase.getInstance().getReference().child("schedule_teacher").child("Momday").child("1");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                           Teacher teacher=dataSnapshot.getValue(Teacher.class);

                           TextView ann=findViewById(R.id.teacher1);
                           TextView a=findViewById(R.id.sub1);

                           ann.setText(teacher.getName());
                           a.setText(teacher.getSubject());

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            ArrayAdapter aa=new ArrayAdapter(schedule_management.this,android.R.layout.simple_spinner_item,teacher_name);
            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(aa);

    }
    private void showCustomDialog(final int a,final int b) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_event);
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;


        (dialog.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        (dialog.findViewById(R.id.bt_save)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String teacher,subject;
                EditText teacher_nam = dialog.findViewById(R.id.set_teacher_name);
                teacher = teacher_nam.getText().toString();
                TextView aa = findViewById(a);
                aa.setText(teacher);
                EditText subject_nam= dialog.findViewById(R.id.set_subject);
                subject = subject_nam.getText().toString();
                TextView bb = findViewById(b);
                bb.setText(subject);

                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    public void findids()
    {
        cardView1=findViewById(R.id.card1);
        cardView3=findViewById(R.id.card3);
        cardView5=findViewById(R.id.card5);
        cardView6=findViewById(R.id.card6);
        cardView7=findViewById(R.id.card7);
        cardView8=findViewById(R.id.card8);
        cardView9=findViewById(R.id.card9);
        cardView10=findViewById(R.id.card10);
        cardView11=findViewById(R.id.card11);
        cardView12=findViewById(R.id.card12);


    }

    static class Teacher
    {
        private String Name;
        private String Subject;

        public Teacher() {}

        public Teacher(String name,String subject)
        {
            this.Name=name;
            this.Subject=subject;
        }

        public String getName()
        {
            return Name;
        }
        public String getSubject()
        {
            return Subject;
        }

        public void setName(String name)
        {
            this.Name=name;
        }
        public void setSubject(String subject)
        {
            this.Subject=subject;
        }
    }
}