
package com.simplestepapp.data.offline;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Block {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("zoneId")
    @Expose
    private Integer zoneId;
    @SerializedName("salesAreaId")
    @Expose
    private Integer salesAreaId;

    /**
     * 
     * @return
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @return
     *     The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The zoneId
     */
    public Integer getZoneId() {
        return zoneId;
    }

    /**
     * 
     * @param zoneId
     *     The zoneId
     */
    public void setZoneId(Integer zoneId) {
        this.zoneId = zoneId;
    }

    /**
     * 
     * @return
     *     The salesAreaId
     */
    public Integer getSalesAreaId() {
        return salesAreaId;
    }

    /**
     * 
     * @param salesAreaId
     *     The salesAreaId
     */
    public void setSalesAreaId(Integer salesAreaId) {
        this.salesAreaId = salesAreaId;
    }

}
