package com.example.tutorfinder.StudentModels;

public class ClassHelperClass {

    String className,subject,Time,Grade,Tutor, Amount;

    public ClassHelperClass() {
    }

    public ClassHelperClass(String className, String subject, String time, String grade, String tutor, String amount) {
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

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }
}
