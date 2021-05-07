package com.prim.honorkings.demo.kotlin;

import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * @author sufulu
 * @version 1.0.0
 * @desc 限流
 * @time 5/5/21 - 10:07 PM
 * @contact sufululove@gmail.com
 * @name Summer
 */
public class SemaphoreDemo {
    public static void main(String[] args) {
        //限流3人
        final Semaphore semaphore = new Semaphore(3, true);
        //一共10人排队
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String name = Thread.currentThread().getName();
                        //
                        semaphore.acquire(2);
                        System.out.println(name + "获取到了许可证，进去游玩了");
                        Thread.sleep(new Random().nextInt(5000));
                        semaphore.release(2);
                        System.out.println(name + "归还了许可证");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}
