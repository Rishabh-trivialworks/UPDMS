
package com.example.updms.rest.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ServiceObject implements Serializable {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("active")
    @Expose
    private boolean active;
    @SerializedName("averagePrice")
    @Expose
    private int averagePrice;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("categoryId")
    @Expose
    private int categoryId;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("hourPrice")
    @Expose
    private int hourPrice;
    @SerializedName("squareFeetPrice")
    @Expose
    private int squareFeetPrice;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(int averagePrice) {
        this.averagePrice = averagePrice;
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

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getHourPrice() {
        return hourPrice;
    }

    public void setHourPrice(int hourPrice) {
        this.hourPrice = hourPrice;
    }

    public int getSquareFeetPrice() {
        return squareFeetPrice;
    }

    public void setSquareFeetPrice(int squareFeetPrice) {
        this.squareFeetPrice = squareFeetPrice;
    }

}
