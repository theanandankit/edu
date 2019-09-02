package com.example.edu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.app.usage.UsageEvents;
import android.os.Bundle;
import android.util.EventLog;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.view.Event;

import java.util.ArrayList;

public class schedule_management extends AppCompatActivity {


    CardView cardView;
    DatabaseReference databaseReference;
    ArrayList<String> teacher_name=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_management);

        Spinner spinner=findViewById(R.id.spinner2);
        Button button=findViewById(R.id.check_button);

        cardView=findViewById(R.id.card1);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showCustomDialog(R.id.teacher1,R.id.sub1);

            }
        });


        for (int a=1;a<13;a++) {

            if (!(a==2||a==4)) {
                databaseReference = FirebaseDatabase.getInstance().getReference().child("schedule_teacher").child("monday").child(String.valueOf(a));
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                     teacher_name.add(dataSnapshot.getValue().toString());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
            ArrayAdapter aa=new ArrayAdapter(schedule_management.this,android.R.layout.simple_spinner_item,teacher_name);
            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(aa);
        }
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


        ((ImageButton) dialog.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        ((Button) dialog.findViewById(R.id.bt_save)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView aa=findViewById(a);
                EditText teacher_name=findViewById(R.id.set_teacher_name);
                String teacher=teacher_name.getText().toString();
             //   aa.setText(teacher);
                TextView bb=findViewById(b);
                EditText subject_name=findViewById(R.id.set_subject);
                String subject=subject_name.getText().toString();
              //  bb.setText(subject);

                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }
}
