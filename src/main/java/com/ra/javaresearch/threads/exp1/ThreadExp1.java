package com.ra.javaresearch.threads.exp1;

class D implements Runnable {

  @Override
  public void run() throws RuntimeException {
    throw new RuntimeException();
  }
}
// When a thread enters into a static synchronized method can other thread enter into
// non-static synchronized method. ans: yes
class A {
  public static synchronized void func1() {
    try {
      Thread.sleep(10000);
      System.out.println("func1");
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  public synchronized void func2() {
    try {
      Thread.sleep(5000);
      System.out.println("func2");
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}

public class ThreadExp1 {
  public static void main(String[] args) {
    A a = new A();
    Thread t1 = new Thread(A::func1);
    Thread t2 = new Thread(a::func2);
    t1.start();
    t2.start();
  }
}
