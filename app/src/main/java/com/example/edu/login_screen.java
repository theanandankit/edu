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
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class login_screen extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        final TextInputLayout use=findViewById(R.id.username);
        final TextInputLayout pass=findViewById(R.id.password);
        final ProgressBar progressBar=findViewById(R.id.progress);
        progressBar.setVisibility(View.INVISIBLE);


        firebaseAuth =FirebaseAuth.getInstance();


        String text1="Forgot Password";
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
                String username= use.getEditText().getText().toString();
                String password= pass.getEditText().getText().toString();
                String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
                SimpleDateFormat sdf2 = new SimpleDateFormat("EEEE");
                final String current_day = sdf2.format(new Date());
                boolean t= username.matches(regex);
                if(t==false)
                    use.setError("Invalid Email");
                else if(password.length()==0)
                    pass.setError("Password can't be empty");
                else
                { progressBar.setVisibility(View.VISIBLE);
                    firebaseAuth.signInWithEmailAndPassword(username,password)
                            .addOnCompleteListener(login_screen.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful())
                                    {
                                        progressBar.setVisibility(View.GONE);
                                        Toast.makeText(getApplicationContext(),current_day,Toast.LENGTH_LONG).show();
                                        Intent i=new Intent(login_screen.this,Management_screen.class);
                                        progressBar.setVisibility(View.INVISIBLE);
                                        startActivity(i);
                                    }
                                    else
                                    {
                                        use.setError(null);
                                        pass.setError(null);
<<<<<<< Updated upstream
                                        Toast.makeText(getApplicationContext(),"Error Logging in", Toast.LENGTH_LONG).show();
=======
                                        Toast.makeText(getApplicationContext(),"Invalid username or password",Toast.LENGTH_LONG).show();
>>>>>>> Stashed changes
                                        progressBar.setVisibility(View.INVISIBLE);
                                    }
                                }
                            });
                }


            }
        });
    }
}
