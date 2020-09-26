
package com.sourav.rxnet.webapi.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CovaregArea {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("thana")
    @Expose
    private String thana;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("deletion_status")
    @Expose
    private String deletionStatus;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getThana() {
        return thana;
    }

    public void setThana(String thana) {
        this.thana = thana;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDeletionStatus() {
        return deletionStatus;
    }

    public void setDeletionStatus(String deletionStatus) {
        this.deletionStatus = deletionStatus;
    }

}
