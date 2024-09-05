package com.atguigu.collections;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName ConcurrentHashMapDemo
 * @Description TODO
 * @Author George
 * @Date 2024/9/5 19:42
 */
public class ConcurrentHashMapDemo {
    public static void main(String[] args) {
        // 线程不安全的操作
//        HashMap<String, String> map = new HashMap<>();
        // 线程安全的操作
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        for (int i = 0; i < 1000; i++) {
            new Thread(() ->{
                map.put(UUID.randomUUID().toString(), "1");
                System.out.println(map.values());
            }, "线程" + i).start();
        }
    }
}
