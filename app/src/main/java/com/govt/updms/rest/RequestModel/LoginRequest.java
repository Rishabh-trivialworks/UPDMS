package com.govt.updms.rest.RequestModel;

public class LoginRequest {
    String userName;
    String password;
    String userType;
    String MobileNo;


    public LoginRequest(String userName, String password, String userType) {
        this.userName = userName;
        this.password = password;
        this.userType = userType;
    }

    public LoginRequest(String mobileno, String userType) {
        this.MobileNo = mobileno;
        this.userType = userType;
    }
}
