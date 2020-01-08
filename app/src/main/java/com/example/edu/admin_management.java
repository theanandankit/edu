package com.example.edu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.ColorSpace;
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
import android.widget.ImageButton;
import android.widget.ImageView;
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

import java.io.CharArrayReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static com.example.edu.login_screen.id;
import static com.example.edu.login_screen.pref;

public class admin_management extends AppCompatActivity {
    public static ArrayList<String> member_list=new ArrayList<>();
    Teacher_management.Teacher admin_teacher_info=new Teacher_management.Teacher();
    ViewFlipper viewFlipper;
    ProgressBar progressBar;
    ArrayList<action_screen.notice> list=new ArrayList<>();
    ImageButton b;
    Button logout;
    SharedPreferences pref;

    FirebaseAuth auth;
    FirebaseAuth.AuthStateListener authStateListener;


    public static member_sgmid member_list_object=new member_sgmid();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        member_list=member_list_object.spinner_setter();
        Collections.sort(member_list,String.CASE_INSENSITIVE_ORDER);
        setContentView(R.layout.activity_admin_management);
        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.general_actionbar);
        getSupportActionBar().setElevation(10);
        View view = getSupportActionBar().getCustomView();
        final TextView textView=(TextView)view.findViewById(R.id.tab_name);
        progressBar=findViewById(R.id.notice_admin_progress);
        textView.setText("Admin");
        Typeface typeface= ResourcesCompat.getFont(getApplicationContext(),R.font.berkshireswash);
        textView.setTextColor(getResources().getColor(R.color.white));
        b=(ImageButton)view.findViewById(R.id.home);
        logout=(Button)view.findViewById(R.id.logout);
        SpannableString spannableString=new SpannableString("MORE");
        TextView textView1=findViewById(R.id.action_admin_more);
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
                    Intent i=new Intent(admin_management.this, action_screen.class);
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
        pref= PreferenceManager.getDefaultSharedPreferences(admin_management.this);

        DatabaseReference databaseReference5=FirebaseDatabase.getInstance().getReference().child("notice_admin");
        Query l=databaseReference5.orderByKey().limitToLast(3);
        l.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e("ihg","jh");

                int finalQ=1;
                for (final DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    Log.e("fghj","hgf");
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


        CardView registration =findViewById(R.id.registration);
        final CardView management_schedule=findViewById(R.id.management_schedule);
        final CardView schedule_management=findViewById(R.id.schedule_management);
        final CardView member_management=findViewById(R.id.member_monitoring);
        final CardView list_of_member=findViewById(R.id.management_listview);
        final CardView management_complain=findViewById(R.id.management_complain);
        final CardView admin_schedule_view=findViewById(R.id.admin_schedule_view);
        CardView date_monitoring=findViewById(R.id.management_date_monitoring);
        CardView notification_create=findViewById(R.id.member_notification);
        CardView basic_info=findViewById(R.id.basic_info);

        basic_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(), com.example.edu.basic_info.class);
                startActivity(i);
            }
        });

        date_monitoring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(getApplicationContext(), com.example.edu.date_monitoring.class);
                startActivity(i);
            }
        });

        admin_schedule_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(getApplicationContext(),schedule_view.class);
                startActivity(i);
            }
        });
        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i =new Intent(admin_management.this,new_registration.class);
                startActivity(i);
            }
        });

        schedule_management.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(admin_management.this,schedule_management.class);
                startActivity(i);
            }
        });
        management_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(admin_management.this,Management_schedule.class);
                startActivity(i);
            }
        });

        member_management.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),member_monitoring.class);
                startActivity(i);
            }
        });

        list_of_member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(getApplicationContext(),list_member.class);
                startActivity(i);
            }
        });
        management_complain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(getApplicationContext(),complain_viewer_activity.class);
                startActivity(i);
            }
        });

        notification_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(getApplicationContext(),Notification_genrater.class);
                startActivity(i);
            }
        });


        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("teacher_info").child(pref.getString("userid","000"));


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                admin_teacher_info=dataSnapshot.getValue(Teacher_management.Teacher.class);

                TextView name=findViewById(R.id.admin_user_name);
                TextView batch=findViewById(R.id.admin_user_batch);
                TextView contact=findViewById(R.id.admin_user_phone);
                TextView email=findViewById(R.id.admi_user_email);

                name.setText(admin_teacher_info.getUser_name());
                batch.setText(admin_teacher_info.getBatch());
                contact.setText(admin_teacher_info.getContact_no());
                email.setText(admin_teacher_info.getEmail());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }
        });

        viewFlipper=findViewById(R.id.admin_flipper);

        int image[]={R.drawable.admin_back,R.drawable.admin_back1};

        for (int images :image)
        {
            flipperimage(images);
        }
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

        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child("notice_admin");
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
    @Override
    public void onBackPressed()
    {
        //auth.signOut();
        //Toast.makeText(getApplicationContext(), "Logging you out", Toast.LENGTH_SHORT).show();
        Intent i=new Intent(admin_management.this,action_screen.class);
        startActivity(i);
        //pref.edit().clear();
    }
    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(authStateListener);
    }

}
