package com.atguigu.completable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

//异步调用和同步调用
public class CompletableFutureDemo {
    public static void main(String[] args) throws Exception {
//        method1();
//        method2();
//        method3();
//        method4();
//        method5();
//        method6();
//        method7();
//        method8();
//        method9();
//        method10();
        method11();
//        m2();
    }

    private static void m2() throws InterruptedException, ExecutionException {
        //同步调用
        CompletableFuture<Void> completableFuture1 = CompletableFuture.runAsync(() -> {
            System.out.println(Thread.currentThread().getName() + " : CompletableFuture1");
        });
        completableFuture1.get();

        //mq消息队列
        //异步调用
        CompletableFuture<Integer> completableFuture2 = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + " : CompletableFuture2");
            //模拟异常
            int i = 10 / 0;
            return 1024;
        });
        completableFuture2.whenComplete((t, u) -> {
            System.out.println("------t=" + t);
            System.out.println("------u=" + u);
        }).get();
    }

    /**
     * 主线程里面创建一个 CompletableFuture，然后主线程调用 get 方法会阻塞，最后我们在一个子线程中使其终止
     *
     * @throws InterruptedException
     * @throws ExecutionException
     */
    private static void method1() throws InterruptedException, ExecutionException {
        CompletableFuture<String> future = new CompletableFuture<>();
        new Thread(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + "开始作业");
                TimeUnit.SECONDS.sleep(5);
                System.out.println(Thread.currentThread().getName() + " 完成作业");

                future.complete("success");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "线程1").start();

        //主线程调用 get 方法阻塞
        System.out.println("主线程调用 get 方法获取结果为: " + future.get());
        System.out.println("主线程完成,阻塞结束!!!!!!");
    }


    /**
     * 没有返回值的异步任务
     *
     * @throws InterruptedException
     * @throws ExecutionException
     */
    private static void method2() throws InterruptedException, ExecutionException {
        CompletableFuture<Void> future = new CompletableFuture<>();
        new Thread(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + "开始作业");
                TimeUnit.SECONDS.sleep(5);
                System.out.println(Thread.currentThread().getName() + " 完成作业");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "线程1").start();

        //主线程调用 get 方法阻塞
        future.get();
        System.out.println("主线程结束");
    }

    /**
     * 有返回值的异步任务
     *
     * @throws InterruptedException
     * @throws ExecutionException
     */
    private static void method3() throws InterruptedException, ExecutionException {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + "开始作业");
                TimeUnit.SECONDS.sleep(5);
                System.out.println(Thread.currentThread().getName() + " 完成作业");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "异步结果";
        });

        //主线程调用 get 方法阻塞
        future.get();
        System.out.println("主线程结束");
    }

    private static int num = 0;

    /**
     * 线程依赖
     * 先对一个数加 10,然后取平方
     *
     * @throws InterruptedException
     * @throws ExecutionException
     */
    private static void method4() throws InterruptedException, ExecutionException {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "开始对值进行 +10 操作");
            num += 10;
            System.out.println(Thread.currentThread().getName() + " 完成作业");
            return num;
        }).thenApply(new Function<Integer, Integer>() {
            @Override
            public Integer apply(Integer data) {
                System.out.println(Thread.currentThread().getName() + "开始对值进行 取平方 操作");
                System.out.println("integer: " + data);
                return data * data;
            }
        });

        //主线程调用 get 方法阻塞
        System.out.println("主线程阻塞获取结果,结果为:" + future.get());
        System.out.println("主线程结束");
    }

    /**
     * thenAccept 消费处理结果, 接收任务的处理结果，并消费处理，无返回结果。
     *
     * @throws InterruptedException
     * @throws ExecutionException
     */
    private static void method5() throws InterruptedException, ExecutionException {
        CompletableFuture<Void> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "开始对值进行 +10 操作");
            num += 10;
            System.out.println(Thread.currentThread().getName() + " 完成作业");
            return num;
        }).thenApply(data -> {
            System.out.println(Thread.currentThread().getName() + "开始对值进行 取平方 操作");
            System.out.println("integer: " + data);
            return data * data;
        }).thenAccept(integer -> {
            System.out.println("子线程全部处理完成,最后调用了 accept,结果为:" + integer);
        });

        //主线程调用 get 方法阻塞
        System.out.println("主线程阻塞获取结果,结果为:" + completableFuture.get());
        System.out.println("主线程结束");
    }

    /**
     * exceptionally 异常处理,出现异常时触发
     *
     * @throws InterruptedException
     * @throws ExecutionException
     */
    private static void method6() throws InterruptedException, ExecutionException {
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
            int i = 1 / 0;
            System.out.println(Thread.currentThread().getName() + "开始对值进行 +10 操作");
            num += 10;
            System.out.println(Thread.currentThread().getName() + " 完成作业");
            return num;
        }).exceptionally(throwable -> {
            System.out.println(throwable.getMessage());
            return -1;
        });

        //主线程调用 get 方法阻塞
        System.out.println("主线程阻塞获取结果,结果为:" + completableFuture.get());
        System.out.println("主线程结束");
    }

    /**
     * handle 类似于 thenAccept/thenRun 方法,是最后一步的处理调用,但是同时可以处理异常
     */
    private static void method7() {
        System.out.println("主线程开始");
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("加 10 任务开始");
            int i = 1 / 0;
            num += 10;
            return num;
        }).handle((i, ex) -> {
            System.out.println("进入 handle 方法");
            if (ex != null) {
                System.out.print("发生了异常,内容为:");
                System.out.println(ex.getMessage());
                return -1;
            } else {
                System.out.println("正常完成,内容为: " + i);
                return i;
            }
        });
    }

    /**
     * thenCompose 合并两个有依赖关系的 CompletableFutures 的执行结果
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    private static void method8() throws ExecutionException, InterruptedException {
        System.out.println("主线程开始......");
        //第一步加 10
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "线程执行num加10");
            num += 10;
            return num;
        });

        // 合并
        CompletableFuture<Integer> future2 = future1.thenCompose(data -> CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "线程执行合并");
            return data + 1;
        }));

        System.out.println("主线程阻塞获取结果: " + future1.get());
        System.out.println("主线程阻塞获取结果: " + future2.get());
    }

    /**
     * thenCombine 合并两个没有依赖关系的 CompletableFutures 任务
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    private static void method9() throws ExecutionException, InterruptedException {
        System.out.println("主线程开始......");
        //第一个线程加 10
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "线程执行num加10");
            num += 10;
            return num;
        });

        // 第二个线程 *10
        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "线程执行num * 10");
            num *= 10;
            return num;
        });

        // 合并两个CompletableFuture
        CompletableFuture<Object> combine = future1.thenCombine(future2, (a, b) -> {
            List<Integer> list = new ArrayList<>();
            list.add(a);
            list.add(b);
            return list;
        });

        System.out.println("主线程阻塞获取future1结果: " + future1.get());
        System.out.println("主线程阻塞获取future2结果: " + future2.get());
        System.out.println("主线程阻塞获取combine结果: " + combine.get());
    }


    /**
     * allOf: 一系列独立的 future 任务，等其所有的任务执行完后做一些事情
     */
    private static void method10() {
        System.out.println("主线程开始");
        List<CompletableFuture> list = new ArrayList<>();
        CompletableFuture<Integer> job1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("加 10 任务开始");
            num += 10;
            return num;
        });
        list.add(job1);

        CompletableFuture<Integer> job2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("乘以 10 任务开始");
            num = num * 10;
            return num;
        });
        list.add(job2);

        CompletableFuture<Integer> job3 = CompletableFuture.supplyAsync(() -> {
            System.out.println("减以 10 任务开始");
            num = num * 10;
            return num;
        });
        list.add(job3);

        CompletableFuture<Integer> job4 = CompletableFuture.supplyAsync(() -> {
            System.out.println("除以 10 任务开始");
            num = num * 10;
            return num;
        });
        list.add(job4);

        //多任务合并
        List<Integer> collect =
                list.stream().map(CompletableFuture<Integer>::join).collect(Collectors.toList());
        System.out.println("所有任务执行结束,结果为: " + collect);
    }

    /**
     * anyOf: 只要在多个 future 里面有一个返回，整个任务就可以结束，而不需要等到每一个future 结束
     * @throws ExecutionException
     * @throws InterruptedException
     */
    private static void method11() throws ExecutionException, InterruptedException {
        System.out.println("主线程开始");
        CompletableFuture<Integer>[] futures = new CompletableFuture[4];
        CompletableFuture<Integer> job1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("加 10 任务开始");
            num += 10;
            return num;
        });
        futures[0] = job1;

        CompletableFuture<Integer> job2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("乘以 10 任务开始");
            num = num * 10;
            return num;
        });
        futures[1] = job2;

        CompletableFuture<Integer> job3 = CompletableFuture.supplyAsync(() -> {
            System.out.println("减以 10 任务开始");
            num = num * 10;
            return num;
        });
        futures[2] = job3;

        CompletableFuture<Integer> job4 = CompletableFuture.supplyAsync(() -> {
            System.out.println("除以 10 任务开始");
            num = num * 10;
            return num;
        });
        futures[3] = job4;

        CompletableFuture<Object> future = CompletableFuture.anyOf(futures);
        System.out.println(future.get());
        System.out.println("其中一个任务执行结束,结果为: " + future.get());
    }
}