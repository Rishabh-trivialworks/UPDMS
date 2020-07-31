package com.govt.updms.rest.RequestModel;

public class FirebaseRequest {

    String fcmToken;

    public FirebaseRequest(String deviceToken) {
        this.fcmToken=deviceToken;
    }

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }
}
