package com.govt.updms.rest.RequestModel;

public class NodalRequest {
    int nodalId;
    int adminId;
    boolean nodalStatus;

    public NodalRequest(int nodalId, int adminId, boolean nodalStatus) {
        this.nodalId = nodalId;
        this.adminId = adminId;
        this.nodalStatus = nodalStatus;
    }
}
