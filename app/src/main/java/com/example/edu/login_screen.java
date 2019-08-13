package com.example.edu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class login_screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        final EditText use=findViewById(R.id.username);
        final EditText pass=findViewById(R.id.password);

        Button login=findViewById(R.id.login);

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

                else
                {
                    Toast.makeText(login_screen.this,"INVALID INPUT",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
