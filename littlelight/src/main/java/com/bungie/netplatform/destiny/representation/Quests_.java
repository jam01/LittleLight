
package com.bungie.netplatform.destiny.representation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Quests_ {

    @SerializedName("quests")
    @Expose
    public List<Quest> quests = new ArrayList<Quest>();
    @SerializedName("enabled")
    @Expose
    public boolean enabled;

}
