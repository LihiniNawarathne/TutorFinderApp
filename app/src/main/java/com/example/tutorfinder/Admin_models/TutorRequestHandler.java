package com.example.tutorfinder.Admin_models;

public class TutorRequestHandler {

    String nic,address,email,name,phoneno,subject,qualifications,password;

    public TutorRequestHandler(){}

    public TutorRequestHandler(String nic, String address, String email, String name,String password, String phoneno, String subject, String qualifications) {
        this.nic = nic;
        this.address = address;
        this.email = email;
        this.name = name;
        this.password = password;
        this.phoneno = phoneno;
        this.subject = subject;
        this.qualifications = qualifications;

    }
    public TutorRequestHandler(String nic, String address, String email, String name, String phoneno, String subject, String qualifications) {
        this.nic = nic;
        this.address = address;
        this.email = email;
        this.name = name;
        this.phoneno = phoneno;
        this.subject = subject;
        this.qualifications = qualifications;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
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

    public String getphoneno() {
        return phoneno;
    }

    public void setphoneno(String phoneno) {
        this.phoneno = phoneno;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
