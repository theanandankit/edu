package com.example.edu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class admin_management extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_management);




        CardView registration =findViewById(R.id.registration);
        final CardView schedule_management=findViewById(R.id.schedule_management);

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
    }
}
