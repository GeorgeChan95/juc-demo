package com.atguigu.collections;

import java.util.HashSet;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @ClassName CopyOnWriteArraySetDemo
 * @Description TODO
 * @Author George
 * @Date 2024/9/5 19:39
 */
public class CopyOnWriteArraySetDemo {
    public static void main(String[] args) {
        // 线程不安全的操作
//        HashSet<String> set = new HashSet<>();
        // 线程安全的操作
        CopyOnWriteArraySet<String> set = new CopyOnWriteArraySet<>();
        for (int i = 0; i < 1000; i++) {
            new Thread(() ->{
                set.add(UUID.randomUUID().toString());
                System.out.println(set);
            }, "线程" + i).start();
        }
    }
}
