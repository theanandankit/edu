package com.example.edu;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.ColorSpace;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class admin_management extends AppCompatActivity {
    public static ArrayList<String> member_list=new ArrayList<>();

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
        TextView textView=(TextView)view.findViewById(R.id.tab_name);
        textView.setText("Admin");
        Typeface typeface= ResourcesCompat.getFont(getApplicationContext(),R.font.berkshireswash);
        textView.setTextColor(getResources().getColor(R.color.white));




        CardView registration =findViewById(R.id.registration);
        final CardView management_schedule=findViewById(R.id.management_schedule);
        final CardView schedule_management=findViewById(R.id.schedule_management);
        final CardView member_management=findViewById(R.id.member_monitoring);
        final CardView list_of_member=findViewById(R.id.management_listview);
        final CardView management_complain=findViewById(R.id.management_complain);


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
    }
}
