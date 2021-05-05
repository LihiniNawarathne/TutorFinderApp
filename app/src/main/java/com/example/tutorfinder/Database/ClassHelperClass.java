package com.example.tutorfinder.Database;

public class ClassHelperClass {

    String className,subject,Time,Grade,Tutor;
    long Amount;

    public ClassHelperClass() {
    }

    public ClassHelperClass(String className, String subject, String time, String grade, String tutor, long amount) {
        this.className = className;
        this.subject = subject;
        Time = time;
        Grade = grade;
        Tutor = tutor;
        Amount = amount;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getGrade() {
        return Grade;
    }

    public void setGrade(String grade) {
        Grade = grade;
    }

    public String getTutor() {
        return Tutor;
    }

    public void setTutor(String tutor) {
        Tutor = tutor;
    }

    public long getAmount() {
        return Amount;
    }

    public void setAmount(long amount) {
        Amount = amount;
    }
}
