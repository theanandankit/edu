package com.example.edu;

import androidx.annotation.NonNull;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class login_screen extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        final EditText use=findViewById(R.id.username);
        final EditText pass=findViewById(R.id.password);
        final ProgressBar progressBar=findViewById(R.id.progress);


        firebaseAuth =FirebaseAuth.getInstance();


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
                progressBar.setVisibility(View.VISIBLE);
                String username= use.getText().toString();
                String password= pass.getText().toString();

                firebaseAuth.signInWithEmailAndPassword(username,password)
                        .addOnCompleteListener(login_screen.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful())
                                {
                                    progressBar.setVisibility(View.GONE);
                                    Intent i=new Intent(login_screen.this,Management_screen.class);
                                    startActivity(i);
                                }
                            }
                        });
            }
        });
    }
}
