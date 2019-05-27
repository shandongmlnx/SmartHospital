package com.mlnx.agv.client.protocol;

/**
 * Created by amanda.shan on 2019/1/3.
 */
public class AgvCmdResponse {

    private Integer messageId;

    private boolean sucess;

    public AgvCmdResponse(Integer messageId, boolean sucess) {
        this.messageId = messageId;
        this.sucess = sucess;
    }

    public AgvCmdResponse() {
    }

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public boolean isSucess() {
        return sucess;
    }

    public void setSucess(boolean sucess) {
        this.sucess = sucess;
    }

    @Override
    public String toString() {
        return "AgvCmdResponse{" +
                "messageId=" + messageId +
                ", sucess=" + sucess +
                '}';
    }
}
