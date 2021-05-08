package com.example.tutorfinder.Database;

public class Tutor_register {
    String name, email, phoneno, password, address,qualifications,subject,nic;

    public Tutor_register(){



    }
    public Tutor_register(String name, String email, String phoneno, String password, String nic, String subject,String qualifications,String address ) {
        this.name = name;
        this.email = email;
        this.phoneno= phoneno;
        this.password = password;
        this.nic = nic;
        this.subject = subject;
        this.qualifications = qualifications;
        this.address = address;





    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getQualifications() {
        return qualifications;
    }

    public void setQualifications(String qualifications) {
        this.qualifications = qualifications;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }



}
