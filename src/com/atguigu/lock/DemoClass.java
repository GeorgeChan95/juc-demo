package com.atguigu.lock;

/**
 * @ClassName DemoClass
 * @Description synchronized 方案 实现两个线程并发修改数值
 * @Author George
 * @Date 2024/9/4 7:49
 */


class TestVolatile {
    public static void main(String[] args) {
        DemoClass demoClass = new DemoClass();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    demoClass.increment();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "线程A").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    demoClass.decrement();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "线程B").start();
    }
}

public class DemoClass {
    private int number = 0;

    public synchronized void increment() throws InterruptedException {
        while (number != 0) {
            this.wait();
        }
        number++;
        System.out.println(Thread.currentThread().getName() + "执行了加1操作，当前值为：" + number);
        notifyAll();
    }


    public synchronized void decrement() throws InterruptedException {
        while (number != 1) {
            this.wait();
        }
        number--;
        System.out.println(Thread.currentThread().getName() + "执行了减1操作，当前值为：" + number);
        notifyAll();
    }
}
