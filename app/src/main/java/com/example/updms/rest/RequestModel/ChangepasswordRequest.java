package com.example.updms.rest.RequestModel;

public class ChangepasswordRequest {

    String oldPassword, newPassword;

    public ChangepasswordRequest(String oldPassword, String newPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }
}
