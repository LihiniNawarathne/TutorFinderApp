package com.example.tutorfinder.Database;

public class StudentHelperClass {

    String Name,Phone,Email,School,NIC,ALStream,DOB,Password,proimg,uid;

    public StudentHelperClass(String name, String phone, String email, String school, String nic, String ALStream, String DOB,String proimg,String uid) {
        Name = name;
        Phone = phone;
        Email = email;
        School = school;
        NIC = nic;
        this.proimg=proimg;
        this.ALStream = ALStream;
        this.DOB = DOB;
        this.uid=uid;
       // Password = password;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getSchool() {
        return School;
    }

    public void setSchool(String school) {
        School = school;
    }

    public String getNIC() {
        return NIC;
    }

    public void setNIC(String nic) {
        NIC = nic;
    }

    public String getALStream() {
        return ALStream;
    }

    public void setALStream(String ALStream) {
        this.ALStream = ALStream;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getProimg() {
        return proimg;
    }

    public void setProimg(String proimg) {
        this.proimg = proimg;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }


    //    public String getPassword() {
//        return Password;
//    }
//
//    public void setPassword(String password) {
//        Password = password;
//    }
}
