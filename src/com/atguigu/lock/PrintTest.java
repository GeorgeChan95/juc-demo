package com.atguigu.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName PrintTest
 * @Description TODO
 * @Author George
 * @Date 2024/9/4 8:07
 */
public class PrintTest {

    // 线程通信标识：0-线程A  1-线程B  2-线程C
    private int number = 0;
    // 创建锁
    private Lock lock = new ReentrantLock();
    // 线程A的通信钥匙
    private Condition conditionA =lock.newCondition();
    // 线程B的通信钥匙
    private Condition conditionB =lock.newCondition();
    // 线程C的通信钥匙
    private Condition conditionC =lock.newCondition();

    /**
     * 打印五次A
     */
    public void printA(int round) {
        try {
            lock.lock();
            while (number != 0) {
                conditionA.await(); // 等待并释放锁
            }
            for (int i = 0; i < 5; i++) {
                System.out.println("第 " + round + "轮：" + Thread.currentThread().getName() + "执行中，开始打印：=== A ===");
            }
            // 打印B
            number = 1;
            conditionB.signal();
        } catch (Exception e) {

        } finally {
            lock.unlock();
        }
    }

    /**
     * 打印10次B
     */
    public void printB(int round) {
        try {
            lock.lock();
            while (number != 1) {
                conditionB.await(); // 等待并释放锁
            }
            for (int i = 0; i < 10; i++) {
                System.out.println("第 " + round + "轮：" + Thread.currentThread().getName() + "执行中，开始打印：=== B ===");
            }
            // 打印B
            number = 2;
            conditionC.signal();
        } catch (Exception e) {

        } finally {
            lock.unlock();
        }
    }

    /**
     * 打印15次C
     */
    public void printC(int round) {
        try {
            lock.lock();
            while (number != 2) {
                conditionC.await(); // 等待并释放锁
            }
            for (int i = 0; i < 15; i++) {
                System.out.println("第 " + round + "轮：" + Thread.currentThread().getName() + "执行中，开始打印：=== C ===");
            }
            // 打印B
            number = 0;
            conditionA.signal();
        } catch (Exception e) {

        } finally {
            lock.unlock();
        }
    }

}

class MainClass {
    public static void main(String[] args) {
        PrintTest printTest = new PrintTest();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                printTest.printA(i);
            }
        }, "线程A").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                printTest.printB(i);
            }
        }, "线程B").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                printTest.printC(i);
            }
        }, "线程C").start();
    }
}
