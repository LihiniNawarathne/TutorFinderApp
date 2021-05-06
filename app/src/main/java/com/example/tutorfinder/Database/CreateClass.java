package com.example.tutorfinder.Database;

public class CreateClass {

    String tutor,className,subject,grade,time,amount;



    public CreateClass(String tutor, String className,String subject,String grade,String time,String amount) {
        this.tutor = tutor;
        this.className = className;
        this.subject= subject;
        this.grade = grade;
        this.time = time;
        this.amount = amount;
    }

    public String getTutorname() {
        return tutor;
    }

    public void setTutorname(String tutor) {
        this.tutor = tutor;
    }

    public String getClassname() {
        return className;
    }

    public void setClassname(String className) {
        this.className = className;
    }

    public String getSubname() {
        return subject;
    }

    public void setSubname(String subject) {
        this.subject = subject;
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
