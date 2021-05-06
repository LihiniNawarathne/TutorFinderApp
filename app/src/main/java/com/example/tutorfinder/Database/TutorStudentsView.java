package com.example.tutorfinder.Database;

public class TutorStudentsView {
    String alstream,dob,email,name,nic,phone,proimg,school,uid;



    public TutorStudentsView(){



    }
    public TutorStudentsView(String alstream, String dob, String email, String name, String nic, String phone,String proimg, String school,String uid) {
        this.alstream = alstream;
        this.dob = dob;
        this.email = email;
        this.name = name;
        this.nic = nic;
        this.phone = phone;
        this.proimg =proimg;
        this.school = school;
        this.uid =uid;
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
}