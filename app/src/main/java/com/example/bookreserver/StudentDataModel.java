package com.example.bookreserver;

public class StudentDataModel {
    String emailID;
    String enrollmentNo;
    String password;

    public StudentDataModel() {
        //empty constructor needed
    }

    public StudentDataModel(String emailID, String enrollmentNo, String password) {
        this.emailID = emailID;
        this.enrollmentNo = enrollmentNo;
        this.password = password;
    }

    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    public String getEnrollmentNo() {
        return enrollmentNo;
    }

    public void setEnrollmentNo(String enrollmentNo) {
        this.enrollmentNo = enrollmentNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
