package com.example.tutorfinder.Database;

public class joinClass {

   String  className,studentNIC,slipIMG;
   long paidID,payment;

    public joinClass(long paidID, String className, String studentNIC, long payment, String slipIMG) {
        this.paidID = paidID;
        this.className = className;
        this.studentNIC = studentNIC;
        this.payment = payment;
        this.slipIMG = slipIMG;
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

    public String getStudentNIC() {
        return studentNIC;
    }

    public void setStudentNIC(String studentNIC) {
        this.studentNIC = studentNIC;
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
}
