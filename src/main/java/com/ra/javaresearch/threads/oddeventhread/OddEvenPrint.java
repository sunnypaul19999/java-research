package com.ra.javaresearch.threads.oddeventhread;

class Driver {
  public volatile int num;
  public final int maxNum;
  public volatile boolean isThread1;
  public final Object MUTEX;

  Driver(int num, int maxNum) {
    this.num = num;
    this.maxNum = maxNum;
    isThread1 = true;
    MUTEX = new Object();
  }
}

class A implements Runnable {
  private final Driver driver;

  A(Driver driver) {
    this.driver = driver;
  }

  @Override
  public void run() {
    while (driver.num <= driver.maxNum) {
      if (driver.isThread1) {
        synchronized (driver.MUTEX) {
          System.out.printf("name: %s, num: %s%n", Thread.currentThread().getName(), driver.num);
          driver.num++;
          driver.isThread1 = false;
        }
      }
      sleep();
    }
  }

  private void sleep() {
    try {
      /*
       * Thread.Sleep(0) : What is the normal behavior?
       * https://stackoverflow.com/questions/3257708/thread-sleep0-what-is-the-normal-behavior
       * Thread.sleep(0) does not force a thread context switch only sleep > 0 does that
       * */
      Thread.sleep(1);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}

class B implements Runnable {
  private final Driver driver;

  B(Driver driver) {
    this.driver = driver;
  }

  @Override
  public void run() {
    while (driver.num <= driver.maxNum) {
      if (!driver.isThread1) {
        synchronized (driver.MUTEX) {
          System.out.printf("name: %s, num: %s%n", Thread.currentThread().getName(), driver.num);
          driver.num++;
          driver.isThread1 = true;
        }
      }
      sleep();
    }
  }

  private void sleep() {
    try {
      Thread.sleep(1);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}

public class OddEvenPrint {

  public static void main(String[] args) {
    final Driver driver = new Driver(1, 3);
    final Thread t1 = new Thread(new A(driver));
    final Thread t2 = new Thread(new B(driver));
    t1.setName("One");
    t2.setName("Two");
    t1.start();
    t2.start();
  }
}
