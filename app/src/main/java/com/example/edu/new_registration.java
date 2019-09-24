package com.example.edu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class new_registration extends AppCompatActivity {

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    DatabaseReference databaseReference,databaseReference_counter;

    public static String counter;
    public static int user_counter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_registration);


        final EditText user = findViewById(R.id.user);
        final EditText pass = findViewById(R.id.pass);
        final EditText uname=findViewById(R.id.user_name);
        final EditText ubatch=findViewById(R.id.user_batch);
        final EditText ucontact=findViewById(R.id.user_contact);

        Button regi = findViewById(R.id.register);

        regi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String username = user.getText().toString();
                final String password = pass.getText().toString();
                String uuname=uname.getText().toString();
                String uubatch=ubatch.getText().toString();
                String uucontact=ucontact.getText().toString();

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
                                    Toast.makeText(new_registration.this, username+" is registered successfully", Toast.LENGTH_LONG).show();

                                } else {
                                    Toast.makeText(new_registration.this, "Something went gone \n Please try again later", Toast.LENGTH_LONG).show();
                                }
                            }
                        });

                new_user userr=new new_user(uuname,username,uubatch,uucontact);

                databaseReference=FirebaseDatabase.getInstance().getReference().child("teacher_info").child(counter);

                databaseReference.child("user").setValue(userr);

                user_counter++;
                databaseReference_counter.setValue(user_counter);
            }
        });

        databaseReference_counter=FirebaseDatabase.getInstance().getReference().child("teacher_info").child("counter");

        databaseReference_counter.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                counter=dataSnapshot.getValue().toString();
                user_counter=dataSnapshot.getValue().hashCode();
                Toast.makeText(new_registration.this,counter,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public class new_user{
        String user_name;
        String email;
        String batch;
        int actual_day;
        int present;
        int extra_day;
        String contact_no;


        public new_user(String user_name,String email,String batch,String contact_no)
        {
            this.user_name=user_name;
            this.batch=batch;
            this.email=email;
            actual_day=0;
            present=0;
            extra_day=0;
            this.contact_no=contact_no;
        }
    }

}