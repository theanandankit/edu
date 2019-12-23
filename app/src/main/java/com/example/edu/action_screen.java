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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;
import android.widget.ViewFlipper;

import com.example.edu.adapter.notice_adapter;
import com.example.edu.adapter.schedule_adapter;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class action_screen extends AppCompatActivity  {
    ViewFlipper viewFlipper;
    FirebaseAuth auth;
    int type;
    int counter;
    TextView login_text;
    public static SharedPreferences pref_1;
    SharedPreferences pref;
    CardView mana;
    CardView action_scedule;
    CardView other_activity;
    CardView sunday_activity;
    Button logout;
    FirebaseAuth.AuthStateListener authStateListener;
    ArrayList<notice> list=new ArrayList<>();
    ProgressBar progressBar;
    SharedPreferences.Editor basic;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_screen);
        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        CardView action_schedule=findViewById(R.id.action_schedule_main);
        getSupportActionBar().setCustomView(R.layout.general_actionbar);
        getSupportActionBar().setElevation(200);
        View view = getSupportActionBar().getCustomView();
        logout=(Button)view.findViewById(R.id.logout);
        logout.setVisibility(View.INVISIBLE);
        logout.setEnabled(false);
        TextView textView=(TextView)view.findViewById(R.id.tab_name);
        textView.setText("Home");
        Typeface typeface= ResourcesCompat.getFont(getApplicationContext(),R.font.berkshireswash);
        textView.setTextColor(getResources().getColor(R.color.white));
        mana= findViewById(R.id.management);
         action_scedule=findViewById(R.id.action_schedule);
         other_activity=findViewById(R.id.other_event);
         sunday_activity=findViewById(R.id.Sunday_activity);
        auth=FirebaseAuth.getInstance();
        login_text=(TextView)findViewById(R.id.login_text);
        TextView textView1=findViewById(R.id.action_more);
        progressBar=findViewById(R.id.notice_progress_box);
        CardView about_app=findViewById(R.id.about_app_card);
        pref_1 = PreferenceManager.getDefaultSharedPreferences(this);
        basic = pref_1.edit();

        about_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                about_app_fun();
            }
        });

        SpannableString spannableString=new SpannableString("MORE");


        ClickableSpan clickableSpan=new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {

                notice_popup_message();
            }
        };

        spannableString.setSpan(clickableSpan,0,4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView1.setText(spannableString);
        textView1.setMovementMethod(LinkMovementMethod.getInstance());

        action_schedule.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        Intent i=new Intent(getApplicationContext(),schedule_view.class);
        startActivity(i);
    }
});

        viewFlipper=findViewById(R.id.flipper);

        int image[]={R.drawable.flipper_1,R.drawable.flipper_2,R.drawable.flipper_3};

        for (int images :image)
        {
            flipperimage(images);
        }
        authStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                 FirebaseUser user=firebaseAuth.getCurrentUser();
                if(user==null)
                {
                    login_text.setText("Login");
                    logout.setVisibility(View.INVISIBLE);
                    logout.setEnabled(false);
                }

            }
        };

        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("notice").child("counter");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                counter=dataSnapshot.getValue().hashCode();
                Toast.makeText(getApplicationContext(),String.valueOf(counter),Toast.LENGTH_LONG).show();
                int q = 3;
                counter--;
                while (q>0) {
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("notice").child(String.valueOf(counter));
                    final int finalQ = q;
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            progressBar.setVisibility(View.INVISIBLE);


                            if(finalQ ==3)
                            {
                                TextView textView=findViewById(R.id.notice_data1);
                                TextView textView1=findViewById(R.id.notice_date1);
                                String[] data = dataSnapshot.getValue().toString().split("-");
                                textView.setText(data[0]);
                                textView1.setText(data[1]);
                            }
                            if(finalQ ==2)
                            {
                                TextView textView1=findViewById(R.id.notice_date2);
                                TextView textView=findViewById(R.id.notice_data2);
                                String[] data = dataSnapshot.getValue().toString().split("-");
                                textView.setText(data[0]);
                                textView1.setText(data[1]);
                            }
                            if(finalQ ==1)
                            {
                                TextView textView1=findViewById(R.id.notice_date3);
                                TextView textView=findViewById(R.id.notice_data3);
                                String[] data = dataSnapshot.getValue().toString().split("-");
                                textView.setText(data[0]);
                                textView1.setText(data[1]);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    q--;
                    counter--;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        final TextView regular=findViewById(R.id.action_regular);
        final TextView sunday=findViewById(R.id.action_suday);
        final TextView student=findViewById(R.id.action_total_student);
        final TextView teacher=findViewById(R.id.action_total_teacher);
        final TextView management=findViewById(R.id.action_total_management);

        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference().child("basic_info");
        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (final DataSnapshot postSnapshot : dataSnapshot.getChildren())
                {
                    if(postSnapshot.getKey().equals("management"))
                    {
                        management.setText(postSnapshot.getValue().toString());
                        basic.putString("management",postSnapshot.getValue().toString());
                    }

                    if(postSnapshot.getKey().equals("regular"))
                    {
                        regular.setText(postSnapshot.getValue().toString());
                        basic.putString("regular",postSnapshot.getValue().toString());
                    }

                    if(postSnapshot.getKey().equals("student"))
                    {
                        student.setText(postSnapshot.getValue().toString());
                        basic.putString("student",postSnapshot.getValue().toString());
                    }

                    if(postSnapshot.getKey().equals("sunday"))
                    {
                        sunday.setText(postSnapshot.getValue().toString());
                        basic.putString("sunday",postSnapshot.getValue().toString());

                    }

                    if(postSnapshot.getKey().equals("total_teacher"))
                    {
                        teacher.setText(postSnapshot.getValue().toString());
                        basic.putString("teacher",postSnapshot.getValue().toString());
                    }
                }
                 basic.commit();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void flipperimage(int image)
    {
        ImageView imageView=new ImageView(this);
        imageView.setBackgroundResource(image);

        viewFlipper.addView(imageView);
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);

        viewFlipper.setInAnimation(this,android.R.anim.slide_in_left);
        viewFlipper.setOutAnimation(this,android.R.anim.slide_out_right);
    }

    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(authStateListener);



        sunday_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(), com.example.edu.sunday_activity.class);
                startActivity(i);
            }
        });




        other_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),other_events.class);
                startActivity(i);
            }
        });

        action_scedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),list_member.class);
                startActivity(i);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();

            }
        });
        if(auth.getCurrentUser()!=null)
        {
            login_text.setText("Visit your page");
            logout.setVisibility(View.VISIBLE);
            logout.setEnabled(true);


        }

        mana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(auth.getCurrentUser()==null)
                {
                    Intent i=new Intent(getApplicationContext(),login_screen.class);
                    startActivity(i);
                }
                else
                {     pref= PreferenceManager.getDefaultSharedPreferences(action_screen.this);
                    type=pref.getInt("type",0);
                   Toast.makeText(getApplicationContext(),"hi",Toast.LENGTH_LONG).show();

                    if(type!=0)
                    {
                        Intent i;

                        if(type==1)
                        {
                            i=new Intent(action_screen.this,admin_management.class);
                        }
                        else if(type==2)
                        {
                            i=new Intent(action_screen.this,Teacher_management.class);
                        }
                        else
                        {
                            i=new Intent(action_screen.this,Profile_mgmt .class);
                        }
                        startActivity(i);
                    }


                }



            }
        });



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

        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child("notice");
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
                        list.add(new notice(data[1], data[0]));
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

    public void about_app_fun()
    {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.about_app);
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        final ListView listView=dialog.findViewById(R.id.notice_list);
        final ProgressBar progressBar=dialog.findViewById(R.id.notice_progress);

        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setAttributes(lp);

    }

  public static class notice
    {
        String date;
        String message;

        public String getDate() {
            return date;
        }

        public String getMessage() {
            return message;
        }

        public notice(String date, String message) {
            this.date = date;
            this.message = message;
        }
    }

    public class basic_info_class
    {
        public basic_info_class(String regular, String sunday, String total_teacher, String management, String student) {
            this.regular = regular;
            this.sunday = sunday;
            this.total_teacher = total_teacher;
            this.management = management;
            this.student = student;
        }
      public basic_info_class()
        {

        }

        String regular;
        String sunday;
        String total_teacher;
        String management;
        String student;

        public String getRegular() {
            return regular;
        }

        public String getSunday() {
            return sunday;
        }

        public String getTotal_teacher() {
            return total_teacher;
        }

        public String getManagement() {
            return management;
        }

        public String getStudent() {
            return student;
        }
    }
}