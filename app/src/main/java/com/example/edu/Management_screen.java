package com.example.edu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

public class Management_screen extends AppCompatActivity {


    public static ArrayList<adaptor_class> class_name=new ArrayList<>();
    public static ListView listView;
    public static int p;
    private myadaptor adaptor;
    ArrayList<String> list_of_student=new ArrayList<>();
    DatabaseReference databaseReference;
    DatabaseReference reference;
     public static int a;
     Dialog other_member, mgmt_substitute_popup;
    HashMap<String,String> attendance = new HashMap<>();
    HashMap<String,String> substitute_list = new HashMap<>();
    public static Teacher_management.Teacher attendance_teacher_info;
    static String name1;
    static String uid2;
    static String uid1;
    static String name2;
    static RadioButton yes,no,cur_sub_mem_yes,oth_sub_mem_yes,oth_sub_mem_no,cur_sub_mem_no,oth_sub_sent, oth_sub_not_sent;
    static Button done,done1;
    static EditText substitute_id, substitute1_id, member_id;
    String Uid,Uid2;
    static String current_day,date;
    int flag;
    Dialog dialog;
    SharedPreferences pref;
    FirebaseAuth auth;
    FirebaseAuth.AuthStateListener authStateListener;

    String teacher,batch,status,id,username, text;
    int k=0;
    int count;
    int c;
    ImageButton b1;
    Button logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management_screen);
        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.actionbar_layout);
        getSupportActionBar().setElevation(10);
        View view = getSupportActionBar().getCustomView();
        b1=(ImageButton)view.findViewById(R.id.home);
        logout=(Button)view.findViewById(R.id.logout);
        auth=FirebaseAuth.getInstance();
        authStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user=firebaseAuth.getCurrentUser();
                if(user==null)
                {
                    Intent i=new Intent(Management_screen.this, action_screen.class);
                    startActivity(i);
                }

            }
        };
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Logging you out", Toast.LENGTH_SHORT).show();
                auth.signOut();

            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Management_screen.this,Profile_mgmt.class);
                startActivity(i);
            }
        });

        Calendar localCalendar = Calendar.getInstance(TimeZone.getDefault());

        member_sgmid attendance_list=new member_sgmid();

        list_of_student=attendance_list.spinner_setter();
        other_member=new Dialog(Management_screen.this);
        other_member.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        other_member.setContentView(R.layout.other_member);
        other_member.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        mgmt_substitute_popup=new Dialog(Management_screen.this);
        mgmt_substitute_popup.setContentView(R.layout.mgmt_substitute_popup);
        mgmt_substitute_popup.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);



        done=other_member.findViewById(R.id.done);
        yes=other_member.findViewById(R.id.yes);
        no=other_member.findViewById(R.id.no);
        done1=mgmt_substitute_popup.findViewById(R.id.done);
        cur_sub_mem_yes=mgmt_substitute_popup.findViewById(R.id.yes);
        cur_sub_mem_no=mgmt_substitute_popup.findViewById(R.id.no);
        oth_sub_sent=other_member.findViewById(R.id.oth_sub_sent);
        oth_sub_not_sent=other_member.findViewById(R.id.oth_sub_not_sent);
        oth_sub_mem_yes=other_member.findViewById(R.id.is_sgm_member);
        oth_sub_mem_no=other_member.findViewById(R.id.not_sgm_member);
        substitute_id=other_member.findViewById(R.id.substitute_id);
        substitute1_id=mgmt_substitute_popup.findViewById(R.id.substitute1_id);
        member_id=other_member.findViewById(R.id.member_id);





        pref= PreferenceManager.getDefaultSharedPreferences(Management_screen.this);
        id=pref.getString("userid", "0");
        if(!id.equals("0"))
        {
           username=FirebaseDatabase.getInstance().getReference().child(id).child("user_name").toString();
           Toast.makeText(getApplicationContext(),id,Toast.LENGTH_SHORT).show();
        }



        listView= findViewById(R.id.list_id);
        SimpleDateFormat sdf2 = new SimpleDateFormat("EEEE");
       current_day = sdf2.format(new Date());
        //final String current_day="Monday";
        final Date todaysDate = new Date();

        DateFormat sdf1 = new SimpleDateFormat("dd-MMM-yyyy");
     date= sdf1.format(todaysDate).toString();
     if(!current_day.equals("Sunday"))
     {
         if(current_day.equals("Saturday"))
             count=8;
         else
             count=10;
     }
     loadData();




        reference=FirebaseDatabase.getInstance().getReference().child(date).child("Teacher-Attendance");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild("set"))
                {   System.out.println("set");
                    Intent i=new Intent(Management_screen.this,Show_Attendance.class);
                    startActivity(i);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });






        Button b=(Button)view.findViewById(R.id.submit2);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                FirebaseDatabase.getInstance().getReference().child("Dates").child(date).setValue(date);

                reference.child("set").setValue("1");
                for(final String Uid: attendance.keySet())
                {  final String a=attendance.get(Uid);
                   final DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference().child("teacher_info").child(Uid).child("actual_day");
                    databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                           /** Teacher_management.Teacher teacher=dataSnapshot.getValue(Teacher_management.Teacher.class);
                           System.out.println(teacher.getUser_name());
                           Map<String, Object> map = new HashMap<>();
                            int b=Integer.parseInt(teacher.getActual_day())+1;
                            int p=Integer.parseInt(teacher.getPresent())+1;
                            map.put("actual_day", String.valueOf(b));*

                            //if(attendance.get(Uid)==1)
                               // map.put("present",String.valueOf(p));
                            databaseReference.updateChildren(map);
                            map.clear();*/
                           p=Integer.parseInt(dataSnapshot.getValue().toString());
                           System.out.println(p);
                           p=p+1;
                            update(p,databaseReference1);

                            System.out.println(dataSnapshot.getValue().hashCode());

                           return;
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    final DatabaseReference databaseReference2=FirebaseDatabase.getInstance().getReference().child("teacher_info").child(Uid).child("present");
                    databaseReference2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            /** Teacher_management.Teacher teacher=dataSnapshot.getValue(Teacher_management.Teacher.class);
                             System.out.println(teacher.getUser_name());
                             Map<String, Object> map = new HashMap<>();
                             int b=Integer.parseInt(teacher.getActual_day())+1;
                             int p=Integer.parseInt(teacher.getPresent())+1;
                             map.put("actual_day", String.valueOf(b));*

                             //if(attendance.get(Uid)==1)
                             // map.put("present",String.valueOf(p));
                             databaseReference.updateChildren(map);
                             map.clear();*/
                            if(a.equals("1"))
                            {
                                p=Integer.parseInt(dataSnapshot.getValue().toString());
                                System.out.println(p);
                                p=p+1;
                                update(p,databaseReference2);

                                System.out.println(dataSnapshot.getValue().hashCode());

                            }

                            return;
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

  }
                attendance.clear();
                showOtherMemberPopup();






        }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                showCustomDialog(position);


                    //Toast.makeText(getApplicationContext(),obj.comment,Toast.LENGTH_LONG).show();
               /* View v=listView.getChildAt(position-listView.getFirstVisiblePosition());
                TextView comm=(TextView)v.findViewById(R.id.com1);
                comm.setText(status);
               // reference.setValue()*/


            }
        });
        //showLoadData();




            class_name.clear();
            for (a = 1;a <=12; ++a)
            {


                if (!(a == 2 || a == 4))
                {
                   final String classes;


                    if (a == 1 || a == 3)
                        classes = a + "-" + (a + 1);
                    else
                        classes = Integer.toString(a);
                    Log.d("class",String.valueOf(a));
                    Log.d("class:",Integer.toString(a));
                    databaseReference = FirebaseDatabase.getInstance().getReference().child("Schedule").child(current_day).child("Teachers").child(String.valueOf(a));

                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                            schedule_management.Teacher teach = dataSnapshot.getValue(schedule_management.Teacher.class);

                            String name = teach.getName();


                            String uid = teach.getUid();
                            String batch = teach.getBatch();


                            String comment = "Take Attendance";
                            adaptor_class adaptorClass = new adaptor_class(name, uid, batch, classes, comment);
                            class_name.add(adaptorClass);

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
        Toast.makeText(getApplicationContext(),String.valueOf(class_name.size()),Toast.LENGTH_LONG).show();
        adaptor=new myadaptor(class_name,getApplicationContext());
        listView.setAdapter(adaptor);

    }
    @Override
    public void onRestart() {
        super.onRestart();

        myadaptor myad=new myadaptor(class_name,this);
        listView.setAdapter(myad);
    }
    public void showCustomDialog(final int i)
    {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.subtitute_popup);
        dialog.setCancelable(true);
        final String key=class_name.get(i).getUid().toUpperCase();
        System.out.println(key);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        final RadioButton present=(RadioButton) dialog.findViewById(R.id.present);
        final RadioButton absent=(RadioButton)dialog.findViewById(R.id.absent);
        final CheckBox check_member=(CheckBox)dialog.findViewById(R.id.check_member);
        final CheckBox check_not_member=(CheckBox)dialog.findViewById(R.id.check_not_member);
        final TextInputLayout name=(TextInputLayout)dialog.findViewById(R.id.name);
        final TextInputLayout Batch=(TextInputLayout)dialog.findViewById(R.id.Batch);
        final RadioButton substitute_sent=(RadioButton)dialog.findViewById(R.id.substitute_sent);
        final RadioButton substitute_not_sent=(RadioButton)dialog.findViewById(R.id.substitute_not_sent);
        final Spinner spinner=(Spinner)dialog.findViewById(R.id.attendance_spinner);
        final ExtendedFloatingActionButton close=(ExtendedFloatingActionButton)dialog.findViewById(R.id.bt_close);



        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        spinner.setEnabled(false);
        ArrayAdapter aaa = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item,list_of_student);
        aaa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(aaa);

        check_member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                spinner.setEnabled(true);
                name.setEnabled(false);
                check_not_member.setChecked(false);
                Batch.setEnabled(false);

            }
        });

        check_not_member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                spinner.setEnabled(false);
                check_member.setChecked(false);
                name.setEnabled(true);
                Batch.setEnabled(true);
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                member_sgmid mm=new member_sgmid();

                final String id=mm.get_uid(adapterView.getItemAtPosition(i).toString());

                DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child("teacher_info").child(id);

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        attendance_teacher_info=dataSnapshot.getValue(Teacher_management.Teacher.class);

                        name.getEditText().setText(attendance_teacher_info.getUser_name());

                        Batch.getEditText().setText(attendance_teacher_info.getBatch());

                        name.setEnabled(false);

                        Batch.setEnabled(false);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Button submit=(Button)dialog.findViewById(R.id.submit);
        present.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(present.isChecked())
                {
                    substitute_sent.setEnabled(false);
                    substitute_not_sent.setEnabled(false);
                   check_member.setEnabled(false);
                   check_not_member.setEnabled(false);
                   spinner.setEnabled(false);
                   name.setEnabled(false);
                   Batch.setEnabled(false);
                }
            }
        });
        absent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(absent.isChecked())
                {   substitute_sent.setEnabled(true);
                substitute_not_sent.setEnabled(true);
                    check_member.setEnabled(true);
                    check_not_member.setEnabled(true);
                    spinner.setEnabled(true);
                    name.setEnabled(true);
                    Batch.setEnabled(true);
                }
            }
        });
        substitute_not_sent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //substitute_not_sent.setEnabled(true);
                //substitute_sent.setEnabled(true);
                check_member.setEnabled(false);
                check_not_member.setEnabled(false);
                spinner.setEnabled(false);
                name.setEnabled(false);
                Batch.setEnabled(false);

            }
        });
        substitute_sent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //substitute_not_sent.setEnabled(true);
                //substitute_sent.setEnabled(true);
                check_member.setEnabled(true);
                check_not_member.setEnabled(true);
                spinner.setEnabled(true);
                name.setEnabled(true);
                Batch.setEnabled(true);

            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                teacher=name.getEditText().getText().toString();
                batch=Batch.getEditText().getText().toString();



                if(present.isChecked())
                {  c++;
                    status="Present";
                    attendance.put(key,"1");
                    adaptor_class obj=class_name.get(i);
                    dialog.dismiss();
                    obj.comment=status;
                    adaptor.notifyDataSetChanged();
                    int x=obj.getClass1().indexOf('-');
                    if(x>0)
                        reference.child(String.valueOf(obj.getClass1().charAt(0))).setValue(obj);
                    else
                        reference.child(String.valueOf(obj.getClass1())).setValue(obj);
                }

               else if(absent.isChecked())
                {   c++;
                    attendance.put(key,"0");
                    if(substitute_sent.isChecked() && !substitute_not_sent.isChecked())
                    {
                        status="Substitute sent";
                        dialog.dismiss();
                        adaptor_class obj=class_name.get(i);
                        obj.comment=status;
                        adaptor.notifyDataSetChanged();
                        int x=obj.getClass1().indexOf('-');
                        if(x>0)
                        reference.child(String.valueOf(obj.getClass1().charAt(0))).setValue(obj);
                        else
                            reference.child(String.valueOf(obj.getClass1())).setValue(obj);
                        dialog.dismiss();
                    }

                    else if(!substitute_sent.isChecked() && substitute_not_sent.isChecked())
                    {
                        status="Substitute not sent";
                        dialog.dismiss();
                        adaptor_class obj=class_name.get(i);
                        obj.comment=status;
                        adaptor.notifyDataSetChanged();
                        int x=obj.getClass1().indexOf('-');
                        if(x>0)
                            reference.child(String.valueOf(obj.getClass1().charAt(0))).setValue(obj);
                        else
                            reference.child(String.valueOf(obj.getClass1())).setValue(obj);
                        dialog.dismiss();
                    }

                    else Toast.makeText(getApplicationContext(),"Enter valid options",Toast.LENGTH_LONG).show();
                }
               else
                   Toast.makeText(getApplicationContext(),"Enter valid options",Toast.LENGTH_LONG).show();
              Toast.makeText(getApplicationContext(),status,Toast.LENGTH_LONG).show();



            }
        }
       );
        dialog.show();
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return;

    }

    private void showLoadData()
    {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.loading_data);
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    @Override
    protected void onResume() {
        super.onResume();
        myadaptor myAdapter=new myadaptor(class_name,getApplicationContext());
        listView.setAdapter(myAdapter);
    }

    public void update(int a,DatabaseReference d)
    {
        d.setValue(String.valueOf(a));
    }
     public   void showOtherMemberPopup() {

             member_id.setEnabled(false);



         final RadioGroup r1 = other_member.findViewById(R.id.oth_sub_member_or_not);
         final RadioGroup r = other_member.findViewById(R.id.oth_sub_sent_not_sent);
         enableOrdisable(r1, false);
         enableOrdisable(r,false);


         substitute_id.setEnabled(false);

         yes.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if (yes.isChecked()) {

                     r.clearCheck();
                     r1.clearCheck();
                     enableOrdisable(r,false);
                     enableOrdisable(r1,false);

                     member_id.setEnabled(true);


                 }
             }
         });
         no.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 member_id.setEnabled(false);
                 enableOrdisable(r,true);


             }
         });
         oth_sub_sent.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 enableOrdisable(r1,true);
                 substitute_id.setEnabled(true);
             }
         });
         oth_sub_not_sent.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 r1.setEnabled(false);
                 substitute_id.setEnabled(true);
                 r1.clearCheck();

             }
         });


         done.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if (flag != 0) {
                     FirebaseDatabase.getInstance().getReference().child("teacher_info").child(Uid).child("present").addListenerForSingleValueEvent(new ValueEventListener() {
                         @Override
                         public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                             int a = Integer.parseInt(dataSnapshot.getValue().toString());
                             update(a + 1, FirebaseDatabase.getInstance().getReference().child("teacher_info").child(Uid).child("present"));

                         }


                         @Override
                         public void onCancelled(@NonNull DatabaseError databaseError) {

                         }
                     });
                 } else {
                     FirebaseDatabase.getInstance().getReference().child("teacher_info").child(id).child("extra_day").addListenerForSingleValueEvent(new ValueEventListener() {
                         @Override
                         public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                             int a = Integer.parseInt(dataSnapshot.getValue().toString());
                             update(a + 1, FirebaseDatabase.getInstance().getReference().child("teacher_info").child(id).child("extra_day"));

                         }


                         @Override
                         public void onCancelled(@NonNull DatabaseError databaseError) {

                         }
                     });
                 }

                 RadioGroup p = other_member.findViewById(R.id.oth_mem_attendance);
                 final String m_id = member_id.getText().toString().toUpperCase();
                 if (p.getCheckedRadioButtonId() != -1) {
                     if (yes.isChecked()) {


                         if (m_id.equals(uid2) || m_id.equals(uid1)) {
                             FirebaseDatabase.getInstance().getReference().child("teacher_info").child(m_id).child("present").addListenerForSingleValueEvent(new ValueEventListener() {
                                 @Override
                                 public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                     int a = Integer.parseInt(dataSnapshot.getValue().toString());
                                     update(a + 1, FirebaseDatabase.getInstance().getReference().child("teacher_info").child(m_id).child("present"));
                                    showAttendance();
                                 }


                                 @Override
                                 public void onCancelled(@NonNull DatabaseError databaseError) {

                                 }
                             });
                         } else
                             Toast.makeText(getApplicationContext(), "Enter correct SGM Id", Toast.LENGTH_SHORT).show();


                     } else {
                         RadioGroup oth_sub_sent_not_sent = other_member.findViewById(R.id.oth_sub_sent_not_sent);
                         RadioGroup oth_sub_member_or_not = other_member.findViewById(R.id.oth_sub_member_or_not);
                         if (oth_sub_sent_not_sent.getCheckedRadioButtonId() != -1) {
                             if (oth_sub_sent.isChecked() == true) {
                                 if (oth_sub_member_or_not.getCheckedRadioButtonId() != -1) {
                                     if ((oth_sub_mem_yes.isChecked() == true)) {
                                         final String Id = substitute_id.getText().toString().toUpperCase();


                                         FirebaseDatabase.getInstance().getReference().child("teacher_info").child(Id).child("extra_day").addListenerForSingleValueEvent(new ValueEventListener() {
                                             @Override
                                             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                 if (dataSnapshot.getValue() != null) {
                                                     int a = Integer.parseInt(dataSnapshot.getValue().toString());
                                                     update(a + 1, FirebaseDatabase.getInstance().getReference().child("teacher_info").child(Id).child("extra_day"));
                                                     showAttendance();

                                                 } else
                                                     Toast.makeText(getApplicationContext(), "Please enter correct SGM id\nSGM id doesn't exist", Toast.LENGTH_SHORT);


                                             }

                                             @Override
                                             public void onCancelled(@NonNull DatabaseError databaseError) {


                                             }
                                         });
                                     } else showAttendance();
                                 } else
                                     Toast.makeText(getApplicationContext(), "Please select if substitute is SGM member or not", Toast.LENGTH_SHORT).show();

                             } else showAttendance();


                         } else {
                             Toast.makeText(getApplicationContext(), "Please select if substitute sent or not", Toast.LENGTH_SHORT).show();
                         }

                     }


                 } else {
                     Toast.makeText(getApplicationContext(), "Please mark the attendance", Toast.LENGTH_SHORT).show();
                 }


             }

         });
         other_member.show();

     }



   private void showAttendance()
   {

       FirebaseDatabase.getInstance().getReference().child("Dates").child(date).setValue(date);

       reference.child("set").setValue("1");
       for(final String Uid: attendance.keySet())
       {  final String a=attendance.get(Uid);
           final DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference().child("teacher_info").child(Uid).child("actual_day");
           databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                   /** Teacher_management.Teacher teacher=dataSnapshot.getValue(Teacher_management.Teacher.class);
                    System.out.println(teacher.getUser_name());
                    Map<String, Object> map = new HashMap<>();
                    int b=Integer.parseInt(teacher.getActual_day())+1;
                    int p=Integer.parseInt(teacher.getPresent())+1;
                    map.put("actual_day", String.valueOf(b));*

                    //if(attendance.get(Uid)==1)
                    // map.put("present",String.valueOf(p));
                    databaseReference.updateChildren(map);
                    map.clear();*/
                   p=Integer.parseInt(dataSnapshot.getValue().toString());
                   System.out.println(p);
                   p=p+1;
                   update(p,databaseReference1);

                   System.out.println(dataSnapshot.getValue().hashCode());

                   return;
               }

               @Override
               public void onCancelled(@NonNull DatabaseError databaseError) {

               }
           });
           final DatabaseReference databaseReference2=FirebaseDatabase.getInstance().getReference().child("teacher_info").child(Uid).child("present");
           databaseReference2.addListenerForSingleValueEvent(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                   /** Teacher_management.Teacher teacher=dataSnapshot.getValue(Teacher_management.Teacher.class);
                    System.out.println(teacher.getUser_name());
                    Map<String, Object> map = new HashMap<>();
                    int b=Integer.parseInt(teacher.getActual_day())+1;
                    int p=Integer.parseInt(teacher.getPresent())+1;
                    map.put("actual_day", String.valueOf(b));*

                    //if(attendance.get(Uid)==1)
                    // map.put("present",String.valueOf(p));
                    databaseReference.updateChildren(map);
                    map.clear();*/
                   if(a.equals("1"))
                   {
                       p=Integer.parseInt(dataSnapshot.getValue().toString());
                       System.out.println(p);
                       p=p+1;
                       update(p,databaseReference2);

                       System.out.println(dataSnapshot.getValue().hashCode());

                   }

                   return;
               }

               @Override
               public void onCancelled(@NonNull DatabaseError databaseError) {

               }
           });

       }
       attendance.clear();
       Intent i=new Intent(Management_screen.this, Show_Attendance.class);
       startActivity(i);

   }
   private void loadData() {
       showLoadData();
       FirebaseDatabase.getInstance().getReference().child("Schedule").child(current_day).child("Management").child("uid1").addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               uid1 = dataSnapshot.getValue().toString();

               FirebaseDatabase.getInstance().getReference().child("teacher_info").child(uid1).child("actual_day").addListenerForSingleValueEvent(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                       int a = Integer.parseInt(dataSnapshot.getValue().toString());
                       update(a + 1, FirebaseDatabase.getInstance().getReference().child("teacher_info").child(uid1).child("actual_day"));
                       FirebaseDatabase.getInstance().getReference().child("Schedule").child(current_day).child("Management").child("uid2").addValueEventListener(new ValueEventListener() {
                           @Override
                           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                               uid2 = dataSnapshot.getValue().toString();
                               FirebaseDatabase.getInstance().getReference().child("teacher_info").child(uid2).child("actual_day").addListenerForSingleValueEvent(new ValueEventListener() {
                                   @Override
                                   public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                       int a = Integer.parseInt(dataSnapshot.getValue().toString());
                                       update(a + 1, FirebaseDatabase.getInstance().getReference().child("teacher_info").child(uid2).child("actual_day"));

                                   }

                                   @Override
                                   public void onCancelled(@NonNull DatabaseError databaseError) {

                                   }
                               });
                               dialog.dismiss();
                               if (id.equals(uid1)) {

                                   flag = 1;
                                   Uid = uid1;
                                   Uid2 = uid2;
                               } else if (id.equals(uid2)) {
                                   flag = 2;
                                   Uid = uid2;
                                   Uid2 = uid1;
                               } else {

                                   flag = 0;
                               }
                           }

                           @Override
                           public void onCancelled(@NonNull DatabaseError databaseError) {

                           }
                       });

                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError databaseError) {

                   }
               });
           }
           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });
    }
    public void enableOrdisable(RadioGroup r, boolean b)
    {
        for (int i = 0; i < r.getChildCount(); i++) {
            r.getChildAt(i).setEnabled(b);
        }
    }
    @Override
    public void onBackPressed()
    {
        //auth.signOut();
        //Toast.makeText(getApplicationContext(), "Logging you out", Toast.LENGTH_SHORT).show();
        Intent i=new Intent(Management_screen.this,Profile_mgmt.class);
        startActivity(i);
        //pref.edit().clear();
    }
    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(authStateListener);
    }
}


