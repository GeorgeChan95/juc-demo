package com.atguigu.juc;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @ClassName CyclicBarrirtTest
 * @Description TODO
 * @Author George
 * @Date 2024/9/7 14:23
 */
public class CyclicBarrirtTest {
    private static Integer NUMBER = 7;

    public static void main(String[] args) {
        // 每次执行 CyclicBarrier 一次障碍数会加一，如果达到了目标障碍数，才会执行 cyclicBarrier.await()之后的语句。
        CyclicBarrier cyclicBarrier = new CyclicBarrier(NUMBER, () -> System.out.println("集齐7颗龙珠，可以召唤神龙了"));

        for (int i = 1; i <= 7; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + " 号龙珠已收集");
                try {
                    // 执行 await() 方法，障碍数 +1
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (BrokenBarrierException e) {
                    throw new RuntimeException(e);
                }
            }, String.valueOf(i)).start();
        }
    }
}
