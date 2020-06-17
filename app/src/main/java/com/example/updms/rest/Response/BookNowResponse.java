
package com.example.updms.rest.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BookNowResponse implements Serializable {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("userId")
    @Expose
    private int userId;
    @SerializedName("serviceCategoryId")
    @Expose
    private int serviceCategoryId;
    @SerializedName("addressId")
    @Expose
    private int addressId;
    @SerializedName("price")
    @Expose
    private int price;
    @SerializedName("initial_order_id")
    @Expose
    private String initialOrderId;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getServiceCategoryId() {
        return serviceCategoryId;
    }

    public void setServiceCategoryId(int serviceCategoryId) {
        this.serviceCategoryId = serviceCategoryId;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getInitialOrderId() {
        return initialOrderId;
    }

    public void setInitialOrderId(String initialOrderId) {
        this.initialOrderId = initialOrderId;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

}
