package com.example.tutorfinder.Admin_models;

public class Studnets {
    String alstream,dob,email,name,nic,phone,school;

    public Studnets(){}
    public Studnets(String alstream, String dob, String email, String name, String nic, String phone, String school) {
        this.alstream = alstream;
        this.dob = dob;
        this.email = email;
        this.name = name;
        this.nic = nic;
        this.phone = phone;
        this.school = school;
    }

    public String getAlstream() {
        return alstream;
    }

    public void setAlstream(String alstream) {
        this.alstream = alstream;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
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

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }
}
