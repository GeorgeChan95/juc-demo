package com.atguigu.juc;

/**
 * @ClassName NoCountDownLatch
 * @Description TODO
 * @Author George
 * @Date 2024/9/7 11:10
 */
public class NoCountDownLatch {
    //6个同学陆续离开教室之后，班长锁门
    public static void main(String[] args) throws InterruptedException {
        // 创建六个线程，模拟六个学生
        for (int i = 1; i <=6; i++) {
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+"离开教室");
            },String.valueOf(i)).start();
        }
        System.out.println(Thread.currentThread().getName()+"锁门");
    }
}
