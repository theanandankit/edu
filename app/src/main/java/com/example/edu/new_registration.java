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

public class new_registration extends AppCompatActivity {

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_registration);

        final EditText user = findViewById(R.id.user);
        final EditText pass = findViewById(R.id.pass);


        Button regi = findViewById(R.id.register);

        regi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String username = user.getText().toString();
                final String password = pass.getText().toString();

                if (username.isEmpty()) {
                    Toast.makeText(new_registration.this, "please Enter the Username", Toast.LENGTH_LONG).show();
                    return;
                }
                if (password.isEmpty()) {
                    Toast.makeText(new_registration.this, "please Enter the password", Toast.LENGTH_LONG).show();
                    return;
                }

                firebaseAuth.createUserWithEmailAndPassword(username, password)
                        .addOnCompleteListener(new_registration.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()) {
                                    Toast.makeText(new_registration.this, "ok", Toast.LENGTH_LONG).show();

                                } else {
                                    Toast.makeText(new_registration.this, "not", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });

    }
}