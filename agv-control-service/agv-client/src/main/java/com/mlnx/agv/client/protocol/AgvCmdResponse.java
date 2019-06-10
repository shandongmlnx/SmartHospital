package com.mlnx.agv.client.protocol;

/**
 * Created by amanda.shan on 2019/1/3.
 */
public class  AgvCmdResponse {

    private String onlyTaskId;

    private boolean success;

    public AgvCmdResponse(String taskId, boolean sucess) {
        this.onlyTaskId = taskId;
        this.success = sucess;
    }

    public AgvCmdResponse() {
    }

    public String getOnlyTaskId() {
        return onlyTaskId;
    }

    public void setOnlyTaskId(String onlyTaskId) {
        this.onlyTaskId = onlyTaskId;
    }

    public boolean isSucess() {
        return success;
    }

    public void setSucess(boolean sucess) {
        this.success = sucess;
    }

    @Override
    public String toString() {
        return "AgvCmdResponse{" +
                "onlyTaskId=" + onlyTaskId +
                ", sucess=" + success +
                '}';
    }
}
