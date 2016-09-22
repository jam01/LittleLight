
package com.bungie.netplatform.destiny.representation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class SmallImage {

    @SerializedName("sheetPath")
    @Expose
    private String sheetPath;
    @SerializedName("rect")
    @Expose
    private Rect rect;
    @SerializedName("sheetSize")
    @Expose
    private SheetSize sheetSize;

    /**
     * @return The sheetPath
     */
    public String getSheetPath() {
        return sheetPath;
    }

    /**
     * @param sheetPath The sheetPath
     */
    public void setSheetPath(String sheetPath) {
        this.sheetPath = sheetPath;
    }

    /**
     * @return The rect
     */
    public Rect getRect() {
        return rect;
    }

    /**
     * @param rect The rect
     */
    public void setRect(Rect rect) {
        this.rect = rect;
    }

    /**
     * @return The sheetSize
     */
    public SheetSize getSheetSize() {
        return sheetSize;
    }

    /**
     * @param sheetSize The sheetSize
     */
    public void setSheetSize(SheetSize sheetSize) {
        this.sheetSize = sheetSize;
    }
}
