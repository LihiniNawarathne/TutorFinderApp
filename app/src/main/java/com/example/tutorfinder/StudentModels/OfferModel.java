package com.example.tutorfinder.StudentModels;

public class OfferModel {

    double amount;
    String subjectName,tutorName,Time;

    public OfferModel() { }

    public OfferModel(double amount, String subjectName, String tutorName, String time) {
        this.amount = amount;
        this.subjectName = subjectName;
        this.tutorName = tutorName;
        Time = time;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getTutorName() {
        return tutorName;
    }

    public void setTutorName(String tutorName) {
        this.tutorName = tutorName;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }
}
