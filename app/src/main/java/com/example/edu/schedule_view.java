package com.example.edu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.edu.adapter.schedule_adapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class schedule_view extends AppCompatActivity {

    ArrayList<String> day = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_view);
        day.add("Monday");
        day.add("Tuesday");
        day.add("Wednesday");
        day.add("Thursday");
        day.add("Friday");
        day.add("saturday");

        Spinner spinner = findViewById(R.id.schedule_view_spinner);
        final ArrayList<Item> student_list=new ArrayList<>();
        final ListView listView=findViewById(R.id.schedule_view_listview);
        final TextView management_name1=findViewById(R.id.schedule_management_name1);
        final TextView management_name2=findViewById(R.id.schedule_management_name2);



        ArrayAdapter aaa = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, day);
        aaa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(aaa);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                DatabaseReference databaseReference_management=FirebaseDatabase.getInstance().getReference().child("Schedule").child(parent.getItemAtPosition(position).toString()).child("Management");

                databaseReference_management.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        Management_class mng=dataSnapshot.getValue(Management_class.class);

                        management_name1.setText(mng.getName1()+"\n("+mng.getBatch1()+")");

                        management_name2.setText(mng.getName2()+"\n("+mng.getBatch2()+")");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Schedule").child(parent.getItemAtPosition(position).toString()).child("Teachers");

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(!student_list.isEmpty())
                        {
                            student_list.clear();
                        }

                        for(final DataSnapshot postSnapshot : dataSnapshot.getChildren())
                        {
                            schedule_management.Teacher teacher=postSnapshot.getValue(schedule_management.Teacher.class);
                            student_list.add(new Item(postSnapshot.getKey(),teacher.getName()+"\n("+teacher.getBatch()+")",teacher.getSubject()));

                        }

                        schedule_adapter myAdapter=new schedule_adapter(schedule_view.this,R.layout.schedule_list_adapter,student_list);
                        listView.setAdapter(myAdapter);
                        setListViewHeightBasedOnChildren(listView);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public static class Item {
        String class_name;
        String student_name;
        String subject_name;

        public Item(String class_name, String student_name, String subject_name) {
            this.class_name = class_name;
            this.student_name = student_name;
            this.subject_name = subject_name;
        }

        public String getClass_name() {
            return class_name;
        }

        public void setClass_name(String class_name) {
            this.class_name = class_name;
        }

        public String getStudent_name() {
            return student_name;
        }

        public void setStudent_name(String student_name) {
            this.student_name = student_name;
        }

        public String getSubject_name() {
            return subject_name;
        }

        public void setSubject_name(String subject_name) {
            this.subject_name = subject_name;
        }
    }

    public static void setListViewHeightBasedOnChildren(ListView listView)
    {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight=0;
        View view = null;

        for (int i = 0; i < listAdapter.getCount(); i++)
        {
            view = listAdapter.getView(i, view, listView);

            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth,
                        ViewGroup.LayoutParams.MATCH_PARENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();

        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + ((listView.getDividerHeight()) * (listAdapter.getCount()));

        listView.setLayoutParams(params);
        listView.requestLayout();

    }
}
