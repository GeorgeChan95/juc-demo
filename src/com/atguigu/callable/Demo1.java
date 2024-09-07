package com.atguigu.callable;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

//比较两个接口
//实现Runnable接口
class MyThread1 implements Runnable {
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "执行了run方法");
    }
}

//实现Callable接口
class MyThread2 implements Callable {

    @Override
    public Integer call() throws Exception {
        System.out.println(Thread.currentThread().getName()+" come in callable");
        return 200;
    }
}

public class Demo1 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //Runnable接口创建线程
        new Thread(new MyThread1(),"MyThread1").start();

        //Callable接口,报错，没有这个类型的构造方法
       // new Thread(new MyThread2(),"BB").start();

        //FutureTask
        FutureTask<Integer> futureTask1 = new FutureTask<>(new MyThread2());

        //lam表达式
        FutureTask<Integer> futureTask2 = new FutureTask<>(()->{
            System.out.println(Thread.currentThread().getName()+" come in callable");
            return 1024;
        });

        //创建一个线程
        new Thread(futureTask2,"futureTask2").start();
        new Thread(futureTask1,"futureTask1").start();

        while(!futureTask2.isDone()) {
            System.out.println("futureTask2 wait.....");
        }
        //调用FutureTask的get方法
        System.out.println("futureTask2结果：" + futureTask2.get());

        System.out.println("futureTask1结果：" + futureTask1.get());

        System.out.println(Thread.currentThread().getName()+" come over");
    }
}
