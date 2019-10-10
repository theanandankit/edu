package com.example.edu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Show_Attendance extends AppCompatActivity {
    private GridView list;
    private DatabaseReference reference;
   private ArrayList<adaptor_class> arrayList=new ArrayList<>();
    private Show_attendance_adapter adaptor;
    int i=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show__attendance);
        list=(GridView) findViewById(R.id.list);
        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.general_actionbar);
        getSupportActionBar().setElevation(10);
        View view = getSupportActionBar().getCustomView();
        TextView textView=(TextView)view.findViewById(R.id.tab_name);
        textView.setText(R.string.status_today);
        Typeface typeface= ResourcesCompat.getFont(getApplicationContext(),R.font.berkshireswash);
        textView.setTextColor(getResources().getColor(R.color.white));
        SimpleDateFormat sdf2 = new SimpleDateFormat("EEEE");
        // String current_day = sdf2.format(new Date());
        String current_day="Wednesday";
        Date todaysDate = new Date();
        String date = "";
        DateFormat sdf1 = new SimpleDateFormat("dd-MMM-yyyy");
        date= sdf1.format(todaysDate).toString();
        i=1;



        reference=FirebaseDatabase.getInstance().getReference().child(date).child("Teacher-Attendance");
        for(i=1;i<=12;i++)
        {
            if(i!=2 && i!=4)
            {
                String s=String.valueOf(i);
                reference.child(s).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        adaptor_class obj=dataSnapshot.getValue(adaptor_class.class);
                        Log.d("teacher",obj.teacher_name);
                        arrayList.add(obj);
                        showList();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }


        }


    }
    private void showList()
    {
        Toast.makeText(getApplicationContext(),String.valueOf(arrayList.size()),Toast.LENGTH_LONG).show();
        adaptor=new Show_attendance_adapter(arrayList,getApplicationContext());
        list.setAdapter(adaptor);

    }
}
