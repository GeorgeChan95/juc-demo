package com.atguigu.juc;

import java.util.concurrent.CountDownLatch;

//演示 CountDownLatch
public class CountDownLatchDemo {
    //6个同学陆续离开教室之后，班长锁门
    public static void main(String[] args) throws InterruptedException {

        CountDownLatch countDownLatch = new CountDownLatch(6);

        for (int i = 1; i <= 6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + " 号同学离开了教室");

                // 计数器减1
                countDownLatch.countDown();
            }, String.valueOf(i)).start();
        }

        // 阻塞，等待计数器为0后，继续往下执行
        countDownLatch.await();
        System.out.println(Thread.currentThread().getName() + "执行了关门操作");
    }
}
