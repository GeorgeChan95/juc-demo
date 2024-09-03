package com.atguigu.lock;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName ReentrantLockTest
 * @Description 可重入锁
 * @Author George
 * @Date 2024/9/3 10:22
 */
public class ReentrantLockTest {
    private ArrayList<Integer> arrayList = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        final ReentrantLockTest test = new ReentrantLockTest();
        Lock lock = new ReentrantLock(); //注意这个地方

        new Thread(() -> {
            test.insert(Thread.currentThread(), lock);
        }).start();

        new Thread(() -> {
            test.insert(Thread.currentThread(), lock);
        }).start();

        Thread.sleep(1000);
    }

    public void insert(Thread thread, Lock lock) {
        lock.lock();
        try {
            System.out.println(thread.getName()+"得到了锁");
            for(int i=0;i<5;i++) {
                arrayList.add(i);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }finally {
            System.out.println(thread.getName()+"释放了锁");
            lock.unlock();
        }
    }

    /**
     * 计算总数
     */
    private void count() {
        int size = arrayList.size();
        System.out.println("总数为：" + size);
    }
}
