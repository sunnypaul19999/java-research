package com.ra.javaresearch.threads;

class Driver {
  private volatile int i;
  private final int n;
  private volatile boolean printThread;

  public Driver(int n) {
    this.n = n;
    printThread = true;
  }

  /*
   * synchronized keyword is important here because
   * - printThread value alteration (true -> false or false -> true),
   * - assigning new value to printThread and
   * - printing the value
   * is one atomic operation
   * */
  public synchronized void print() {
    printThread = !printThread;
    System.out.println(Thread.currentThread().getName() + ": " + i);
  }

  public int getI() {
    return i;
  }

  public void setI(int i) {
    this.i = i;
  }

  public int getN() {
    return n;
  }

  /*
   * isPrintThread() is the one that coordinates whose turn is to print
   * we perform manipulative operations inside after checking isPrintThread()
   * */
  public boolean isPrintThread() {
    return printThread;
  }
}

class AThread implements Runnable {
  private Driver d;

  public AThread(Driver d) {
    this.d = d;
  }

  private void sleep() {
    try {
      Thread.sleep(1);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void run() {
    while (d.getI() < d.getN()) {
      // on true
      if (d.isPrintThread()) {
        d.setI(d.getI() + 1);
        d.print();
      } else {
        sleep();
      }
    }
  }
}

class BThread implements Runnable {
  private Driver d;

  public BThread(Driver d) {
    this.d = d;
  }

  private void sleep() {
    try {
      Thread.sleep(1);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void run() {
    while (d.getI() < d.getN()) {
      // on false
      if (!d.isPrintThread()) {
        d.setI(d.getI() + 1);
        d.print();
      } else {
        sleep();
      }
    }
  }
}

public class SyncPrintNumbers {
  public static void main(String[] args) {
    Driver d = new Driver(100);
    Thread a = new Thread(new AThread(d));
    Thread b = new Thread(new BThread(d));
    a.setName("Thread1");
    b.setName("Thread2");
    a.start();
    b.start();
  }
}
