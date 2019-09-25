package com.example.edu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import static com.example.edu.Management_screen.class_name;

public class class_management extends AppCompatActivity {

    CardView subtitute,merge,nostudent,problem;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_management);

        subtitute=findViewById(R.id.subtitute);
        nostudent=findViewById(R.id.nostudent);
        subtitute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showdilog_subtitue();
            }
        });
        nostudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showdilog_nostudent();
            }
        });
    }

   /* private void showdilog_merge()
    {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.merge_popup);
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        String class_name[]={"1","2","3","4","5","6","7","8","9","10","11","12"};

        Spinner spinner=dialog.findViewById(R.id.spinner1);

        ArrayAdapter aa=new ArrayAdapter(class_management.this,android.R.layout.simple_spinner_item,class_name);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(aa);

        dialog.show();
        dialog.getWindow().setAttributes(lp);

    }
    */
    private void showdilog_subtitue()
    {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.subtitute_popup);
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;



        final EditText name_teacher=dialog.findViewById(R.id.name);
        final EditText batch_teacher=dialog.findViewById(R.id.Batch);

        final CheckBox check_member=dialog.findViewById(R.id.check_member);
        final CheckBox check_not_member=dialog.findViewById(R.id.check_not_member);

        final String name[]={"Amit","Aayush","Ankit","Divyansh","kamal","Shivam","Vishnu"};

        final Spinner spinner=dialog.findViewById(R.id.spinner);

        ArrayAdapter<String> aa=new ArrayAdapter<>(class_management.this,android.R.layout.simple_spinner_item,name);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(aa);

        check_member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(check_member.isChecked())
                {
                    spinner.setEnabled(true);
                    name_teacher.setEnabled(false);
                    batch_teacher.setEnabled(false);
                    check_not_member.setChecked(false);
                }
            }
        });
        check_not_member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(check_not_member.isChecked())
                {
                    spinner.setEnabled(false);
                    name_teacher.setEnabled(true);
                    batch_teacher.setEnabled(true);
                    check_member.setChecked(false);
                }
            }
        });

        dialog.findViewById(R.id.bt_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(check_member.isChecked())
                {
                    String name= spinner.getSelectedItem().toString();
                    class_name.set(0,new adaptor_class(name,"2018","okj"));
                }
                else
                {
                     EditText editText=findViewById(R.id.name);
                     String name= editText.getText().toString();
                    class_name.set(0,new adaptor_class(name,"2018","okj"));
                }

            }
        });


    (dialog.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                }
       });

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }
    private void showdilog_nostudent()
    {


        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.nostudent_popup);
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }
 /*   private void showdilog_otherproblem()
    {


        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.otherproblem_popup);
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        dialog.show();
        dialog.getWindow().setAttributes(lp);

    }
  */
}