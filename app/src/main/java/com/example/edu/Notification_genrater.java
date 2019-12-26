package com.example.edu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Notification_genrater extends AppCompatActivity {

    public EditText edtTitle;
    public EditText edtMessage;
    String serverKey = "key=" + "AIzaSyCqS4bc8OhDfAoJUq0sfBXZhkzEkOjSFOs";
    String FCM_API = "https://fcm.googleapis.com/fcm/send";
    String contentType = "application/json";
    final public String TAG = "NOTIFICATION TAG";

    int counter_in;
    String counter;
    String NOTIFICATION_TITLE;
    String NOTIFICATION_MESSAGE;
    String TOPIC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_genrater);

        edtTitle = findViewById(R.id.notification_title);
        edtMessage = findViewById(R.id.notification_body);
        Button btnSend = findViewById(R.id.notification_ok);
        Button btn_notice_ok=findViewById(R.id.notice_ok);

        final DatabaseReference databaseReference_counter=FirebaseDatabase.getInstance().getReference().child("notice_counter");
        databaseReference_counter.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                counter=dataSnapshot.getValue().toString();
                Toast.makeText(getApplicationContext(),counter,Toast.LENGTH_LONG).show();
                counter_in=dataSnapshot.getValue().hashCode();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        final RadioButton managemennt_radio=findViewById(R.id.notice_management_check);
        final RadioButton teacher_radio=findViewById(R.id.notice_teacher_check);
        final RadioButton admin_radio=findViewById(R.id.notice_admin_check);
        final RadioButton common_radio=findViewById(R.id.notice_common_check);

      /*  managemennt_radio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    managemennt_radio.setChecked(false);
                }
            }
        });

        teacher_radio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!teacher_radio.isChecked())
                {
                    teacher_radio.setChecked(false);
                }
            }
        });


        admin_radio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                   admin_radio.setChecked(false);
                }
            }
        });

        common_radio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    common_radio.setChecked(false);
                }
            }
        });

       */

      btn_notice_ok.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

              EditText editText = findViewById(R.id.notice_board_message);


              if (!editText.getText().toString().isEmpty()) {

                  if (managemennt_radio.isChecked() || teacher_radio.isChecked() || admin_radio.isChecked() || common_radio.isChecked()) {

                      if (managemennt_radio.isChecked()) {
                          DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("notice_management").child(counter);
                          databaseReference.setValue(editText.getText().toString() + " - " + new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
                      }

                      if (teacher_radio.isChecked()) {
                          DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("notice_teacher").child(counter);
                          databaseReference.setValue(editText.getText().toString() + " - " + new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
                      }


                      if (admin_radio.isChecked()) {
                          DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("notice_admin").child(counter);
                          databaseReference.setValue(editText.getText().toString() + " - " + new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
                      }
                      if (common_radio.isChecked()) {
                          DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("notice").child(counter);
                          databaseReference.setValue(editText.getText().toString() + " - " + new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
                      }

                      counter_in = counter_in + 1;


                      databaseReference_counter.setValue(counter_in);


                      Toast.makeText(getApplicationContext(),"Message successfully send, Thank you",Toast.LENGTH_LONG).show();

                      finish();

                  } else

                      Toast.makeText(getApplicationContext(), "Please select atleast one Check box", Toast.LENGTH_LONG).show();
              }
              else
                  Toast.makeText(getApplicationContext(),"Please enter message",Toast.LENGTH_LONG).show();
          }
      });


        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TOPIC = "/topics/hello"; //topic must match with what the receiver subscribed to
                NOTIFICATION_TITLE = edtTitle.getText().toString();
                NOTIFICATION_MESSAGE = edtMessage.getText().toString();

                JSONObject notification = new JSONObject();
                JSONObject notificationBody = new JSONObject();
                try {
                    notificationBody.put("title", NOTIFICATION_TITLE);
                    notificationBody.put("message", NOTIFICATION_MESSAGE);

                    notification.put("to", TOPIC);
                    notification.put("data", notificationBody);
                } catch (JSONException e) {
                    Log.e(TAG, "onCreate: " + e.getMessage());
                }

                if (!NOTIFICATION_TITLE.isEmpty()) {

                    if (!NOTIFICATION_MESSAGE.isEmpty()) {

                        sendNotification(notification);
                    }
                    else
                        Toast.makeText(getApplicationContext(),"Message field can't be empty",Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(getApplicationContext(),"Title field can't be empty",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void sendNotification(JSONObject notification) {

        Log.e("kc","kj");

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,FCM_API,notification,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(TAG, "onResponse: " + response.toString());
                        edtTitle.setText("");
                        edtMessage.setText("");

                        Toast.makeText(getApplicationContext(),"Notification successfully send, thank you",Toast.LENGTH_LONG).show();

                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Request error", Toast.LENGTH_LONG).show();
                        Log.i(TAG, "onErrorResponse: Didn't work");
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", serverKey);
                params.put("Content-Type", contentType);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        int socketTimeout = 1000 * 60;   // 60 seconds
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsonObjectRequest.setRetryPolicy(policy);
        requestQueue.add(jsonObjectRequest);
    }
}