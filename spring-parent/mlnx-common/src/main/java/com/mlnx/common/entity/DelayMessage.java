package com.mlnx.common.entity;

import java.util.Date;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * Created by amanda.shan on 2018/5/10.
 */
public class DelayMessage implements Delayed {

    private String id;
    private String messageType;
    private String message;

    private long insertTime;
    private long trigger;

    public DelayMessage() {
    }

    public DelayMessage(String id, String messageType, long trigger) {
        this.id = id;
        this.messageType = messageType;
        this.trigger = trigger;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(trigger - System.currentTimeMillis(), MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        //比较 1是放入队尾  -1是放入队头
        DelayMessage that = (DelayMessage) o;
        if (this.trigger > that.trigger) {
            return 1;
        } else if (this.trigger == that.trigger) {
            return 0;
        } else {
            return -1;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DelayMessage that = (DelayMessage) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        return messageType != null ? messageType.equals(that.messageType) : that.messageType == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (messageType != null ? messageType.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "DelayMessage{" +
                "id='" + id + '\'' +
                ", messageType='" + messageType + '\'' +
                ", message='" + message + '\'' +
                ", insertTime=" + new Date(insertTime) +
                ", trigger=" + new Date(trigger) +
                '}';
    }
}
