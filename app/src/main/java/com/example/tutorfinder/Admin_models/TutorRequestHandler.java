package com.example.tutorfinder.Admin_models;

public class TutorRequestHandler {

    String NIC,Address,Email,Name,Password,PhoneNo,Subject,Qualifications;

    public TutorRequestHandler(){}

    public TutorRequestHandler(String NIC, String Address, String Email, String Name, String Password, String PhoneNo, String Subject, String Qualifications) {
        this.NIC = NIC;
        this.Address = Address;
        this.Email = Email;
        this.Name = Name;
        this.Password = Password;
        this.PhoneNo = PhoneNo;
        this.Subject = Subject;
        this.Qualifications = Qualifications;
    }


    public String getNIC() {
        return NIC;
    }

    public void setNIC(String NIC) {
        this.NIC = NIC;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public String getPhoneNo() {
        return PhoneNo;
    }

    public void setPhoneNo(String PhoneNo) {
        this.PhoneNo = PhoneNo;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String Subject) {
        this.Subject = Subject;
    }

    public String getQualifications() {
        return Qualifications;
    }

    public void setQualifications(String Qualifications) {
        this.Qualifications = Qualifications;
    }


}
