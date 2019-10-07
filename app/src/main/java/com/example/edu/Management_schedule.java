package com.example.edu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Management_schedule extends AppCompatActivity {
    String s;
    DatabaseReference databaseReference;
    ArrayList<String> teacher_name=new ArrayList<>();
    Spinner spinner;
    CardView mgmt_card1,mgmt_card2,mgmt_card3,mgmt_card4,mgmt_card5,mgmt_card6;
    final String days[]={"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
    Management_class mgmt;

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
        SimpleDateFormat sdf2 = new SimpleDateFormat("EEEE");
        String current_day = sdf2.format(new Date());
        //String current_day="Wednesday";
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Schedule");//.child(current_day).child("Management");
        textView.setText("Set schedule");
        mgmt_card1=(CardView)findViewById(R.id.mgmt_card1);
        mgmt_card2=(CardView)findViewById(R.id.mgmt_card2);
        mgmt_card3=(CardView)findViewById(R.id.mgmt_card3);
        mgmt_card4=(CardView)findViewById(R.id.mgmt_card4);
        mgmt_card5=(CardView)findViewById(R.id.mgmt_card5);
        mgmt_card6=(CardView)findViewById(R.id.mgmt_card6);
        set_week_info();
        mgmt_card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDialog("Monday",R.id.monday1,R.id.monday2,R.id.monday_uid1,R.id.monday_uid2,R.id.monday_batch1,R.id.monday_batch2);
            }
        });
        mgmt_card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDialog("Tuesday",R.id.tuesday1,R.id.tuesday2,R.id.tuesday_uid1,R.id.tuesday_uid2,R.id.tuesday_batch1,R.id.tuesday_batch2);
            }
        });
        mgmt_card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDialog("Wednesday",R.id.wednesday1,R.id.wednesday2,R.id.wednesday_uid1,R.id.wednesday_uid2,R.id.wednesday_batch1,R.id.wednesday_batch2);
            }
        });
        mgmt_card4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDialog("Thursday",R.id.thursday1,R.id.thursday2,R.id.thursday_uid1,R.id.thursday_uid2,R.id.thursday_batch1,R.id.thursday_batch2);
            }
        });
        mgmt_card5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDialog("Friday",R.id.friday1,R.id.friday2,R.id.friday_uid1,R.id.friday_uid2,R.id.friday_batch1,R.id.friday_batch2);
            }
        });
        mgmt_card6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDialog("Saturday",R.id.saturday1,R.id.saturday2,R.id.saturday_uid1,R.id.saturday_uid2,R.id.saturday_batch1,R.id.saturday_batch2);
            }
        });

    }
    public void showCustomDialog(final String s,int name1,int name2,int uid1,int uid2 , int batch1, int batch2 )
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
                TextInputLayout member1=(TextInputLayout)dialog.findViewById(R.id.set_member1_name);
                TextInputLayout member2=(TextInputLayout)dialog.findViewById(R.id.set_member2_name);
                TextInputLayout Uid1=(TextInputLayout)dialog.findViewById(R.id.set_uid1);
                TextInputLayout Uid2=(TextInputLayout)dialog.findViewById(R.id.set_uid2);
                TextInputLayout Batch1=(TextInputLayout)dialog.findViewById(R.id.set_batch1);
                TextInputLayout Batch2=(TextInputLayout)dialog.findViewById(R.id.set_batch2);
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
                Management_class mgmt=new Management_class(member1_name,uid1,batch1,member2_name,uid2,batch2);
                databaseReference.child(s).child("Management").setValue(mgmt);
                dialog.dismiss();

            }
        });
        dialog.show();
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
    public void set_week_info()
    {
         DatabaseReference reference=FirebaseDatabase.getInstance().getReference().child("Schedule");
         for(int i=0;i<6;i++)
         { final int j=i;
             reference.child(days[i]).child("Management").addValueEventListener(new ValueEventListener() {
                 @Override
                 public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                     mgmt=dataSnapshot.getValue(Management_class.class);
                     int a;
                     if(mgmt!=null)
                       a=1;
                     else
                         a=0;
                     switch(j)
                     {
                         case 0:
                             setvalue(R.id.monday1,R.id.monday2,R.id.monday_uid1,R.id.monday_uid2,R.id.monday_batch1,R.id.monday_batch2,a);
                             break;
                         case 1:
                             setvalue(R.id.tuesday1,R.id.tuesday2,R.id.tuesday_uid1,R.id.tuesday_uid2,R.id.tuesday_batch1,R.id.tuesday_batch2,a);
                             break;
                         case 2:
                             setvalue(R.id.wednesday1,R.id.wednesday2,R.id.wednesday_uid1,R.id.wednesday_uid2,R.id.wednesday_batch1,R.id.wednesday_batch2,a);
                             break;
                         case 3:
                             setvalue(R.id.thursday1,R.id.thursday2,R.id.thursday_uid1,R.id.thursday_uid2,R.id.thursday_batch1,R.id.thursday_batch2,a);
                             break;
                         case 4:
                             setvalue(R.id.friday1,R.id.friday2,R.id.friday_uid1,R.id.friday_uid2,R.id.friday_batch1,R.id.friday_batch2,a);
                             break;
                         default:
                             setvalue(R.id.saturday1,R.id.saturday2,R.id.saturday_uid1,R.id.saturday_uid2,R.id.saturday_batch1,R.id.saturday_batch2,a);

                     }



                 }

                 @Override
                 public void onCancelled(@NonNull DatabaseError databaseError) {

                 }
             });
         }



    }
    public void setvalue(final int a,final int b, final int c, final int d, final int e,final int f,int i)
    {
        TextView mgmt_name1 = findViewById(a);
        TextView mgmt_uid1 = findViewById(b);
        TextView mgmt_batch1=findViewById(c);
        TextView mgmt_name2 = findViewById(d);
        TextView mgmt_uid2 = findViewById(e);
        TextView mgmt_batch2=findViewById(f);

        String name1="Name: ";
        String u_id1="UID: ";
        String Batch1="Batch: ";
        String name2="Name: ";
        String u_id2="UID: ";
        String Batch2="Batch: ";


        if(i==1)
        {
            name1=name1+mgmt.getName1().toUpperCase();
            u_id1=u_id1+mgmt.getUid1().toUpperCase();
            Batch1=Batch1+mgmt.getBatch1().toUpperCase();
            name2=name2+mgmt.getName2().toUpperCase();
            u_id2=u_id2+mgmt.getUid2().toUpperCase();
            Batch2=Batch2+mgmt.getBatch2().toUpperCase();

        }
        Typeface typeface= ResourcesCompat.getFont(getApplicationContext(),R.font.stylish);
        mgmt_name1.setText(name1);
        mgmt_name1.setTypeface(typeface);
        mgmt_name1.setTextColor(Color.BLACK);
        mgmt_uid1.setText(u_id1);
        mgmt_uid1.setTypeface(typeface);
        mgmt_uid1.setTextColor(Color.BLACK);
        mgmt_batch1.setText(Batch1);
        mgmt_batch1.setTypeface(typeface);
        mgmt_batch1.setTextColor(Color.BLACK);
        mgmt_name2.setText(name2);
        mgmt_name2.setTypeface(typeface);
        mgmt_name2.setTextColor(Color.BLACK);
        mgmt_uid2.setText(u_id2);
        mgmt_uid2.setTypeface(typeface);
        mgmt_uid2.setTextColor(Color.BLACK);
        mgmt_batch2.setText(Batch2);
        mgmt_batch2.setTypeface(typeface);
        mgmt_batch2.setTextColor(Color.BLACK);



    }

}
