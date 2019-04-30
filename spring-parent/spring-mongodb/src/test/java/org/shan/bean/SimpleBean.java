package org.shan.bean;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by amanda.shan on 2019/1/20.
 */
@Document(collection = "SimpleBean")
public class SimpleBean {

    private String _id;

    private String name;

    private int age;

    public SimpleBean(String _id) {
        this._id = _id;
    }

    public SimpleBean(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public SimpleBean() {
    }

    private byte[] bytes = new byte[10000];

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    @Override
    public String toString() {
        return "SimpleBean{" +
                "_id='" + _id + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
