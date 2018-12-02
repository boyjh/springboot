package com.xwbing.demo;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author xiangwb
 * @date 2018/12/2 09:30
 * @description 线程间的通信
 * 编写两个线程，一个线程打印1~52，另一个线程打印字母A~Z，打印顺序为12A34B56C……5152Z
 */
public class ThreadCommunication {
    private static final ThreadPoolExecutor EXECUTOR = new ThreadPoolExecutor(20, 35, 10,
            TimeUnit.SECONDS, new ArrayBlockingQueue<>(1000), new ThreadPoolExecutor.DiscardPolicy());

    public static String[] buildNoArr(int max) {
        String[] noArr = new String[max];
        for (int i = 0; i < max; i++) {
            noArr[i] = Integer.toString(i + 1);
        }
        return noArr;
    }

    public static String[] buildCharArr(int max) {
        String[] charArr = new String[max];
        int tmp = 65;
        for (int i = 0; i < max; i++) {
            charArr[i] = String.valueOf((char) (tmp + i));
        }
        return charArr;
    }

    public static void print(String... input) {
        if (input == null)
            return;
        for (String each : input) {
            System.out.print(each);
        }
    }


    public static void main(String[] args) throws InterruptedException {
        MethodOne one = new MethodOne();
        CompletableFuture.runAsync(one.threadOne(), EXECUTOR);
        CompletableFuture.runAsync(one.threadTwo(), EXECUTOR);
        Thread.sleep(10);
        System.out.println("==synchronized、notify、wait==");
        MethodTwo two = new MethodTwo();
        CompletableFuture.runAsync(two.threadOne(), EXECUTOR);
        CompletableFuture.runAsync(two.threadTwo(), EXECUTOR);
        Thread.sleep(10);
        System.out.println("==Lock、Condition==");
        MethodFour four = new MethodFour();
        CompletableFuture.runAsync(four.threadOne(), EXECUTOR);
        CompletableFuture.runAsync(four.threadTwo(), EXECUTOR);
        Thread.sleep(10);
        System.out.println("==AtomicInteger==");
        MethodThree three = new MethodThree();
        CompletableFuture.runAsync(three.threadOne(), EXECUTOR);
        CompletableFuture.runAsync(three.threadTwo(), EXECUTOR);
        Thread.sleep(10);
        System.out.println("==volatile==");
    }
}

/**
 * 利用最基本的synchronized、notify、wait,靠共享变量来做控制
 * wait()、notify() 方法是Object方法
 * 一般在synchronized同步代码块里使用 wait()、notify()。
 */
class MethodOne {
    private final Share share = new Share();

    class Share {
        int value = 1;
    }

    public Runnable threadOne() {
        final String[] inputArr = ThreadCommunication.buildNoArr(52);
        return new Runnable() {
            private String[] arr = inputArr;

            public void run() {
                try {
                    for (int i = 0; i < arr.length; i = i + 2) {
                        synchronized (share) {
                            while (share.value == 2)
                                share.wait();
                            ThreadCommunication.print(arr[i], arr[i + 1]);
                            share.value = 2;
                            share.notify();
                        }
                    }
                } catch (InterruptedException e) {
                    System.out.println("Oops...");
                }
            }
        };
    }

    public Runnable threadTwo() {
        final String[] inputArr = ThreadCommunication.buildCharArr(26);
        return new Runnable() {
            private String[] arr = inputArr;

            public void run() {
                try {
                    for (int i = 0; i < arr.length; i++) {
                        synchronized (share) {
                            while (share.value == 1)
                                share.wait();
                            ThreadCommunication.print(arr[i]);
                            share.value = 1;
                            share.notify();
                        }
                    }
                } catch (InterruptedException e) {
                    System.out.println("Oops...");
                }
            }
        };
    }
}

/**
 * 利用Lock和Condition，靠共享变量来做控制
 */
class MethodTwo {
    private Lock lock = new ReentrantLock(true);
    private Condition condition = lock.newCondition();
    private final Share share = new Share();

    class Share {
        int value = 1;
    }

    public Runnable threadOne() {
        final String[] inputArr = ThreadCommunication.buildNoArr(52);
        return new Runnable() {
            private String[] arr = inputArr;

            public void run() {
                for (int i = 0; i < arr.length; i = i + 2) {
                    try {
                        lock.lock();
                        while (share.value == 2)
                            condition.await();
                        ThreadCommunication.print(arr[i], arr[i + 1]);
                        share.value = 2;
                        condition.signal();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        lock.unlock();
                    }
                }
            }
        };
    }

    public Runnable threadTwo() {
        final String[] inputArr = ThreadCommunication.buildCharArr(26);
        return new Runnable() {
            private String[] arr = inputArr;

            public void run() {
                for (int i = 0; i < arr.length; i++) {
                    try {
                        lock.lock();
                        while (share.value == 1)
                            condition.await();
                        ThreadCommunication.print(arr[i]);
                        share.value = 1;
                        condition.signal();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        lock.unlock();
                    }
                }
            }
        };
    }
}

/**
 * 利用volatile：
 * volatile修饰的变量值直接存在main memory里面，子线程对该变量的读写直接写入main memory，而不是像其它变量一样在local thread里面产生一份copy。
 * volatile能保证所修饰的变量对于多个线程可见性，即只要被修改，其它线程读到的一定是最新的值。
 */
class MethodThree {
    private volatile Share share = new Share();

    class Share {
        int value = 1;
    }

    public Runnable threadOne() {
        final String[] inputArr = ThreadCommunication.buildNoArr(52);
        return new Runnable() {
            private String[] arr = inputArr;

            public void run() {
                for (int i = 0; i < arr.length; i = i + 2) {
                    while (share.value == 2) {
                    }
                    ThreadCommunication.print(arr[i], arr[i + 1]);
                    share.value = 2;
                }
            }
        };
    }

    public Runnable threadTwo() {
        final String[] inputArr = ThreadCommunication.buildCharArr(26);
        return new Runnable() {
            private String[] arr = inputArr;

            public void run() {
                for (int i = 0; i < arr.length; i++) {
                    while (share.value == 1) {
                    }
                    ThreadCommunication.print(arr[i]);
                    share.value = 1;
                }
            }
        };
    }
}

/**
 * 利用AtomicInteger
 */
class MethodFour {
    private AtomicInteger share = new AtomicInteger(1);

    public Runnable threadOne() {
        final String[] inputArr = ThreadCommunication.buildNoArr(52);
        return new Runnable() {
            private String[] arr = inputArr;

            public void run() {
                for (int i = 0; i < arr.length; i = i + 2) {
                    while (share.get() == 2) {
                    }
                    ThreadCommunication.print(arr[i], arr[i + 1]);
                    share.set(2);
                }
            }
        };
    }

    public Runnable threadTwo() {
        final String[] inputArr = ThreadCommunication.buildCharArr(26);
        return new Runnable() {
            private String[] arr = inputArr;

            public void run() {
                for (int i = 0; i < arr.length; i++) {
                    while (share.get() == 1) {
                    }
                    ThreadCommunication.print(arr[i]);
                    share.set(1);
                }
            }
        };
    }
}
