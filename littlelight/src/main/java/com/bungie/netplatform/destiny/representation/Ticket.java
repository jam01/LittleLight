
package com.bungie.netplatform.destiny.representation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Ticket {

    @SerializedName("hasTicket")
    @Expose
    public boolean hasTicket;
    @SerializedName("maxWins")
    @Expose
    public long maxWins;
    @SerializedName("maxLosses")
    @Expose
    public long maxLosses;
    @SerializedName("wins")
    @Expose
    public long wins;
    @SerializedName("losses")
    @Expose
    public long losses;

}
