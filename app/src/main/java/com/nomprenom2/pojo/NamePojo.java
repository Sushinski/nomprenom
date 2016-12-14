package com.nomprenom2.pojo;


import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NamePojo {

    @SerializedName("_id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("sex")
    @Expose
    private int sex;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("groups")
    @Expose
    private String groups;
    @SerializedName("zodiacs")
    @Expose
    private List<ZodiacPojo> zodiacs = new ArrayList<ZodiacPojo>();

    /**
     *
     * @return
     * The id
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id
     * The _id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The sex
     */
    public int getSex() {
        return sex;
    }

    /**
     *
     * @param sex
     * The sex
     */
    public void setSex(int sex) {
        this.sex = sex;
    }

    /**
     *
     * @return
     * The description
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     * The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return
     * The groups
     */
    public String getGroups() {
        return groups;
    }

    /**
     *
     * @param groups
     * The groups
     */
    public void setGroups(String groups) {
        this.groups = groups;
    }

    /**
     *
     * @return
     * The zodiacs
     */
    public List<ZodiacPojo> getZodiacs() {
        return zodiacs;
    }

    /**
     *
     * @param zodiacs
     * The zodiacs
     */
    public void setZodiacs(List<ZodiacPojo> zodiacs) {
        this.zodiacs = zodiacs;
    }

}

