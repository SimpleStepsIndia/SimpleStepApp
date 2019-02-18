package com.simplestepapp.data.offline;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Suren Reddy on 07-Apr-16.
 */
public class KpiIndicator {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("kpitopicid")
    @Expose
    private Integer kpitopicid;
    @SerializedName("id")
    @Expose
    private Integer id;

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
     * The kpitopicid
     */
    public Integer getKpitopicid() {
        return kpitopicid;
    }

    /**
     *
     * @param kpitopicid
     * The kpitopicid
     */
    public void setKpitopicid(Integer kpitopicid) {
        this.kpitopicid = kpitopicid;
    }

    /**
     *
     * @return
     * The id
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(Integer id) {
        this.id = id;
    }
}
