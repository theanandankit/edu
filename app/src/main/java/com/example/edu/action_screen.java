package com.example.edu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class action_screen extends AppCompatActivity  {
    ViewFlipper viewFlipper;
    FirebaseAuth auth;
    int type;
    TextView login_text;
    SharedPreferences pref;
    CardView mana;
    CardView action_scedule;
    CardView other_activity;
    CardView sunday_activity;
    Button logout;
   FirebaseAuth.AuthStateListener authStateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_screen);
        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
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
}