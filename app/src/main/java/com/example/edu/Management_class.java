package com.example.edu;

public class Management_class {
    String name1,batch1,uid1,name2,uid2,batch2;

    public Management_class(String name1, String batch1, String uid1, String name2, String uid2, String batch2) {
        this.name1 = name1;
        this.batch1 = batch1;
        this.uid1 = uid1;
        this.name2 = name2;
        this.uid2 = uid2;
        this.batch2 = batch2;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getBatch1() {
        return batch1;
    }

    public void setBatch1(String batch1) {
        this.batch1 = batch1;
    }

    public String getUid1() {
        return uid1;
    }

    public void setUid1(String uid1) {
        this.uid1 = uid1;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public String getUid2() {
        return uid2;
    }

    public void setUid2(String uid2) {
        this.uid2 = uid2;
    }

    public String getBatch2() {
        return batch2;
    }

    public void setBatch2(String batch2) {
        this.batch2 = batch2;
    }
}
