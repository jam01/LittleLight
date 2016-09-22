
package com.bungie.netplatform.destiny.representation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Rect {

    @SerializedName("y")
    @Expose
    private Long y;
    @SerializedName("width")
    @Expose
    private Long width;
    @SerializedName("height")
    @Expose
    private Long height;
    @SerializedName("x")
    @Expose
    private Long x;

    /**
     * @return The y
     */
    public Long getY() {
        return y;
    }

    /**
     * @param y The y
     */
    public void setY(Long y) {
        this.y = y;
    }

    /**
     * @return The width
     */
    public Long getWidth() {
        return width;
    }

    /**
     * @param width The width
     */
    public void setWidth(Long width) {
        this.width = width;
    }

    /**
     * @return The height
     */
    public Long getHeight() {
        return height;
    }

    /**
     * @param height The height
     */
    public void setHeight(Long height) {
        this.height = height;
    }

    /**
     * @return The x
     */
    public Long getX() {
        return x;
    }

    /**
     * @param x The x
     */
    public void setX(Long x) {
        this.x = x;
    }
}
