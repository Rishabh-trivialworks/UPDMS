
package com.example.updms.rest.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Division {

    @SerializedName("DivisionCode")
    @Expose
    private int divisionCode;
    @SerializedName("DivisionName")
    @Expose
    private String divisionName;
    @SerializedName("ActiveStatus")
    @Expose
    private boolean activeStatus;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("StateCode")
    @Expose
    private int stateCode;
    @SerializedName("state")
    @Expose
    private State state;

    public int getDivisionCode() {
        return divisionCode;
    }

    public void setDivisionCode(int divisionCode) {
        this.divisionCode = divisionCode;
    }

    public String getDivisionName() {
        return divisionName;
    }

    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    public boolean isActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(boolean activeStatus) {
        this.activeStatus = activeStatus;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getStateCode() {
        return stateCode;
    }

    public void setStateCode(int stateCode) {
        this.stateCode = stateCode;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
