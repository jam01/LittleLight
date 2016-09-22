
package com.bungie.netplatform.destiny.representation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class PendingBounties {

    @SerializedName("saleItems")
    @Expose
    private List<SaleItem> saleItems = new ArrayList<SaleItem>();

    /**
     * @return The saleItems
     */
    public List<SaleItem> getSaleItems() {
        return saleItems;
    }

    /**
     * @param saleItems The saleItems
     */
    public void setSaleItems(List<SaleItem> saleItems) {
        this.saleItems = saleItems;
    }

}
