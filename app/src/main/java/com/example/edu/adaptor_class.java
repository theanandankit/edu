package com.example.edu;

import android.util.Log;

public class adaptor_class {

    String teacher_name;
    String uid;
    String batch;
    String class1;

    String comment;


    public adaptor_class(String teach,String uid,String batch,String class1,String comm)
    {
        teacher_name=teach;
        this.uid=uid;
        this.batch=batch;
        comment=comm;
        this.class1=class1;
    }
    public String getTeacher_name()
    {
        return teacher_name;
    }
    public String getBatch()
    {
        return batch;
    }
    public String getComment()
    {
        return comment;
    }

    public String getUid() {
        return uid;
    }

    public String getClass1() {
        return class1;
    }
}


