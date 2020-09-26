
package com.sourav.rxnet.webapi.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetAllThana {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("thana_name")
    @Expose
    private String thanaName;
    @SerializedName("deletion_status")
    @Expose
    private String deletionStatus;
    @SerializedName("status")
    @Expose
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getThanaName() {
        return thanaName;
    }

    public void setThanaName(String thanaName) {
        this.thanaName = thanaName;
    }

    public String getDeletionStatus() {
        return deletionStatus;
    }

    public void setDeletionStatus(String deletionStatus) {
        this.deletionStatus = deletionStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
