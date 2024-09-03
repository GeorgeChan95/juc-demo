package com.atguigu.readwrite;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @ClassName ReentrantReadWriteLockDemo
 * @Description 使用读写锁对缓存内容进行读写测试
 * @Author George
 * @Date 2024/9/3 20:18
 */
public class ReentrantReadWriteLockDemo {
    //创建 map 集合
    private volatile Map<String, Object> map = new HashMap<>();

    //创建读写锁对象
    private ReadWriteLock rwLock = new ReentrantReadWriteLock();

    //放数据
    public void put(String key, Object value) {
        //添加写锁
        rwLock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "正在写数据" + key);
            //暂停一会
            TimeUnit.MICROSECONDS.sleep(300);
            //放数据
            map.put(key, value);
            System.out.println(Thread.currentThread().getName() + "写完了" + key);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //释放写锁
            rwLock.writeLock().unlock();
        }
    }

    //取数据
    public Object get(String key) {
        //添加读锁
        rwLock.readLock().lock();
        Object result = null;
        try {
            System.out.println(Thread.currentThread().getName() + "正在取数据" + key);
            //暂停一会
            TimeUnit.MICROSECONDS.sleep(300);
            result = map.get(key);
            System.out.println(Thread.currentThread().getName() + "取完数据了" + key);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //释放读锁
            rwLock.readLock().unlock();
        }
        return result;
    }

    public static void main(String[] args) {

        ReentrantReadWriteLockDemo demo = new ReentrantReadWriteLockDemo();

        for (int i = 1; i <= 5; i++) {
            final int number = i;
            new Thread(() -> {//5个线程放数据
                demo.put(String.valueOf(number), number);
            }, String.valueOf(i)).start();
        }

        for (int i = 1; i <= 5; i++) {
            final int number = i;
            new Thread(() -> {//5个线程取数据
                demo.get(String.valueOf(number));
            }, String.valueOf(i)).start();
        }
    }
}
