package com.bungie.netplatform.destiny.representation;

import com.google.gson.annotations.Expose;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class UserResponse {

    @Expose
    private User user;

    /**
     * @return The user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param data The user
     */
    public void setUser(User user) {
        this.user = user;
    }
}
