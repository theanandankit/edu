package com.example.edu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;

public class new_registration extends AppCompatActivity {

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    public static DatabaseReference databaseReference, databaseReference_counter;

    public static String counter;
    public static ProgressDialog progressDialog;
    public static int user_counter;
    public static Spinner spinner;
    ArrayList<String> teacher_type = new ArrayList<>();
    public static TextView user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_registration);
        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.general_actionbar);
        getSupportActionBar().setElevation(10);
        View view = getSupportActionBar().getCustomView();
        TextView textView=(TextView)view.findViewById(R.id.tab_name);
        textView.setText("New Registration");
        Typeface typeface= ResourcesCompat.getFont(getApplicationContext(),R.font.berkshireswash);
        textView.setTextColor(getResources().getColor(R.color.white));

        progressDialog = new ProgressDialog(new_registration.this);

        progressDialog.setMessage("Loading.... Creating SGM ID");

        progressDialog.show();

        progressDialog.setCanceledOnTouchOutside(false);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);


        user = findViewById(R.id.user);
        final EditText pass = findViewById(R.id.pass);
        final EditText uname = findViewById(R.id.user_name);
        final EditText ubatch = findViewById(R.id.user_batch);
        final EditText ucontact = findViewById(R.id.user_contact);
        final EditText uemail = findViewById(R.id.email);
        spinner = findViewById(R.id.type);

        teacher_type.add("Teacher");
        teacher_type.add("Management");
        teacher_type.add("administrator");

        ArrayAdapter aaa = new ArrayAdapter(new_registration.this, android.R.layout.simple_spinner_item, teacher_type);
        aaa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(aaa);

        Button regi = findViewById(R.id.register);

        databaseReference_counter = FirebaseDatabase.getInstance().getReference().child("teacher_info").child("counter");

        databaseReference_counter.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                counter = dataSnapshot.getValue().toString();
                user_counter = dataSnapshot.getValue().hashCode();
                user.setText("SMG" + counter);
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        regi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = user.getText().toString();
                final String password = pass.getText().toString();
                final String uuname = uname.getText().toString();
                final String email = uemail.getText().toString();
                final String uubatch = ubatch.getText().toString();
                String uucontact = ucontact.getText().toString();
                String type = spinner.getSelectedItem().toString();

                if (email.isEmpty()) {
                    Toast.makeText(new_registration.this, "please Enter the Username", Toast.LENGTH_LONG).show();
                    return;
                }
                if (password.isEmpty()) {
                    Toast.makeText(new_registration.this, "please Enter the password", Toast.LENGTH_LONG).show();
                    return;
                }

                if (uuname.isEmpty()) {
                    Toast.makeText(new_registration.this, "please Enter the Name", Toast.LENGTH_LONG).show();
                    return;
                }

                if (uubatch.isEmpty()) {
                    Toast.makeText(new_registration.this, "please Enter the batch", Toast.LENGTH_LONG).show();
                    return;
                }
                if (uucontact.isEmpty()) {

                    Toast.makeText(new_registration.this, "please Enter the contact no.", Toast.LENGTH_LONG).show();
                    return;
                }
                firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new_registration.this, new OnCompleteListener<AuthResult>() {

                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()) {
                                    Toast.makeText(new_registration.this, "user is registered successfully", Toast.LENGTH_LONG).show();

                                    show_new_regi_popup("SGM"+counter);

                                } else {
                                    Toast.makeText(new_registration.this, "Something went gone \n Please try again later", Toast.LENGTH_LONG).show();
                                }
                            }
                        });

                new_user userr = new new_user(uuname, email, uubatch, uucontact, type);

                databaseReference = FirebaseDatabase.getInstance().getReference().child("teacher_info").child("SGM" + counter);

                databaseReference.setValue(userr);

                user_counter++;

                databaseReference_counter.setValue(user_counter);

            }
        });
    }

    public class new_user implements Serializable {
        public String user_name;
        public String email;
        public String batch;
        public String contact_no;
        public String actual_day;
        public String present;
        public String extra_day;
        public String type;

        public new_user() {
        }

        public new_user(String user_name, String email, String batch, String contact_no, String type) {
            this.user_name = user_name;
            this.batch = batch;
            this.email = email;
            this.type = type;
            this.contact_no = contact_no;
            actual_day = "0";
            present = "0";
            extra_day = "0";
        }

        public String getUser_name() {
            return user_name;
        }

        public String getEmail() {
            return email;
        }

        public String getBatch() {
            return batch;
        }

        public String getContact_no() {
            return contact_no;
        }

        public String getActual_day() {
            return actual_day;
        }

        public String getPresent() {
            return present;
        }

        public String getExtra_day() {
            return extra_day;
        }

        public String getType() {
            return type;
        }
    }

    public void show_new_regi_popup(String id)
    {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.new_regi_popup_message);
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        TextView textView=dialog.findViewById(R.id.new_regi_confirm);
        Button button=dialog.findViewById(R.id.done);

        textView.setText(id);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
                finish();
            }
        });

        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setAttributes(lp);

    }

}