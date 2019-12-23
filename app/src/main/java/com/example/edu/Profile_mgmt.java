package com.example.edu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
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

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Profile_mgmt extends AppCompatActivity {
    CardView takeAttendance;
    CardView complains;
    TextView name;
    TextView id;
    TextView mail;
    TextView contact;
    TextView roll;
    TextView actual;
    TextView extra;
    TextView present;
    DatabaseReference user;
    ViewFlipper viewFlipper;
    FirebaseAuth auth;
    ImageButton b;
    SharedPreferences pref;
    Button logout;
    FirebaseAuth.AuthStateListener authStateListener;
    ProgressBar progressBar;
    ArrayList<action_screen.notice> list=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_mgmt);
        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.general_actionbar);
        getSupportActionBar().setElevation(10);
        View view = getSupportActionBar().getCustomView();
        b=(ImageButton)view.findViewById(R.id.home);
        logout=(Button)view.findViewById(R.id.logout);

        progressBar=findViewById(R.id.notice_management_progress);
        SpannableString spannableString=new SpannableString("MORE");
        TextView textView1=findViewById(R.id.action_more);
        ClickableSpan clickableSpan=new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {

                notice_popup_message();
            }
        };

        spannableString.setSpan(clickableSpan,0,4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView1.setText(spannableString);
        textView1.setMovementMethod(LinkMovementMethod.getInstance());


        auth=FirebaseAuth.getInstance();
        authStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user=firebaseAuth.getCurrentUser();
                if(user==null)
                {
                   Intent i=new Intent(Profile_mgmt.this, action_screen.class);
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

        DatabaseReference databaseReference5=FirebaseDatabase.getInstance().getReference().child("notice_management");
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


        TextView textView=(TextView)view.findViewById(R.id.tab_name);
        textView.setText("Management ");
        takeAttendance=findViewById(R.id.goto_attendance);
        name=(TextView)findViewById(R.id.member_name);
        id=(TextView)findViewById(R.id.member_id);
        mail=(TextView)findViewById(R.id.member_mail);
        contact=(TextView)findViewById(R.id.member_contact);
        roll=(TextView)findViewById(R.id.member_roll);
        actual=(TextView)findViewById(R.id.actual_days);
        extra=(TextView)findViewById(R.id.extra_days);
        present=(TextView)findViewById(R.id.present_days);
        complains=findViewById(R.id.complain_mgmt);

        pref= PreferenceManager.getDefaultSharedPreferences(Profile_mgmt.this);

        viewFlipper=findViewById(R.id.management_flipper);
        int image[]={R.drawable.management_flipper1,R.drawable.management_flipper2};

        for (int images :image)
        {
            flipperimage(images);
        }

        Intent i=getIntent();
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Profile_mgmt.this,action_screen.class);
                startActivity(i);
            }
        });

        mail.setText(pref.getString("mail","000"));
        String Id=pref.getString("userid","000");
        id.setText(Id);
        user= FirebaseDatabase.getInstance().getReference().child("teacher_info").child(Id);
        user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Teacher_management.Teacher details=dataSnapshot.getValue(Teacher_management.Teacher.class);
                String batch=details.getBatch();
                String contact_no=details.getContact_no();
                String actual_day=details.getActual_day();
                String present_day=details.getPresent();
                String extra_day=details.getExtra_day();
                String user_name=details.getUser_name();
                roll.setText(batch);
                contact.setText(contact_no);
                name.setText(user_name);
                actual.setText(actual_day);
                present.setText(present_day);
                extra.setText(extra_day);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        takeAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Profile_mgmt.this,Management_screen.class);

                startActivity(i);
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
    public void onBackPressed()
    {
        //auth.signOut();
        //Toast.makeText(getApplicationContext(), "Logging you out", Toast.LENGTH_SHORT).show();
        Intent i=new Intent(Profile_mgmt.this,action_screen.class);
        startActivity(i);
        //pref.edit().clear();
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

        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child("notice_management");
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
