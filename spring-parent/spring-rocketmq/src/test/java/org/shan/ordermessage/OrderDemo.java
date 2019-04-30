package org.shan.ordermessage;

/**
 * Created by amanda.shan on 2018/10/18.
 */
public class OrderDemo {

    private Long orderId;

    private String desc;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "OrderDemo{" +
                "orderId=" + orderId +
                ", desc='" + desc + '\'' +
                '}';
    }
}
