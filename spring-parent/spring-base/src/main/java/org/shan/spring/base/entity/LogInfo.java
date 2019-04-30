package org.shan.spring.base.entity;

/**
 * Created by amanda.shan on 2018/10/31.
 */
public class LogInfo {
    private Long startTime = System.currentTimeMillis();
    private StringBuffer bfParams = new StringBuffer();

    public Long getStartTime() {
        return startTime;
    }

    public StringBuffer getBfParams() {
        return bfParams;
    }
}
