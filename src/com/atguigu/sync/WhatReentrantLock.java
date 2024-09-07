package com.atguigu.sync;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName WhatReentrantLock
 * @Description TODO
 * @Author George
 * @Date 2024/9/6 9:27
 */
public class WhatReentrantLock {
    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        new Thread(() -> {
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + " 获取到第一把锁");
                lock.lock();
                try {
                    System.out.println(Thread.currentThread().getName() + " 获取到第二把锁");
                } finally {
                    lock.unlock();
                }
            } finally {
                lock.unlock();
            }
        }).start();
    }
}
