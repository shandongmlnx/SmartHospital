package com.mlnx.common.utils;

/**
 * Created by amanda.shan on 2018/2/9.
 */
public class RandomUtils{

    public static int getRandom(int size) {
        System.out.println();

        return (int) ((Math.random() * 9 + 1) * Math.pow(10, size-1));
    }

    public static void main(String[] args) {
        System.out.println(getRandom(9));
    }

}
