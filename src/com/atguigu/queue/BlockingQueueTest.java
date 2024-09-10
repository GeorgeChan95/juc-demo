package com.atguigu.queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author George Chan
 * @date 2024/9/7 20:40
 * <p></p>
 */
public class BlockingQueueTest {


    public static void main(String[] args) throws InterruptedException {

        /********************* 第一种: 抛出异常 ***********************/
        BlockingQueue<Object> bQueue1 = new ArrayBlockingQueue<>(3);
        System.out.println(bQueue1.add("a")); // true
        System.out.println(bQueue1.add("b")); // true
        System.out.println(bQueue1.add("c")); // true
//        System.out.println(bQueue1.add("d")); // Exception in thread "main" java.lang.IllegalStateException: Queue full
        // 检查
        System.out.println(bQueue1.element()); // a
        System.out.println(bQueue1.remove()); // a
        System.out.println(bQueue1.remove()); // b
        System.out.println(bQueue1.element()); // c


        /********************* 第二种: 特殊值 ***********************/
        BlockingQueue<Object> bQueue2 = new ArrayBlockingQueue<>(3);
        System.out.println(bQueue2.offer("e")); // true
        System.out.println(bQueue2.offer("f")); // true
        System.out.println(bQueue2.offer("g")); // true
        System.out.println(bQueue2.offer("h")); // false
        System.out.println(bQueue2.peek()); // e
        System.out.println(bQueue2.poll()); // e
        System.out.println(bQueue2.poll()); // f


        /********************* 第三种: 阻塞 ***********************/
        BlockingQueue<Object> bQueue3 = new ArrayBlockingQueue<>(3);
        bQueue3.put("j");
        bQueue3.put("k");
        bQueue3.put("l");
        System.out.println(bQueue3.take()); // j
        bQueue3.put("m");
        System.out.println("m"); // m
        bQueue3.put("n"); // 阻塞


        /********************* 第四种: 超时 ***********************/
        BlockingQueue<Object> bQueue4 = new ArrayBlockingQueue<>(3);
        System.out.println(bQueue4.offer("o", 3, TimeUnit.SECONDS)); // true
        System.out.println(bQueue4.offer("p", 3, TimeUnit.SECONDS)); // true
        System.out.println(bQueue4.offer("q", 3, TimeUnit.SECONDS)); // true
        System.out.println(bQueue4.offer("r", 3, TimeUnit.SECONDS)); // false
        System.out.println(bQueue4.poll(2, TimeUnit.SECONDS)); // o
        System.out.println(bQueue4.poll(2, TimeUnit.SECONDS)); // p
        System.out.println(bQueue4.poll(2, TimeUnit.SECONDS)); // q
        System.out.println(bQueue4.poll(2, TimeUnit.SECONDS)); // null
    }
}
