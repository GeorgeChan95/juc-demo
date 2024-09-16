package com.atguigu.pool;

import java.util.concurrent.*;

//演示线程池三种常用分类
public class ThreadPoolDemo1 {
    public static void main(String[] args) {
        //一池五线程
        ExecutorService threadPool1 = Executors.newFixedThreadPool(3); //5个窗口

        //一池一线程
        ExecutorService threadPool2 = Executors.newSingleThreadExecutor(); //一个窗口

        //一池可扩容线程
        ExecutorService threadPool3 = Executors.newCachedThreadPool();

        // 创建当前可用的CPU核数的线程数的线程池
        ExecutorService threadPool4 = Executors.newWorkStealingPool();
        //10个顾客请求
        try {
            for (int i = 1; i <= 10; i++) {
                final int num = i;
                //执行
                threadPool1.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + "开始办理业务" + num);
                    try {
                        TimeUnit.SECONDS.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "结束办理业务" + num);
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭线程池
            threadPool1.shutdown();
        }

    }


    /**
     * 可缓存线程池
     *
     * @return
     */
    public static ExecutorService newCachedThreadPool() {

        /**
         * corePoolSize 线程池的核心线程数
         * maximumPoolSize 能容纳的最大线程数
         * keepAliveTime 空闲线程存活时间
         * unit 存活的时间单位
         * workQueue 存放提交但未执行任务的队列
         * threadFactory 创建线程的工厂类:可以省略
         * handler 等待队列满后的拒绝策略:可以省略
         */
        return new ThreadPoolExecutor(0,
                Integer.MAX_VALUE,
                60L,
                TimeUnit.SECONDS,
                new SynchronousQueue<>(),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());
    }


    /**
     * 固定长度线程池
     *
     * @return
     */
    public static ExecutorService newFixedThreadPool() {
        /**
         * corePoolSize 线程池的核心线程数
         * maximumPoolSize 能容纳的最大线程数
         * keepAliveTime 空闲线程存活时间
         * unit 存活的时间单位
         * workQueue 存放提交但未执行任务的队列
         * threadFactory 创建线程的工厂类:可以省略
         * handler 等待队列满后的拒绝策略:可以省略
         */
        return new ThreadPoolExecutor(10,
                10,
                0L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());
    }


    /**
     * 单一线程池
     *
     * @return
     */
    public static ExecutorService newSingleThreadExecutor() {
        /**
         * corePoolSize 线程池的核心线程数
         * maximumPoolSize 能容纳的最大线程数
         * keepAliveTime 空闲线程存活时间
         * unit 存活的时间单位
         * workQueue 存放提交但未执行任务的队列
         * threadFactory 创建线程的工厂类:可以省略
         * handler 等待队列满后的拒绝策略:可以省略
         */
        return new ThreadPoolExecutor(1,
                1,
                0L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());
    }

    public static ScheduledExecutorService newScheduledThreadPool(int corePoolSize,
                                                                  ThreadFactory threadFactory) {
        return new ScheduledThreadPoolExecutor(corePoolSize,
                threadFactory);
    }

    public static ExecutorService newWorkStealingPool(int parallelism) {
        /**
         * parallelism：并行级别，通常默认为 JVM 可用的处理器个数
         * factory：用于创建 ForkJoinPool 中使用的线程。
         * handler：用于处理工作线程未处理的异常，默认为 null
         * asyncMode：用于控制 WorkQueue 的工作模式:队列---反队列
         */
        return new ForkJoinPool(parallelism,
                ForkJoinPool.defaultForkJoinWorkerThreadFactory,
                null,
                true);
    }
}
