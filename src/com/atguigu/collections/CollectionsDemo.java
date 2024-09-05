package com.atguigu.collections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * @ClassName CollectionsDemo
 * @Description 使用 Collections.synchronizedList 创建一个线程安全的集合
 * @Author George
 * @Date 2024/9/5 15:09
 */
public class CollectionsDemo {
    public static void main(String[] args) {
        List<Object> list = Collections.synchronizedList(new ArrayList<>());
        for (int i = 0; i < 100; i++) {
            new Thread(() ->{
                list.add(UUID.randomUUID().toString());
                System.out.println(list);
            }, "线程" + i).start();
        }
    }
}
