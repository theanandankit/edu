package com.example.edu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class class_management extends AppCompatActivity {

    CardView subtitute,merge,nostudent,problem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_management);

        subtitute=findViewById(R.id.subtitute);
        merge=findViewById(R.id.merge);
        problem=findViewById(R.id.problem);
        nostudent=findViewById(R.id.nostudent);
        subtitute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showdilog_subtitue();
            }
        });
        merge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent ii=new Intent(class_management.this,merge_popup.class);
                startActivity(ii);
            }
        });
        nostudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ii=new Intent(class_management.this,nostudent_popup.class);
                startActivity(ii);
            }
        });
        problem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent ii=new Intent(class_management.this,otherproblem_popup.class);
                startActivity(ii);
            }
        });

    }

    private void showdilog_merge()
    {

    }
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

    }
    private void showdilog_otherproblem()
    {

    }
}