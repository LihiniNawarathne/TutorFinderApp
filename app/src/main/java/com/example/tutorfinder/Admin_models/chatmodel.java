package com.example.tutorfinder.Admin_models;

public class chatmodel {
    String role,uid;

    public chatmodel() {

    }

    public chatmodel(String role, String uid) {
        this.role = role;
        this.uid = uid;
    }


    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
