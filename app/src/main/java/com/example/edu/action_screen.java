package com.example.edu;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class action_screen extends AppCompatActivity implements View.OnClickListener {
    ViewFlipper viewFlipper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_screen);
        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.general_actionbar);
        getSupportActionBar().setElevation(10);
        View view = getSupportActionBar().getCustomView();
        TextView textView=(TextView)view.findViewById(R.id.tab_name);
        textView.setText("Home");
        Typeface typeface= ResourcesCompat.getFont(getApplicationContext(),R.font.berkshireswash);
        textView.setTextColor(getResources().getColor(R.color.white));
        CardView mana= findViewById(R.id.management);
        CardView teach=findViewById(R.id.teaching);
        CardView admin=findViewById(R.id.admin);
        CardView list_member=findViewById(R.id.list_teacher);

        viewFlipper=findViewById(R.id.flipper);

        int image[]={R.drawable.flipper_1,R.drawable.flipper_2,R.drawable.flipper_3};

        for (int images :image)
        {
            flipperimage(images);
        }
        mana.setOnClickListener(this);
        teach.setOnClickListener(this);
        admin.setOnClickListener(this);
        list_member.setOnClickListener(this);
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
                i=new Intent(this,login_screen.class);
                startActivity(i);
                break;

            case R.id.list_teacher:
                i=new Intent(getApplicationContext(),schedule_view.class);
                startActivity(i);
                break;
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