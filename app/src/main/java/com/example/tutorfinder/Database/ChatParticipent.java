package com.example.tutorfinder.Database;

public class ChatParticipent {
    String role,uid;
    public ChatParticipent(){}
    public ChatParticipent(String role, String uid) {
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
