
package com.bungie.netplatform.destiny.representation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Event {

    @SerializedName("eventHash")
    @Expose
    public long eventHash;
    @SerializedName("friendlyIdentifier")
    @Expose
    public String friendlyIdentifier;
    @SerializedName("eventIdentifier")
    @Expose
    public String eventIdentifier;
    @SerializedName("expirationDate")
    @Expose
    public String expirationDate;
    @SerializedName("startDate")
    @Expose
    public String startDate;
    @SerializedName("expirationKnown")
    @Expose
    public boolean expirationKnown;
    @SerializedName("vendor")
    @Expose
    public Vendor vendor;
    @SerializedName("progression")
    @Expose
    public Progression progression;
    @SerializedName("bounties")
    @Expose
    public Bounties bounties;
    @SerializedName("quests")
    @Expose
    public Quests quests;
    @SerializedName("showNagMessage")
    @Expose
    public boolean showNagMessage;
    @SerializedName("active")
    @Expose
    public boolean active;

}
