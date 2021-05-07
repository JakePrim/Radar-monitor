package com.prim.honorkings.demo.kotlin;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * @author sufulu
 * @version 1.0.0
 * @desc
 * @time 5/5/21 - 10:02 PM
 * @contact sufululove@gmail.com
 * @name Summer
 */
public class CountDownLatchDemo {
    public static void main(String[] args) throws InterruptedException {
        //模拟多人过山车的场景 ，等待5人全部准备好才能发车
        CountDownLatch downLatch = new CountDownLatch(5);
        for (int i = 0; i < 5; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(new Random().nextInt(4000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "准备好了");
                    downLatch.countDown();
                }
            }).start();
        }
        downLatch.await();
        System.out.println("所有人准备好了，准备发车....");
    }
}
