package com.example.edu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.edu.adapter.notice_adapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Teacher_management extends AppCompatActivity {
    public  static  Teacher teacher_info;
    CardView cardView_teacher_complain;
    public static String userid;
    ViewFlipper viewFlipper;
    ImageButton b;
    Button logout;
    FirebaseAuth auth;
    FirebaseAuth.AuthStateListener authStateListener;
    ArrayList<action_screen.notice> list=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_management);
        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.general_actionbar);
        getSupportActionBar().setElevation(10);
        View view = getSupportActionBar().getCustomView();
        b=(ImageButton)view.findViewById(R.id.home);
        final ProgressBar progressBar=findViewById(R.id.notice_teacher_progress);
        logout=(Button)view.findViewById(R.id.logout);
        TextView textView=(TextView)view.findViewById(R.id.tab_name);
        textView.setText("Teacher");
        Typeface typeface= ResourcesCompat.getFont(getApplicationContext(),R.font.berkshireswash);
        textView.setTextColor(getResources().getColor(R.color.white));
        auth=FirebaseAuth.getInstance();
        SpannableString spannableString=new SpannableString("MORE");
        TextView textView1=findViewById(R.id.action_teacher_more);
        ClickableSpan clickableSpan=new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {

                notice_popup_message();
            }
        };

        spannableString.setSpan(clickableSpan,0,4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView1.setText(spannableString);
        textView1.setMovementMethod(LinkMovementMethod.getInstance());

        authStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user=firebaseAuth.getCurrentUser();
                if(user==null)
                {
                    Intent i=new Intent(Teacher_management.this, action_screen.class);
                    startActivity(i);
                }

            }
        };
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Logging you out", Toast.LENGTH_SHORT).show();
                auth.signOut();

            }
        });
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Teacher_management.this,action_screen.class);
                startActivity(i);
            }
        });

        cardView_teacher_complain=findViewById(R.id.teacher_complain_mgmt);
        SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(Teacher_management.this);
        userid=pref.getString("userid","000");

        Toast.makeText(getApplicationContext(),userid,Toast.LENGTH_LONG).show();

        DatabaseReference databaseReference5=FirebaseDatabase.getInstance().getReference().child("notice_teacher");
        Query l=databaseReference5.orderByKey().limitToLast(3);
        l.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                int finalQ=1;
                for (final DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    if(finalQ ==1)
                    {
                        TextView textView=findViewById(R.id.notice_data1);
                        TextView textView1=findViewById(R.id.notice_date1);
                        String[] data = postSnapshot.getValue().toString().split("-");
                        textView.setText(data[0]);
                        textView1.setText(data[1]);
                        finalQ=2;
                        continue;
                    }
                    if(finalQ ==2)
                    {
                        TextView textView1=findViewById(R.id.notice_date2);
                        TextView textView=findViewById(R.id.notice_data2);
                        String[] data = postSnapshot.getValue().toString().split("-");
                        textView.setText(data[0]);
                        textView1.setText(data[1]);
                        finalQ=3;
                        continue;
                    }
                    if(finalQ ==3)
                    {
                        TextView textView1=findViewById(R.id.notice_date3);
                        TextView textView=findViewById(R.id.notice_data3);
                        String[] data = postSnapshot.getValue().toString().split("-");
                        textView.setText(data[0]);
                        textView1.setText(data[1]);
                    }
                    Log.e("hj", dataSnapshot.getValue().toString());
                }

                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("teacher_info").child(userid);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                teacher_info=dataSnapshot.getValue(Teacher.class);
                set_value();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }
        });

        cardView_teacher_complain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                teacher_complain(teacher_info.getUser_name());
            }
        });

        viewFlipper=findViewById(R.id.teacher_flipper);

        int image[]={R.drawable.teacher_flipper1,R.drawable.teacher_flipper2};

        for (int images :image)
        {
            flipperimage(images);
        }

    }
    static class Teacher
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
    public void set_value()
    {
        TextView user_name=findViewById(R.id.teacher_member_name);
        TextView user_phone=findViewById(R.id.teacher_member_contact);
        TextView user_email=findViewById(R.id.teacher_member_mail);
        TextView user_batch=findViewById(R.id.teacher_member_roll);
        TextView user_total=findViewById(R.id.teacher_actual_days);
        TextView user_extra=findViewById(R.id.teacher_extra_days);
        TextView user_present=findViewById(R.id.teacher_present_days);
        TextView user_sgm_id=findViewById(R.id.teacher_member_id);

        user_name.setText(teacher_info.getUser_name());
        user_email.setText(teacher_info.getEmail());
        user_batch.setText(teacher_info.getBatch());
        user_phone.setText(teacher_info.getContact_no());
        user_total.setText(teacher_info.getActual_day());
        user_extra.setText(teacher_info.getExtra_day());
        user_present.setText(teacher_info.getPresent());
        user_sgm_id.setText(userid);


    }
    public void teacher_complain(String name)
    {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.complain_teacher);
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        EditText complain=dialog.findViewById(R.id.teacher_complain_text);
        Button done=dialog.findViewById(R.id.teacher_complain_done);
        Button cancel=dialog.findViewById(R.id.teacher_complain_cancel);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        String complain_text=complain.getText().toString();

        complain_text=complain_text+"-by "+name+" (from teacher section)";

        final String finalComplain_text = complain_text;
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final DatabaseReference databaseReference_complain_counter=FirebaseDatabase.getInstance().getReference().child("complain").child("counter");


                databaseReference_complain_counter.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        String counter=dataSnapshot.getValue().toString();
                        int count=dataSnapshot.getValue().hashCode();

                        DatabaseReference databaseReference_complain=FirebaseDatabase.getInstance().getReference().child("complain").child(counter);
                        databaseReference_complain.setValue(finalComplain_text);

                        count++;

                        databaseReference_complain_counter.setValue(count);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }});
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setAttributes(lp);

    }

    public void flipperimage(int image)
    {
        ImageView imageView=new ImageView(this);
        imageView.setBackgroundResource(image);

        viewFlipper.addView(imageView);
        viewFlipper.setFlipInterval(4000);
        viewFlipper.setAutoStart(true);

        viewFlipper.setInAnimation(this,android.R.anim.slide_in_left);
        viewFlipper.setOutAnimation(this,android.R.anim.slide_out_right);
    }

    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(authStateListener);
    }

    public void notice_popup_message()
    {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.notice_popup);
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        final ListView listView=dialog.findViewById(R.id.notice_list);
        final ProgressBar progressBar=dialog.findViewById(R.id.notice_progress);

        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child("notice_teacher");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(!list.isEmpty())
                {
                    list.clear();
                }

                for(final DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String[] data = postSnapshot.getValue().toString().split("-");
                    if (!postSnapshot.getKey().equals("counter")) {
                        list.add(new action_screen.notice(data[1], data[0]));
                    }
                }

                progressBar.setVisibility(View.GONE);
                notice_adapter myAdapter=new notice_adapter(getApplicationContext(),R.layout.notice_view,list);
                listView.setAdapter(myAdapter);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setAttributes(lp);
    }
}
