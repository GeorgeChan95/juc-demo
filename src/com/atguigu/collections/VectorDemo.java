package com.atguigu.collections;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Vector;

/**
 * @ClassName VectorDemo
 * @Description Vector 对集合进行操作，线程安全
 * @Author George
 * @Date 2024/9/4 10:12
 */
public class VectorDemo {

    /**
     * 多个线程同时对集合进行修改
     * @param args
     */
    public static void main(String[] args) {
        List list = new Vector();
        for (int i = 0; i < 100; i++) {
            new Thread(() ->{
                list.add(UUID.randomUUID().toString());
                System.out.println(list);
            }, "线程" + i).start();
        }
    }
}
