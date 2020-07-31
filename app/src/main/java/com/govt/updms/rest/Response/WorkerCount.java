
package com.govt.updms.rest.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WorkerCount {

    @SerializedName("total")
    @Expose
    private int total;
    @SerializedName("unverified")
    @Expose
    private int unverified;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getUnverified() {
        return unverified;
    }

    public void setUnverified(int unverified) {
        this.unverified = unverified;
    }

}
