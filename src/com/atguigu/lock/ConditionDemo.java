package com.atguigu.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName ConditionDemo
 * @Description 当前有一个变量 number，初始值是0，创建四个线程同时修改次变量，线程AA、线程CC对变量做++操作， 线程BB、线程DD对变量做--操作。每个线程都执行10次
 * @Author George
 * @Date 2024/9/2 7:33
 */
class ShareNum {
    private int number = 0;

    private ReentrantLock lock = new ReentrantLock(true);
    private Condition condition = lock.newCondition();

    // ++操作
    public void increase() {
        // 上锁
        lock.lock();
        try {
            while (number != 0) {
                condition.await();
            }
            // 执行 ++ 操作
            number++;
            System.out.println(Thread.currentThread().getName() + "执行了 ++ 操作，当前number值为：" + number);
            // 唤醒其它线程
            condition.signalAll();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            // 解锁
            lock.unlock();
        }
    }

    // -- 操作
    public void decrease() {
        // 上锁
        lock.lock();
        try {
            while (number != 1) {
                condition.await();
            }
            // 执行 -- 操作
            number--;
            System.out.println(Thread.currentThread().getName() + "执行了 -- 操作，当前number值为：" + number);
            // 唤醒其它线程
            condition.signalAll();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            // 解锁
            lock.unlock();
        }
    }
}

public class ConditionDemo {
    public static void main(String[] args) {
        ShareNum shareNum = new ShareNum();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                shareNum.increase();
            }
        }, "AA").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                shareNum.decrease();
            }
        }, "BB").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                shareNum.increase();
            }
        }, "CC").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                shareNum.decrease();
            }
        }, "DD").start();
    }
}
