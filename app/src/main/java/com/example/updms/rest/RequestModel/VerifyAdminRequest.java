package com.example.updms.rest.RequestModel;

public class VerifyAdminRequest {
    int adminId;
    int workerId;
    boolean status;
    String reason;



    public VerifyAdminRequest(int adminId, int workerId, boolean status, String reason) {
        this.adminId = adminId;
        this.workerId = workerId;
        this.status = status;
        this.reason = reason;
    }
}
