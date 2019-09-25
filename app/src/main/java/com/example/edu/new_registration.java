package com.example.edu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class new_registration extends AppCompatActivity {

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    public static DatabaseReference databaseReference,databaseReference_counter;

    public static String counter;
    public static ProgressDialog progressDialog;
    public static int user_counter;
    public static Spinner spinner;
    ArrayList<String> teacher_type=new ArrayList<>();
    public static EditText user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_registration);

        progressDialog=new ProgressDialog(new_registration.this);

        progressDialog.setMessage("Loading.... Creating SGM ID");
        progressDialog.show();


        user = findViewById(R.id.user);
        final EditText pass = findViewById(R.id.pass);
        final EditText uname=findViewById(R.id.user_name);
        final EditText ubatch=findViewById(R.id.user_batch);
        final EditText ucontact=findViewById(R.id.user_contact);
        spinner=findViewById(R.id.type);

        teacher_type.add("Teacher");
        teacher_type.add("Management");
        teacher_type.add("administrator");

        ArrayAdapter aaa=new ArrayAdapter(new_registration.this,android.R.layout.simple_spinner_item,teacher_type);
        aaa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(aaa);

        Button regi = findViewById(R.id.register);

        databaseReference_counter=FirebaseDatabase.getInstance().getReference().child("teacher_info").child("counter");

        databaseReference_counter.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                counter=dataSnapshot.getValue().toString();
                user_counter=dataSnapshot.getValue().hashCode();
                user.setText("SMG"+counter);
                user.setEnabled(false);
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
                username=username+"@gmail.com";
                final String password = pass.getText().toString();
                String uuname=uname.getText().toString();
                String uubatch=ubatch.getText().toString();
                String uucontact=ucontact.getText().toString();
                String type=spinner.getSelectedItem().toString();

                if (username.isEmpty()) {
                    Toast.makeText(new_registration.this, "please Enter the Username", Toast.LENGTH_LONG).show();
                    return;
                }
                if (password.isEmpty()) {
                    Toast.makeText(new_registration.this, "please Enter the password", Toast.LENGTH_LONG).show();
                    return;
                }

                if(uuname.isEmpty())
                {
                    Toast.makeText(new_registration.this, "please Enter the Name", Toast.LENGTH_LONG).show();
                    return;
                }

                if(uubatch.isEmpty())
                {
                    Toast.makeText(new_registration.this, "please Enter the batch", Toast.LENGTH_LONG).show();
                    return;
                }
                if (uucontact.isEmpty())
                {

                    Toast.makeText(new_registration.this, "please Enter the contact no.", Toast.LENGTH_LONG).show();
                    return;
                }
                firebaseAuth.createUserWithEmailAndPassword(username, password)
                        .addOnCompleteListener(new_registration.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()) {
                                    Toast.makeText(new_registration.this, "user is registered successfully", Toast.LENGTH_LONG).show();

                                    finish();
                                } else {
                                    Toast.makeText(new_registration.this, "Something went gone \n Please try again later", Toast.LENGTH_LONG).show();
                                }
                            }
                        });

                new_user userr=new new_user(uuname,username,uubatch,uucontact,type);

                databaseReference=FirebaseDatabase.getInstance().getReference().child("teacher_info").child("SGM"+counter);

                databaseReference.setValue(userr);

                user_counter++;

                databaseReference_counter.setValue(user_counter);
            }
        });
    }
    public class new_user{
       public String user_name;
        public String email;
       public String batch;
       public String contact_no;
       public String actual_day;
       public String present;
       public String extra_day;
       public String type;


        public new_user(String user_name,String email,String batch,String contact_no,String type)
        {
            this.user_name=user_name;
            this.batch=batch;
            this.email=email;
            this.type=type;
            this.contact_no=contact_no;
            actual_day="0";
            present="0";
            extra_day="0";
        }
    }
    @Override
    public void onBackPressed() {
        finish();
    }

}