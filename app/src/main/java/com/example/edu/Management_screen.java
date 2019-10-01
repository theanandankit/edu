package com.example.edu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Management_screen extends AppCompatActivity {


    public static ArrayList<adaptor_class> class_name=new ArrayList<>();
    public static ListView listView;
    private myadaptor adaptor;
    DatabaseReference databaseReference;
    DatabaseReference reference;
     int a;
    String classes;
    String teacher,batch,status;
    int k=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management_screen);
        Calendar localCalendar = Calendar.getInstance(TimeZone.getDefault());

        listView= findViewById(R.id.list_id);
        SimpleDateFormat sdf2 = new SimpleDateFormat("EEEE");
        String current_day = sdf2.format(new Date());
        Date todaysDate = new Date();
        String date = "";
        DateFormat sdf1 = new SimpleDateFormat("dd/MMM/yyyy");
        date= sdf1.format(todaysDate);

        reference=FirebaseDatabase.getInstance().getReference().child(date);




            for (a = 1; a < 10; a++) {

                if (!(a == 2 || a == 4)) {

                    databaseReference = FirebaseDatabase.getInstance().getReference().child("schedule_teacher").child(current_day).child(String.valueOf(a));

                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            schedule_management.Teacher teach = dataSnapshot.getValue(schedule_management.Teacher.class);
                            String name = teach.getName();

                            String uid = teach.getUid();
                            String batch = teach.getBatch();
                            if (a == 1 || a == 3)
                                classes = a + "-" + a + 1;
                            else
                                classes = Integer.toString(a);
                            String comment = "";
                            adaptor_class adaptorClass = new adaptor_class(name, uid, batch, classes, comment);
                            class_name.add(adaptorClass);


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

            }
            adaptor=new myadaptor(class_name,getApplicationContext());
            listView.setAdapter(adaptor);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    adaptor_class obj=class_name.get(position);

                    showCustomDialog(position);
                    obj.comment=status;
                    adaptor.notifyDataSetChanged();
                    reference.child(obj.getClass1()).setValue(obj);


                }
            });





    }
    @Override
    public void onRestart() {
        super.onRestart();

        myadaptor myad=new myadaptor(class_name,this);
        listView.setAdapter(myad);
    }
    public void showCustomDialog(int i)
    {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.subtitute_popup);
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        final RadioButton present=(RadioButton) findViewById(R.id.present);
        final RadioButton absent=(RadioButton)findViewById(R.id.absent);
        final CheckBox check_member=(CheckBox)findViewById(R.id.check_member);
        final CheckBox check_not_member=(CheckBox)findViewById(R.id.check_not_member);
        final TextInputLayout name=(TextInputLayout)findViewById(R.id.name);
        final TextInputLayout Batch=(TextInputLayout)findViewById(R.id.Batch);
        final RadioButton substitute_sent=(RadioButton)findViewById(R.id.substitute_sent);
        final RadioButton substitute_not_sent=(RadioButton)findViewById(R.id.substitute_not_sent);
        final Spinner spinner=(Spinner)findViewById(R.id.spinner);

        Button submit=(Button)findViewById(R.id.submit);
        check_member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(present.isChecked())
                {
                   check_member.setEnabled(false);
                   check_not_member.setEnabled(false);
                   spinner.setEnabled(false);
                   name.setEnabled(false);
                   Batch.setEnabled(false);
                }

            }
        });
        absent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(absent.isChecked())
                {   substitute_sent.setEnabled(false);
                substitute_not_sent.setEnabled(false);
                    check_member.setEnabled(true);
                    check_not_member.setEnabled(true);
                    spinner.setEnabled(true);
                    name.setEnabled(true);
                    Batch.setEnabled(true);
                }
            }
        });
        substitute_not_sent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                substitute_not_sent.setEnabled(true);
                substitute_sent.setEnabled(true);
                check_member.setEnabled(false);
                check_not_member.setEnabled(false);
                spinner.setEnabled(false);
                name.setEnabled(false);
                Batch.setEnabled(false);

            }
        });
        substitute_sent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                teacher=name.getEditText().getText().toString();
                batch=Batch.getEditText().getText().toString();

                if(present.isChecked())
                {
                    status="Present";
                    dialog.dismiss();
                }

               else if(absent.isChecked())
                {
                    if(substitute_sent.isChecked() && !substitute_not_sent.isChecked())
                    {
                        status="Substitute sent";
                        dialog.dismiss();
                    }

                    else if(!substitute_sent.isChecked() && substitute_not_sent.isChecked())
                    {
                        status="Substitute not sent";
                        dialog.dismiss();
                    }

                    else Toast.makeText(getApplicationContext(),"Enter valid options",Toast.LENGTH_LONG).show();
                }
               else
                   Toast.makeText(getApplicationContext(),"Enter valid options",Toast.LENGTH_LONG).show();


            }
        });

    }
}
