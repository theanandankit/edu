package com.example.edu;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class Management_schedule extends AppCompatActivity {
    String s;
    DatabaseReference databaseReference;
    ArrayList<String> teacher_name=new ArrayList<>();
    Spinner spinner;
    CardView mgmt_card1,mgmt_card2,mgmt_card3,mgmt_card4,mgmt_card5,mgmt_card6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management_schedule);
        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.general_actionbar);
        getSupportActionBar().setElevation(10);
        View view = getSupportActionBar().getCustomView();
        TextView textView=(TextView)view.findViewById(R.id.tab_name);
        textView.setText("Set schedule");
        mgmt_card1=(CardView)findViewById(R.id.mgmt_card1);
        mgmt_card2=(CardView)findViewById(R.id.mgmt_card2);
        mgmt_card3=(CardView)findViewById(R.id.mgmt_card3);
        mgmt_card4=(CardView)findViewById(R.id.mgmt_card4);
        mgmt_card5=(CardView)findViewById(R.id.mgmt_card5);
        mgmt_card6=(CardView)findViewById(R.id.mgmt_card6);
        mgmt_card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDialog(R.id.monday1,R.id.monday2,R.id.monday_uid1,R.id.monday_uid2,R.id.monday_batch1,R.id.monday_batch2);
            }
        });
        mgmt_card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDialog(R.id.tuesday1,R.id.tuesday2,R.id.tuesday_uid1,R.id.tuesday_uid2,R.id.tuesday_batch1,R.id.tuesday_batch2);
            }
        });
        mgmt_card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDialog(R.id.wednesday1,R.id.wednesday2,R.id.wednesday_uid1,R.id.wednesday_uid2,R.id.wednesday_batch1,R.id.wednesday_batch2);
            }
        });
        mgmt_card4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDialog(R.id.thursday1,R.id.thursday2,R.id.thursday_uid1,R.id.thursday_uid2,R.id.thursday_batch1,R.id.thursday_batch2);
            }
        });
        mgmt_card5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDialog(R.id.friday1,R.id.friday2,R.id.friday_uid1,R.id.friday_uid2,R.id.friday_batch1,R.id.friday_batch2);
            }
        });
        mgmt_card6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDialog(R.id.saturday1,R.id.saturday2,R.id.saturday_uid1,R.id.saturday_uid2,R.id.saturday_batch1,R.id.saturday_batch2);
            }
        });

    }
    public void showCustomDialog(int name1,int name2,int uid1,int uid2 , int batch1, int batch2 )
    {
        final TextView m1=(TextView)findViewById(name1);
        final TextView m2=(TextView)findViewById(name2);
         final TextView u1=(TextView)findViewById(uid1);
        final TextView u2=(TextView)findViewById(uid2);
        final TextView b1=(TextView)findViewById(batch1);
        final TextView b2=(TextView)findViewById(batch2);
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.mgmt_dialog);
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;


        (dialog.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.bt_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String member1_name,uid1,batch1,member2_name,uid2,batch2;
                TextInputLayout member1=(TextInputLayout)findViewById(R.id.set_member1_name);
                TextInputLayout member2=(TextInputLayout)findViewById(R.id.set_member2_name);
                TextInputLayout Uid1=(TextInputLayout)findViewById(R.id.set_uid1);
                TextInputLayout Uid2=(TextInputLayout)findViewById(R.id.set_uid2);
                TextInputLayout Batch1=(TextInputLayout)findViewById(R.id.set_batch1);
                TextInputLayout Batch2=(TextInputLayout)findViewById(R.id.set_batch2);
                member1_name=member1.getEditText().getText().toString();
                uid1=Uid1.getEditText().getText().toString();
                batch1=Batch1.getEditText().getText().toString();
                member2_name=member2.getEditText().getText().toString();
                uid2=Uid2.getEditText().getText().toString();
                batch2=Batch2.getEditText().getText().toString();
                m1.setText(member1_name);
                m2.setText(member2_name);
                u1.setText(uid1);
                u2.setText(uid2);
                b1.setText(batch1);
                b2.setText(batch2);

            }
        });
        dialog.show();
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
}
