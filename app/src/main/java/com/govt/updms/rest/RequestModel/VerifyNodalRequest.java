package com.govt.updms.rest.RequestModel;

public class VerifyNodalRequest {
    int nodalId;
    int workerId;
    boolean status;
    String reason;

    public VerifyNodalRequest(int nodalId, boolean status, int workerId, String text) {
        this.nodalId = nodalId;
        this.workerId = workerId;
        this.reason = text;
        this.status = status;
    }
}
