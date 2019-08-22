package com.example.edu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class login_screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        final EditText use=findViewById(R.id.username);
        final EditText pass=findViewById(R.id.password);

        String text1="Forget Password";
        String text2="Trouble In Login";

        Button login=findViewById(R.id.login);
        TextView forget_password=findViewById(R.id.forget_password);
        TextView trouble=findViewById(R.id.trouble);

        SpannableString spannableString=new SpannableString(text1);
        SpannableString spannableString1=new SpannableString(text2);

        ClickableSpan clickableSpan =new ClickableSpan() {
            @Override
            public void onClick(View view) {
                Toast.makeText(login_screen.this,"Forget password",Toast.LENGTH_LONG).show();
            }
        };

        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                Toast.makeText(login_screen.this,"trouble in login",Toast.LENGTH_LONG).show();
            }
        };

        spannableString.setSpan(clickableSpan,0,15, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString1.setSpan(clickableSpan1,0,16,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        forget_password.setText(spannableString);
        forget_password.setMovementMethod(LinkMovementMethod.getInstance());
        trouble.setText(spannableString1);
        trouble.setMovementMethod(LinkMovementMethod.getInstance());

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username= use.getText().toString();
                String password= pass.getText().toString();

                if(username.equals("theanandankit") && password.equals("12345"))
                {
                    Intent i=new Intent(login_screen.this,Management_screen.class);
                    startActivity(i);
                }
                else if(username.equals("teacher") && password.equals("123"))
                {
                    Intent i=new Intent(login_screen.this,Teacher_management.class);
                    startActivity(i);
                }

                else
                {
                    Toast.makeText(login_screen.this,"INVALID INPUT",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
