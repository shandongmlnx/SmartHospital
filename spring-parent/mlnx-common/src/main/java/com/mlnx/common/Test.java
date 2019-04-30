package com.mlnx.common;

import com.mlnx.common.utils.ids.MongodbId;

/**
 * Created by amanda.shan on 2018/4/25.
 */
public class Test {

    public static void main(String[] args) {

        for (int i = 0; i < 10; i++) {
            MongodbId id = new MongodbId();
            System.out.println(id.toHexString());
        }
    }
}
