package com.example.edu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class action_screen extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_screen);

        CardView mana= findViewById(R.id.management);
        CardView teach=findViewById(R.id.teaching);
        CardView admin=findViewById(R.id.admin);


        mana.setOnClickListener(this);
        teach.setOnClickListener(this);
        admin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        Intent i;

        switch (view.getId())
        {
            case R.id.management:
                i=new Intent(this,login_screen.class);
                startActivity(i);
                break;

            case R.id.teaching:
                 i=new Intent(this,login_screen.class);
                 startActivity(i);
                 break;
            case R.id.admin:
                break;

        }
    }
}
