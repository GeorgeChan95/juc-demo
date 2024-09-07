package com.atguigu.callable;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @ClassName CallableTest
 * @Description TODO
 * @Author George
 * @Date 2024/9/7 8:48
 */
public class CallableTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // Runable 调用线程
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "执行了run方法");
        }, "Runable").start();

        // 使用Callable调用线程
        FutureTask<String> futureTask = new FutureTask<>(() -> {
            System.out.println(Thread.currentThread().getName() +  "执行了call方法");
            return "Callable的call方法返回结果";
        });
        new Thread(futureTask, "Callable").start();

        String result = futureTask.get();
        System.out.println(result);
    }
}
