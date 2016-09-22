package com.bungie.netplatform.destiny.representation;

import com.google.gson.annotations.Expose;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Response<T> {

    @Expose
    private T data;

    /**
     * @return The data
     */
    public T getData() {
        return data;
    }

    /**
     * @param data The data
     */
    public void setData(T data) {
        this.data = data;
    }
}
