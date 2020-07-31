
package com.govt.updms.rest.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Tehsil {

    @SerializedName("TehsilCode")
    @Expose
    private int tehsilCode;
    @SerializedName("TehsilName")
    @Expose
    private String tehsilName;
    @SerializedName("ActiveStatus")
    @Expose
    private boolean activeStatus;

    public int getTehsilCode() {
        return tehsilCode;
    }

    public void setTehsilCode(int tehsilCode) {
        this.tehsilCode = tehsilCode;
    }

    public String getStateName() {
        return tehsilName;
    }

    public void setStateName(String stateName) {
        this.tehsilName = stateName;
    }

    public boolean isActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(boolean activeStatus) {
        this.activeStatus = activeStatus;
    }

}
