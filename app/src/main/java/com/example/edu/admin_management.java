package com.example.edu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.ColorSpace;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.CharArrayReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static com.example.edu.login_screen.pref;

public class admin_management extends AppCompatActivity {
    public static ArrayList<String> member_list=new ArrayList<>();
    Teacher_management.Teacher admin_teacher_info=new Teacher_management.Teacher();
    ViewFlipper viewFlipper;

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
        textView.setText("Admin");
        Typeface typeface= ResourcesCompat.getFont(getApplicationContext(),R.font.berkshireswash);
        textView.setTextColor(getResources().getColor(R.color.white));


        CardView registration =findViewById(R.id.registration);
        final CardView management_schedule=findViewById(R.id.management_schedule);
        final CardView schedule_management=findViewById(R.id.schedule_management);
        final CardView member_management=findViewById(R.id.member_monitoring);
        final CardView list_of_member=findViewById(R.id.management_listview);
        final CardView management_complain=findViewById(R.id.management_complain);
        final CardView admin_schedule_view=findViewById(R.id.admin_schedule_view);
        CardView date_monitoring=findViewById(R.id.management_date_monitoring);

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

}
