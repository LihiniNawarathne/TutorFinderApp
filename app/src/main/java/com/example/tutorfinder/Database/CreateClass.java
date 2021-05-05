package com.example.tutorfinder.Database;

public class CreateClass {

    String tutorname,classname,subname,grade,time,amount;



    public CreateClass(String tutorname, String classname,String subname,String grade,String time,String amount) {
        this.tutorname = tutorname;
        this.classname = classname;
        this.subname= subname;
        this.grade = grade;
        this.time = time;
        this.amount = amount;
    }

    public String getTutorname() {
        return tutorname;
    }

    public void setTutorname(String tutorname) {
        this.tutorname = tutorname;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getSubname() {
        return subname;
    }

    public void setSubname(String subname) {
        this.subname = subname;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
