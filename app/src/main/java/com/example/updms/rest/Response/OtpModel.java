
package com.example.updms.rest.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OtpModel {

    @SerializedName("balance")
    @Expose
    private int balance;
    @SerializedName("batch_id")
    @Expose
    private int batchId;
    @SerializedName("cost")
    @Expose
    private int cost;
    @SerializedName("num_messages")
    @Expose
    private int numMessages;
    @SerializedName("message")
    @Expose
    private Message message;
    @SerializedName("receipt_url")
    @Expose
    private String receiptUrl;
    @SerializedName("custom")
    @Expose
    private String custom;
    @SerializedName("messages")
    @Expose
    private List<Message_> messages = null;
    @SerializedName("status")
    @Expose
    private String status;

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getBatchId() {
        return batchId;
    }

    public void setBatchId(int batchId) {
        this.batchId = batchId;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getNumMessages() {
        return numMessages;
    }

    public void setNumMessages(int numMessages) {
        this.numMessages = numMessages;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public String getReceiptUrl() {
        return receiptUrl;
    }

    public void setReceiptUrl(String receiptUrl) {
        this.receiptUrl = receiptUrl;
    }

    public String getCustom() {
        return custom;
    }

    public void setCustom(String custom) {
        this.custom = custom;
    }

    public List<Message_> getMessages() {
        return messages;
    }

    public void setMessages(List<Message_> messages) {
        this.messages = messages;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
