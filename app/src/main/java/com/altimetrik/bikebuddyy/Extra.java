package com.altimetrik.bikebuddyy;

/**
 * Created by SFLDPGUSER-24 on 6/21/2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Extra {

    @SerializedName("address")
    @Expose
    private Object address;
    @SerializedName("last_updated")
    @Expose
    private Integer lastUpdated;
    @SerializedName("renting")
    @Expose
    private Integer renting;
    @SerializedName("returning")
    @Expose
    private Integer returning;
    @SerializedName("uid")
    @Expose
    private String uid;

    public Object getAddress() {
        return address;
    }

    public void setAddress(Object address) {
        this.address = address;
    }

    public Integer getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Integer lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Integer getRenting() {
        return renting;
    }

    public void setRenting(Integer renting) {
        this.renting = renting;
    }

    public Integer getReturning() {
        return returning;
    }

    public void setReturning(Integer returning) {
        this.returning = returning;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

}
