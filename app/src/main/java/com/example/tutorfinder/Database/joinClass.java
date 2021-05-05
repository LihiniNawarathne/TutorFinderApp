package com.example.tutorfinder.Database;

public class joinClass {

   String  className,studentUID,slipIMG,subject,alstream;
   long paidID,payment;

    public joinClass(long paidID, String className, String studentNIC, long payment, String slipIMG,String subject,String alstream) {
        this.paidID = paidID;
        this.className = className;
        this.studentUID = studentNIC;
        this.payment = payment;
        this.slipIMG = slipIMG;
        this.subject = subject;
        this.alstream=alstream;
    }

    public long getPaidID() {
        return paidID;
    }

    public void setPaidID(long paidID) {
        this.paidID = paidID;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getStudentUID() {
        return studentUID;
    }

    public void setStudentUID(String studentUID) {
        this.studentUID = studentUID;
    }

    public long getPayment() {
        return payment;
    }

    public void setPayment(long payment) {
        this.payment = payment;
    }

    public String getSlipIMG() {
        return slipIMG;
    }

    public void setSlipIMG(String slipIMG) {
        this.slipIMG = slipIMG;
    }

    public String getSubject() { return subject; }

    public void setSubject(String subject) {this.subject = subject; }

    public String getAlstream() {
        return alstream;
    }

    public void setAlstream(String alstream) {
        this.alstream = alstream;
    }
}
