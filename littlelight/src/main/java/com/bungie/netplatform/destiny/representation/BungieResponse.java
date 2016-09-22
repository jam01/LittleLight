
package com.bungie.netplatform.destiny.representation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class BungieResponse<T> {

    @SerializedName("Response")
    @Expose
    private com.bungie.netplatform.destiny.representation.Response<T> Response;
    @SerializedName("ThrottleSeconds")
    @Expose
    private Long ThrottleSeconds;
    @SerializedName("MessageData")
    @Expose
    private com.bungie.netplatform.destiny.representation.MessageData MessageData;
    @SerializedName("ErrorCode")
    @Expose
    private Long ErrorCode;
    @SerializedName("Message")
    @Expose
    private String Message;
    @SerializedName("ErrorStatus")
    @Expose
    private String ErrorStatus;

    /**
     * @return The Response
     */
    public com.bungie.netplatform.destiny.representation.Response<T> getResponse() {
        return Response;
    }

    /**
     * @param Response The Response
     */
    public void setResponse(com.bungie.netplatform.destiny.representation.Response<T> Response) {
        this.Response = Response;
    }

    /**
     * @return The ThrottleSeconds
     */
    public Long getThrottleSeconds() {
        return ThrottleSeconds;
    }

    /**
     * @param ThrottleSeconds The ThrottleSeconds
     */
    public void setThrottleSeconds(Long ThrottleSeconds) {
        this.ThrottleSeconds = ThrottleSeconds;
    }

    /**
     * @return The MessageData
     */
    public com.bungie.netplatform.destiny.representation.MessageData getMessageData() {
        return MessageData;
    }

    /**
     * @param MessageData The MessageData
     */
    public void setMessageData(com.bungie.netplatform.destiny.representation.MessageData MessageData) {
        this.MessageData = MessageData;
    }

    /**
     * @return The ErrorCode
     */
    public Long getErrorCode() {
        return ErrorCode;
    }

    /**
     * @param ErrorCode The ErrorCode
     */
    public void setErrorCode(Long ErrorCode) {
        this.ErrorCode = ErrorCode;
    }

    /**
     * @return The Message
     */
    public String getMessage() {
        return Message;
    }

    /**
     * @param Message The Message
     */
    public void setMessage(String Message) {
        this.Message = Message;
    }

    /**
     * @return The ErrorStatus
     */
    public String getErrorStatus() {
        return ErrorStatus;
    }

    /**
     * @param ErrorStatus The ErrorStatus
     */
    public void setErrorStatus(String ErrorStatus) {
        this.ErrorStatus = ErrorStatus;
    }
}
