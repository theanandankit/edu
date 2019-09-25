package com.example.edu;

class User_info {
    String uid;
    String name;
    int attendance;
    int extra;
    int total_days;
    int contact_no;
    String roll_no;
    int actual_days;

    public User_info( String name, int attendance, int extra, int total_days, int contact_no, String roll_no) {
        this.name = name;
        this.attendance = attendance;
        this.extra = extra;
        this.total_days = total_days;
        this.contact_no=contact_no;
        this.roll_no=roll_no;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAttendance() {
        return attendance;
    }

    public void setAttendance(int attendance) {
        this.attendance = attendance;
    }

    public int getExtra() {
        return extra;
    }

    public void setExtra(int extra) {
        this.extra = extra;
    }

    public int getTotal_days() {
        return total_days;
    }

    public void setTotal_days(int total_days) {
        this.total_days = total_days;
    }
    public int getContact_no() {
        return contact_no;
    }

    public void setContact_no(int contact_no) {
        this.contact_no = contact_no;
    }

    public String getRoll_no() {
        return roll_no;
    }

    public void setRoll_no(String roll_no) {
        this.roll_no = roll_no;
    }

}
