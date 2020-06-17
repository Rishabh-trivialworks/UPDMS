package com.example.updms.rest.RequestModel;

public class RegisterRequest {
    String email,password,name,mobile, fcmToken;;

    public RegisterRequest(String email, String password, String name, String mobile, String fcmToken) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.mobile=mobile;
        this.fcmToken = fcmToken;
    }
}
