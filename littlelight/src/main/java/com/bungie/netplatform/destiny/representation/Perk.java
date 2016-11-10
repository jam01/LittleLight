
package com.bungie.netplatform.destiny.representation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Perk {

    @SerializedName("iconPath")
    @Expose
    public String iconPath;
    @SerializedName("perkHash")
    @Expose
    public long perkHash;
    @SerializedName("isActive")
    @Expose
    public boolean isActive;

}
