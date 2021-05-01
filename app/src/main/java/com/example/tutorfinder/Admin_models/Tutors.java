package com.example.tutorfinder.Admin_models;

public class Tutors {

    public String nic,address,email,name,password,phoneNo,subject,qualifications;


    public Tutors() {
    }

    public Tutors(String nic, String address, String email, String name, String password, String phoneNo, String subject, String qualifications) {
        this.nic = nic;
        this.address = address;
        this.email = email;
        this.name = name;
        this.password = password;
        this.phoneNo = phoneNo;
        this.subject = subject;
        this.qualifications = qualifications;
    }

    public String getNIC() {
        return nic;
    }

    public void setNIC(String nic) {
        this.nic = nic;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getQualifications() {
        return qualifications;
    }

    public void setQualifications(String qualifications) {
        this.qualifications = qualifications;
    }


}

