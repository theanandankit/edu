package com.example.edu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_mgmt);
        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.general_actionbar);
        getSupportActionBar().setElevation(10);
        View view = getSupportActionBar().getCustomView();
        TextView textView=(TextView)view.findViewById(R.id.tab_name);
        textView.setText("Management Member");
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

        viewFlipper=findViewById(R.id.management_flipper);
        int image[]={R.drawable.management_flipper1,R.drawable.management_flipper2};

        for (int images :image)
        {
            flipperimage(images);
        }

        Intent i=getIntent();
        mail.setText(i.getStringExtra("mail"));
        String Id=i.getStringExtra("id");
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
}
