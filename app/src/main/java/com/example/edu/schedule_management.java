package com.example.edu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;

import android.app.Dialog;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class schedule_management extends AppCompatActivity {

    CardView cardView1,cardView3,cardView5,cardView6,cardView7,cardView8,cardView9,cardView10,cardView11,cardView12;
    String s;
    DatabaseReference databaseReference;
    ArrayList<String> teacher_name=new ArrayList<>();
    Spinner spinner;
    public static Teacher teacher;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_management);
        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.general_actionbar);
        getSupportActionBar().setElevation(10);
        View view = getSupportActionBar().getCustomView();
        TextView textView=(TextView)view.findViewById(R.id.tab_name);
        textView.setText("Set schedule");
        Typeface typeface= ResourcesCompat.getFont(getApplicationContext(),R.font.berkshireswash);
        textView.setTextColor(getResources().getColor(R.color.white));
        databaseReference=FirebaseDatabase.getInstance().getReference().child("schedule_teacher");
        teacher_name.add("Select day");
        teacher_name.add("Monday");
        teacher_name.add("Tuesday");
        teacher_name.add("Wednesday");
        teacher_name.add("Thursday");
        teacher_name.add("Friday");
        teacher_name.add("Saturday");

        spinner=findViewById(R.id.spinner2);

        ArrayAdapter<String> aa=new ArrayAdapter<String>(schedule_management.this,android.R.layout.simple_spinner_item,teacher_name){
            @Override
            public boolean isEnabled(int position) {
                if(position==0)
                    return false;
                else
                    return true;
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View v=super.getDropDownView(position,convertView,parent);
                TextView t=(TextView)v;
                if(position==0)
                    t.setTextColor(Color.GRAY);
                else
                    t.setTextColor(Color.BLACK);
                return v;
            }
        };
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(aa);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

               switch (i)
               {
                   case 1:
                       s="Monday";
                       break;
                   case 2:
                       s="Tuesday";
                       break;
                   case 3:
                       s="Wednesday";
                       break;
                   case 4:
                       s="Thursday";
                       break;
                   case 5:
                       s="Friday";
                       break;
                   case 6:
                       s="Saturday";
                       break;
                   case 7:
                       s="Sunday";
                       break;
                   default:
                       s="Monday";
               }
               set_week_info(s);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        findids();

        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showCustomDialog(1,R.id.teacher1,R.id.uid1,R.id.batch1,R.id.sub1);

            }
        });

        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCustomDialog(3,R.id.teacher3,R.id.uid3,R.id.batch3,R.id.sub3);
            }
        });
        cardView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCustomDialog(5,R.id.teacher5,R.id.uid5,R.id.batch5,R.id.sub5);
            }
        });

        cardView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCustomDialog(6,R.id.teacher6,R.id.uid6,R.id.batch6,R.id.sub6);
            }
        });
        cardView7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCustomDialog(7,R.id.teacher7,R.id.uid7,R.id.batch7,R.id.sub7);
            }
        });
        cardView8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCustomDialog(8,R.id.teacher8,R.id.uid8,R.id.batch8,R.id.sub8);
            }
        });
        cardView9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCustomDialog(9,R.id.teacher9,R.id.uid9,R.id.batch9,R.id.sub9);
            }
        });
        cardView10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCustomDialog(10,R.id.teacher10,R.id.uid10,R.id.batch10,R.id.sub10);
            }
        });
        cardView11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCustomDialog(11,R.id.teacher11,R.id.uid11,R.id.batch11,R.id.sub11);
            }
        });
        cardView12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCustomDialog(12,R.id.teacher12,R.id.uid12,R.id.batch12,R.id.sub12);
            }
        });

    }
    private void showCustomDialog(final int d,final int a,final int b, final int e,final int c) {
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
                Typeface typeface= ResourcesCompat.getFont(getApplicationContext(),R.font.stylish);

                String teacher,uid,subject,batch;
                TextInputLayout teacher_nam = dialog.findViewById(R.id.set_teacher_name);
                teacher = teacher_nam.getEditText().getText().toString();
                TextView aa = findViewById(a);
                aa.setText("Teacher: "+teacher);
                aa.setTypeface(typeface);
                aa.setTextColor(Color.BLACK);
                TextInputLayout u_id=dialog.findViewById(R.id.set_uid);
                uid=u_id.getEditText().getText().toString();
                TextView bb=findViewById(b);
                bb.setText("UID: "+uid);
                bb.setTypeface(typeface);
                bb.setTextColor(Color.BLACK);
                TextInputLayout subject_nam= dialog.findViewById(R.id.set_subject);
                subject = subject_nam.getEditText().getText().toString();
                TextView cc = findViewById(c);
                cc.setText("Subject: "+subject);
                cc.setTypeface(typeface);
                cc.setTextColor(Color.BLACK);
                TextInputLayout batch_nam=dialog.findViewById(R.id.set_batch);
                batch=batch_nam.getEditText().getText().toString();
                TextView ee=findViewById(e);
                ee.setText("Batch: "+batch);
                ee.setTypeface(typeface);
                ee.setTextColor(Color.BLACK);

                DatabaseReference reference=databaseReference.child(s).child(Integer.toString(d));
                Teacher teach=new Teacher(teacher,uid,batch,subject);
                reference.setValue(teach);


                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
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
        private String uid;
        private String batch;


        public Teacher() {}
        public Teacher(String name,String uid,String batch,String subject)
        {
            this.Name=name;
            this.uid=uid;
            this.Subject=subject;
            this.batch=batch;
        }

        public String getName()
        {
            return Name;
        }
        public String getSubject()
        {
            return Subject;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public void setName(String name)
        {
            this.Name=name;
        }
        public void setSubject(String subject)
        {
            this.Subject=subject;
        }

        public String getBatch() {
            return batch;
        }
    }

    public void setvalue(final int a,final int b, final int c, final int d, final int i)
    {
        TextView teacher_name = findViewById(a);
        TextView uid = findViewById(b);
        TextView batch=findViewById(c);
        TextView subject=findViewById(d);
        String teach="Teacher: ";
        String u_id="UID: ";
        String Batch="Batch: ";
        String Subject="Subject: ";
        if(i==1)
        {
            teach=teach+teacher.getName();
            u_id=u_id+teacher.getUid();
            Batch=Batch+teacher.getBatch();
            Subject=Subject+teacher.getSubject();
        }
        Typeface typeface= ResourcesCompat.getFont(getApplicationContext(),R.font.stylish);
        teacher_name.setText(teach);
        teacher_name.setTypeface(typeface);
        teacher_name.setTextColor(Color.BLACK);
        uid.setText(u_id);
        uid.setTypeface(typeface);
        uid.setTextColor(Color.BLACK);
        batch.setText(Batch);
        batch.setTypeface(typeface);
        batch.setTextColor(Color.BLACK);
        subject.setText(Subject);
        subject.setTypeface(typeface);
        subject.setTextColor(Color.BLACK);

    }


    public void set_week_info(String day)
    {
        for (int counter=1;counter<=12;counter++) {

            if (!(counter == 2 || counter == 4)) {

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("schedule_teacher").child(day).child(String.valueOf(counter));
                final int finalCounter = counter;
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        teacher = dataSnapshot.getValue(Teacher.class);
                        int i=0;
                        if(teacher!=null)
                        {  i=1;

                        }
                        else
                        {
                            i=0;
                        }
                        if(finalCounter ==1)
                        {
                            setvalue(R.id.teacher1,R.id.uid1,R.id.batch1,R.id.sub1,i);
                        }
                        else if(finalCounter ==3)
                        {
                            setvalue(R.id.teacher3,R.id.uid3,R.id.batch3,R.id.sub3,i);
                        }

                        else if(finalCounter ==5)
                        {
                            setvalue(R.id.teacher5,R.id.uid5,R.id.batch5,R.id.sub5,i);
                        }

                        else if(finalCounter ==6)
                        {
                            setvalue(R.id.teacher6,R.id.uid6,R.id.batch6,R.id.sub6,i);
                        }

                        else if(finalCounter ==7)
                        {
                            setvalue(R.id.teacher7,R.id.uid7,R.id.batch7,R.id.sub7,i);
                        }

                        else if(finalCounter ==8)
                        {
                            setvalue(R.id.teacher8,R.id.uid8,R.id.batch8,R.id.sub8,i);
                        }

                        else if(finalCounter ==9)
                        {
                            setvalue(R.id.teacher9,R.id.uid9,R.id.batch9,R.id.sub9,i);
                        }
                        else if(finalCounter ==10)
                        {
                            setvalue(R.id.teacher10,R.id.uid10,R.id.batch10,R.id.sub10,i);
                        }

                        else if(finalCounter ==11)
                        {
                            setvalue(R.id.teacher11,R.id.uid11,R.id.batch11,R.id.sub11,i);
                        }

                        else {
                            setvalue(R.id.teacher12,R.id.uid12,R.id.batch12,R.id.sub12,i);
                        }



                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        }
    }
}