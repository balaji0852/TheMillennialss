package com.tmv01.themillennial;

public class account {
    String UserName,phoneNumber,emailId,password;

    public account(String UserName, String phoneNumber, String emailId, String password) {
        this.UserName = UserName;
        this.phoneNumber = phoneNumber;
        this.emailId = emailId;
        this.password = password;
    }

    public String getUserName() {
        return UserName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmailId() {
        return emailId;
    }

    public String getPassword() {
        return password;
    }




}
