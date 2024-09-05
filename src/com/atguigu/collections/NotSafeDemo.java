package com.atguigu.collections;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @ClassName NotSafeDemo
 * @Description 不安全的方式操作集合
 * @Author George
 * @Date 2024/9/4 9:32
 */
public class NotSafeDemo {
    /**
     * 多个线程同时对集合进行修改
     * @param args
     */
    public static void main(String[] args) {
        List list = new ArrayList();
        for (int i = 0; i < 100; i++) {
            new Thread(() ->{
                list.add(UUID.randomUUID().toString());
                System.out.println(list);
            }, "线程" + i).start();
        }
    }
}
