package com.example.tutorfinder.Admin_models;

public class studentPayment {

    String className,slipIMG,studentUID,subject,alstream;
    long paidID;
    double payment;

    //constructor
    public studentPayment() {

    }

    //overloaded constructor

    public studentPayment(String className, String slipIMG, String studentUID, String subject, String alstream, long paidID, double payment) {
        this.className = className;
        this.slipIMG = slipIMG;
        this.studentUID = studentUID;
        this.subject = subject;
        this.alstream = alstream;
        this.paidID = paidID;
        this.payment = payment;
    }


    //setters and getters


    public long getPaidID() {
        return paidID;
    }

    public void setPaidID(long paidID) {
        this.paidID = paidID;
    }
    /*public String getPaidID() {
        return paidID;
    }

    public void setPaidID(String paidID) {
        this.paidID = paidID;
    }*/

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public double getPayment() {
        return payment;
    }

    public void setPayment(double payment) {
        this.payment = payment;
    }

    /*public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }*/

    public String getSlipIMG() {
        return slipIMG;
    }

    public void setSlipIMG(String slipIMG) {
        this.slipIMG = slipIMG;
    }

    public String getStudentUID() {
        return studentUID;
    }

    public void setStudentUID(String studentNIC) {
        this.studentUID = studentUID;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }


    public String getAlstream() {
        return alstream;
    }

    public void setAlstream(String alstream) {
        this.alstream = alstream;
    }
}

