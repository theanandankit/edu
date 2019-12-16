package com.example.edu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.Button;

import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
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

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import br.com.simplepass.loading_button_lib.interfaces.OnAnimationEndListener;


public class login_screen extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    public DatabaseReference databaseReference,databaseReference1,databaseReference2;
    public static SharedPreferences pref;
    public static TextInputLayout use, pass, id;
    public static String complain,complain_name;
    ProgressDialog progressDialog;
    static String type;
    CircularProgressButton btn;
    SharedPreferences.Editor editor;
    ImageButton b;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.general_actionbar);
        getSupportActionBar().setElevation(10);
        View view = getSupportActionBar().getCustomView();
        view.findViewById(R.id.logout).setVisibility(View.INVISIBLE);
        view.findViewById(R.id.logout).setEnabled(false);
        b=(ImageButton)view.findViewById(R.id.home);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(login_screen.this,action_screen.class);
                startActivity(i);
            }
        });
        TextView textView=(TextView)view.findViewById(R.id.tab_name);
        textView.setText("Auth Screen");
        Typeface typeface= ResourcesCompat.getFont(getApplicationContext(),R.font.berkshireswash);
        textView.setTextColor(getResources().getColor(R.color.white));
        btn = (CircularProgressButton) findViewById(R.id.btn_id);

        use=findViewById(R.id.username);
         pass=findViewById(R.id.password);
         id=findViewById(R.id.userid);
        progressDialog=new ProgressDialog(this);


        // shared preference to remember the last login
        pref = PreferenceManager.getDefaultSharedPreferences(this);
         editor = pref.edit();
        /**btn.setText("Login");
        btn.setElevation(5.0f);
        //btn.setFinalCornerRadius(5.0f);
        btn.setBackgroundColor(getResources().getColor(R.color.white));
        btn.setPaddingProgress(0.0f);
        btn.setSpinningBarWidth(4.0f);
        btn.setSpinningBarColor(R.color.colorPrimaryDark);*/

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


                trouble_loading();
            }
        };
        spannableString.setSpan(clickableSpan,0,15, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString1.setSpan(clickableSpan1,0,16,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        forget_password.setText(spannableString);
        forget_password.setMovementMethod(LinkMovementMethod.getInstance());
        trouble.setText(spannableString1);
        trouble.setMovementMethod(LinkMovementMethod.getInstance());




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

    public void trouble_loading()
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
        final EditText complain_username=dialog.findViewById(R.id.complain_name);

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

                complain=complain_text.getText().toString();
                complain_name=complain_username.getText().toString();


                complain=complain+"-by "+complain_name;

               final DatabaseReference databaseReference_complain_counter=FirebaseDatabase.getInstance().getReference().child("complain").child("counter");


                databaseReference_complain_counter.addListenerForSingleValueEvent(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                       String counter=dataSnapshot.getValue().toString();
                       int count=dataSnapshot.getValue().hashCode();

                       DatabaseReference databaseReference_complain=FirebaseDatabase.getInstance().getReference().child("complain").child(counter);
                       databaseReference_complain.setValue(complain);

                       count++;

                       databaseReference_complain_counter.setValue(count);
                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError databaseError) {

                   }
               });


            }});
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setAttributes(lp);
    }
    @Override
    protected void onStart() {
        super.onStart();
        btn.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.roundbutton));





        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressDialog.setCancelable(false);
                progressDialog.setTitle("Please Wait...");
                progressDialog.setMessage("Checking your email address and password");
                //progressDialog.show();


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
                {  //btn.startAnimation();
                    startanim();

                    firebaseAuth.signInWithEmailAndPassword(username,password)
                            .addOnCompleteListener(login_screen.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful())
                                    {
                                        progressDialog.setMessage("Checking your SGM ID");

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

                                                            progressDialog.setMessage("Checking Authentication");

                                                            if(username.equals(dataSnapshot.getValue().toString()))
                                                            {
                                                                databaseReference2=FirebaseDatabase.getInstance().getReference().child("teacher_info").child(userid).child("type");

                                                                databaseReference2.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                    @Override
                                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                                                        CheckBox checkBox=findViewById(R.id.remember_checkbox);

                                                                        if(dataSnapshot.getValue().toString().equals("administrator"))
                                                                        {


                                                                            editor.putInt("type",1);                       // for administrator the value of type in shared preference is 1


                                                                            Intent i=new Intent(login_screen.this,admin_management.class);
                                                                            editor.putString("userid",userid);
                                                                            editor.putString("mail",username);
                                                                            editor.commit();
                                                                            startActivity(i);
                                                                            progressDialog.dismiss();


                                                                        }
                                                                        else if(dataSnapshot.getValue().toString().equals("Teacher")) {

                                                                            editor.putInt("type", 2);

                                                                            Intent i=new Intent(login_screen.this,Teacher_management.class);
                                                                            editor.putString("userid",userid);
                                                                            editor.putString("mail",username);
                                                                            editor.commit();
                                                                            startActivity(i);
                                                                            progressDialog.dismiss();

                                                                        }
                                                                        else if(dataSnapshot.getValue().toString().equals("Management")) {

                                                                            editor.putInt("type", 3);

                                                                            Intent i=new Intent(login_screen.this,Profile_mgmt .class);
                                                                           Bitmap b= getBitmapFromDrawable(R.drawable.tick);
                                                                           btn.doneLoadingAnimation(R.color.black, BitmapFactory.decodeResource(getResources(),R.drawable.tick));
                                                                           Log.d("mess","logging");
                                                                            editor.putString("userid",userid);
                                                                            editor.putString("mail",username);
                                                                            editor.commit();
                                                                            //i.putExtra("mail",username);
                                                                            //i.putExtra("id",userid);
                                                                            startActivity(i);
                                                                            progressDialog.dismiss();

                                                                        }
                                                                    }

                                                                    @Override
                                                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                                                       btn.revertAnimation(new OnAnimationEndListener() {
                                                                            @Override
                                                                            public void onAnimationEnd() {
                                                                                btn.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.roundbutton));
                                                                            }
                                                                        }
                                                                       );

                                                                    }
                                                                });

                                                            }
                                                        }

                                                        @Override
                                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                                            btn.revertAnimation(new OnAnimationEndListener() {
                                                                @Override
                                                                public void onAnimationEnd() {
                                                                    btn.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.roundbutton));
                                                                }
                                                            });

                                                        }
                                                    });
                                                }
                                                else
                                                    Toast.makeText(getApplicationContext(),"Incorrect SGM ID",Toast.LENGTH_LONG).show();
                                                btn.revertAnimation(new OnAnimationEndListener() {
                                                    @Override
                                                    public void onAnimationEnd() {
                                                        btn.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.roundbutton));
                                                    }
                                                });
                                                //progressDialog.dismiss();

                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                                btn.revertAnimation(new OnAnimationEndListener() {
                                                    @Override
                                                    public void onAnimationEnd() {
                                                        btn.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.roundbutton));
                                                    }
                                                });


                                            }
                                        });
                                    }
                                    else
                                    {  btn.revertAnimation(new OnAnimationEndListener() {
                                        @Override
                                        public void onAnimationEnd() {
                                            btn.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.roundbutton));
                                        }
                                    });

                                        use.setError(null);
                                        pass.setError(null);

                                        Toast.makeText(getApplicationContext(),"Error Logging in", Toast.LENGTH_LONG).show();

                                        Toast.makeText(getApplicationContext(),"Invalid username or password",Toast.LENGTH_LONG).show();
                                        progressDialog.dismiss();

                                    }
                                }
                            });
                }
            }
        });



    }
    void startanim()
    {
        btn.startAnimation();
       // btn.setBackgroundColor(getResources().getColor(R.color.white));
        btn.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.buttonbganim));
        btn.setSpinningBarColor(R.color.black);
        btn.setSpinningBarWidth(12.0f);
        Toast.makeText(getApplicationContext(),"SEE", Toast.LENGTH_LONG).show();

    }
    public Bitmap getBitmapFromDrawable(int drawableId) {
        Bitmap bitmap = null;
        Drawable drawable = ContextCompat.getDrawable(getApplicationContext(), drawableId);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && (drawable instanceof VectorDrawable || drawable instanceof VectorDrawableCompat)) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                drawable = (DrawableCompat.wrap(drawable)).mutate();
            }

            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
        } else if (drawable instanceof BitmapDrawable) {
            bitmap = ((BitmapDrawable) drawable).getBitmap();
        }
        return bitmap;
    }
}
