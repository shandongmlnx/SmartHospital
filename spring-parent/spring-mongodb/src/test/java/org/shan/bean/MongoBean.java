package org.shan.bean;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * Created by amanda.shan on 2019/1/17.
 */
@Document(collection = "MongoBean")
public class MongoBean {

    private String _id;

    private byte[] bytes = new byte[10];

    private Date date = new Date();

    private long aLong = 1000;

    private int anInt = 200;

    private int[] ints = {11, 22, 33};

    private String string = "休息休息";

    private Person person;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public int[] getInts() {
        return ints;
    }

    public void setInts(int[] ints) {
        this.ints = ints;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getaLong() {
        return aLong;
    }

    public void setaLong(long aLong) {
        this.aLong = aLong;
    }

    public int getAnInt() {
        return anInt;
    }

    public void setAnInt(int anInt) {
        this.anInt = anInt;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }
}
