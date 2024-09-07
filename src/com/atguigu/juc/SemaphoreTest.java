package com.atguigu.juc;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName SemaphoreTest
 * @Description TODO
 * @Author George
 * @Date 2024/9/7 15:20
 */
public class SemaphoreTest {
    public static void main(String[] args) {
        //创建Semaphore，设置许可数量
        Semaphore semaphore = new Semaphore(3);
        for (int i = 1; i <= 6; i++) {
            new Thread(() -> {
                try {
                    // 获取许可，在获取到前，线程阻塞
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + " 找到了车位");
                    // 设置停车时间
                    TimeUnit.SECONDS.sleep(new Random().nextInt(5));
                    // 离开车位
                    System.out.println(Thread.currentThread().getName()+"------离开了车位");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    // 释放许可
                    semaphore.release();
                }

            }, "车辆" + i).start();
        }
    }
}
