package com.example.edu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class login_screen extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    public DatabaseReference databaseReference,databaseReference1,databaseReference2;
    public static SharedPreferences pref;
    public static TextInputLayout use;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        use=findViewById(R.id.username);
        final TextInputLayout pass=findViewById(R.id.password);
        final ProgressBar progressBar=findViewById(R.id.progress);
        final TextInputLayout id=findViewById(R.id.userid);
        progressBar.setVisibility(View.INVISIBLE);

        // shared preference to remember the last login
         pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        final SharedPreferences.Editor editor = pref.edit();

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

                if (!use.getEditText().getText().toString().isEmpty()) {

                    email_confirm_popup(use.getEditText().getText().toString());
                }
                else {
                    Toast.makeText(getApplicationContext(),"Enter your Email",Toast.LENGTH_LONG).show();
                }
            }
        };

        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(View view) {


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
                final String username= use.getEditText().getText().toString();
                String password= pass.getEditText().getText().toString();
                final String userid=id.getEditText().getText().toString();
                String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
                SimpleDateFormat sdf2 = new SimpleDateFormat("EEEE");
                final String current_day = sdf2.format(new Date());
                boolean t= username.matches(regex);
                if(t==false)
                    use.setError("Invalid Email");
                else if(password.length()==0)
                    pass.setError("Password can't be empty");
                else if (userid.isEmpty())
                    id.setError("Enter SGM ID");
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
                                        databaseReference=FirebaseDatabase.getInstance().getReference().child("teacher_info");
                                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                if(dataSnapshot.hasChild(userid))
                                                {
                                                    databaseReference1=FirebaseDatabase.getInstance().getReference().child("teacher_info").child(userid).child("email");

                                                    databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                                            if(username.equals(dataSnapshot.getValue().toString()))
                                                            {
                                                                databaseReference2=FirebaseDatabase.getInstance().getReference().child("teacher_info").child(userid).child("type");

                                                                databaseReference2.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                    @Override
                                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                                                        CheckBox checkBox=findViewById(R.id.remember_checkbox);

                                                                        if(dataSnapshot.getValue().toString().equals("administrator"))
                                                                        {

                                                                            if (checkBox.isChecked())
                                                                            {
                                                                                editor.putInt("type",1);                       // for administrator the value of type in shared preference is 1
                                                                            }

                                                                            Intent i=new Intent(login_screen.this,admin_management.class);
                                                                            progressBar.setVisibility(View.INVISIBLE);
                                                                            startActivity(i);

                                                                        }
                                                                        else if(dataSnapshot.getValue().toString().equals("Teacher")) {
                                                                            if (checkBox.isChecked()) {
                                                                                editor.putInt("type", 2);
                                                                            }
                                                                            Intent i=new Intent(login_screen.this,Teacher_management.class);
                                                                            progressBar.setVisibility(View.INVISIBLE);
                                                                            startActivity(i);
                                                                        }
                                                                        else if(dataSnapshot.getValue().toString().equals("Management")) {
                                                                            if (checkBox.isChecked()) {
                                                                                editor.putInt("type", 3);
                                                                            }
                                                                            Intent i=new Intent(login_screen.this,Management_screen.class);
                                                                            progressBar.setVisibility(View.INVISIBLE);
                                                                            startActivity(i);
                                                                        }
                                                                    }

                                                                    @Override
                                                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                                                    }
                                                                });


                                                                Toast.makeText(getApplicationContext(),"ok",Toast.LENGTH_LONG).show();
                                                            }
                                                        }

                                                        @Override
                                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                                        }
                                                    });
                                                }
                                                else
                                                    Toast.makeText(getApplicationContext(),"incorrect SGM ID",Toast.LENGTH_LONG).show();

                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                    }
                                    else
                                    {
                                        use.setError(null);
                                        pass.setError(null);

                                        Toast.makeText(getApplicationContext(),"Error Logging in", Toast.LENGTH_LONG).show();

                                        Toast.makeText(getApplicationContext(),"Invalid username or password",Toast.LENGTH_LONG).show();

                                        progressBar.setVisibility(View.INVISIBLE);
                                    }
                                }
                            });
                }
            }
        });
    }
    public void email_confirm_popup(String email)
    {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.email_reset_popup);
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        TextView textView=dialog.findViewById(R.id.email_dispaly);
        Button done=dialog.findViewById(R.id.email_done);
        Button cancel=dialog.findViewById(R.id.email_cancel);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                FirebaseAuth.getInstance().sendPasswordResetEmail(use.getEditText().getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {

                                    Toast.makeText(login_screen.this, "Link send to " + use.getEditText().getText().toString(), Toast.LENGTH_LONG).show();

                                } else
                                    Toast.makeText(login_screen.this, "Enter valid username", Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();

            }
        });

        textView.setText(email);

        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setAttributes(lp);
    }

    public void trouble_loading(final String username)
    {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.complain_popup);
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        final EditText complain_text=dialog.findViewById(R.id.complain_text);

        Button done=dialog.findViewById(R.id.complain_done);
        Button cancel=dialog.findViewById(R.id.complain_cancel);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String complain=complain_text.getText().toString();

                complain=complain+"-by"+username;


            }});
    }
}
