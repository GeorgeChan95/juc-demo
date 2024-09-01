package com.atguigu.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName ReentrantLockDemo
 * @Description TODO
 * @Author George
 * @Date 2024/9/1 8:32
 */

// 定义资源类、属性、方法
class LTickets {

    //票数量
    private int num = 30;

    public int getNum() {
        return num;
    }

    // 创建可重入锁
    private ReentrantLock lock = new ReentrantLock(true);

    //卖票方法
    public void sale() {
        //上锁
        lock.lock();
        try {
            // 判断是否还有票
            if (num > 0) {
                System.out.println(Thread.currentThread().getName() + " 卖出1张票,还剩：" + --num +" 张票");
            }
        } finally {
            // 解锁
            lock.unlock();
        }
    }

}

public class ReentrantLockDemo {
    //第二步 创建多个线程，调用资源类的操作方法
    //创建三个线程
    public static void main(String[] args) {
        LTickets lTickets = new LTickets();

        new Thread(() -> {
            while (lTickets.getNum() > 0) {
                lTickets.sale();
            }
        }, "售票员1").start();

        new Thread(() -> {
            while (lTickets.getNum() > 0) {
                lTickets.sale();
            }
        }, "售票员2").start();

        new Thread(() -> {
            while (lTickets.getNum() > 0) {
                lTickets.sale();
            }
        }, "售票员3").start();

    }
}
