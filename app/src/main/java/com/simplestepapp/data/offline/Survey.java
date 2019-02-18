
package com.simplestepapp.data.offline;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Survey {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("gujrathi")
    @Expose
    private String gujrathi;
    @SerializedName("id")
    @Expose
    private Integer id;

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
     *     The gujrathi
     */
    public String getGujrathi() {
        return gujrathi;
    }

    /**
     * 
     * @param gujrathi
     *     The gujrathi
     */
    public void setGujrathi(String gujrathi) {
        this.gujrathi = gujrathi;
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

}
