package com.atguigu.callable;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @ClassName FutureTest
 * @Description TODO
 * @Author George
 * @Date 2024/9/7 9:59
 */
public class FutureTest {
    public static void main(String[] args) throws Exception {
        // 创建一个线程池
        ExecutorService executor = Executors.newFixedThreadPool(1);
        // 初始化一个任务
        Callable<String> task = new Task();
        // 提交任务并获得Future的实例
        Future<String> future = executor.submit(task);
        // 从Future获取异步执行返回的结果(可能会阻塞等待结果)
        String result = future.get();
        System.out.println("任务执行结果：" +  result);

        // 任务执行完毕之后，关闭线程池（可选）
        executor.shutdown();
    }
}

class Task implements Callable<String> {

    @Override
    public String call() throws Exception {
        // 执行下载某文件任务，并返回文件名称
        System.out.println("thread name:" +  Thread.currentThread().getName() + " 开始执行下载任务");
        return "xxx.png";
    }
}
