package com.nomprenom2.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GroupPojo {

    @SerializedName("group_name")
    @Expose
    private String groupName;

    /**
     *
     * @return
     * The groupName
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     *
     * @param groupName
     * The group_name
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

}