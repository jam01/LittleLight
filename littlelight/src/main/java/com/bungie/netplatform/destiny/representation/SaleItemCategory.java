
package com.bungie.netplatform.destiny.representation;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

import com.bungie.netplatform.destiny.representation.*;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class SaleItemCategory {

    @SerializedName("categoryIndex")
    @Expose
    public long categoryIndex;
    @SerializedName("categoryTitle")
    @Expose
    public String categoryTitle;
    @SerializedName("saleItems")
    @Expose
    public List<com.bungie.netplatform.destiny.representation.SaleItem> saleItems = new ArrayList<com.bungie.netplatform.destiny.representation.SaleItem>();

}
