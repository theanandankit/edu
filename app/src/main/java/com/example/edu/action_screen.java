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

public class action_screen extends AppCompatActivity  {
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

        mana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(getApplicationContext(),login_screen.class);
                startActivity(i);
            }
        });

        viewFlipper=findViewById(R.id.flipper);

        int image[]={R.drawable.flipper_1,R.drawable.flipper_2,R.drawable.flipper_3};

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