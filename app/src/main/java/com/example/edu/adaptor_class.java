package com.example.edu;

import android.util.Log;

public class adaptor_class {

    String teacher_name;
    String batch;
    String comment;

    public adaptor_class(String tech,String batch,String comm)
    {
        teacher_name=tech;
        this.batch=batch;
        comment=comm;
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

}
