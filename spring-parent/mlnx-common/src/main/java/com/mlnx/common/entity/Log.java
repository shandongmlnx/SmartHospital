package com.mlnx.common.entity;

import java.util.Date;

/**
 * Created by amanda.shan on 2018/8/15.
 */
public class Log {

    private static final long serialVersionUID = 1L;

    /**
     * 日志ID
     */
    private Long logId;
    /**
     * 管理员ID
     */
    private Long userId;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 耗时
     */
    private Integer spendTime;
    /**
     * 请求类型
     */
    private String method;
    /**
     * 用户标识
     */
    private String userAgent;
    /**
     * 用户IP
     */
    private String userIp;
    /**
     * 请求内容
     */
    private String optContent;
    /**
     * 请求路径
     */
    private String url;

    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getSpendTime() {
        return spendTime;
    }

    public void setSpendTime(Integer spendTime) {
        this.spendTime = spendTime;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getUserIp() {
        return userIp;
    }

    public void setUserIp(String userIp) {
        this.userIp = userIp;
    }

    public String getOptContent() {
        return optContent;
    }

    public void setOptContent(String optContent) {
        this.optContent = optContent;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Log{" +
                "logId=" + logId +
                ", userId=" + userId +
                ", createTime=" + createTime +
                ", spendTime=" + spendTime +
                ", method='" + method + '\'' +
                ", userAgent='" + userAgent + '\'' +
                ", userIp='" + userIp + '\'' +
                ", optContent='" + optContent + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
