package com.ra.javaresearch.threads.exp4;

import java.util.concurrent.Exchanger;

class A implements Runnable {
  final Exchanger<String> exchanger;

  A(Exchanger<String> exchanger) {
    this.exchanger = exchanger;
  }

  @Override
  public void run() {
    try {
      System.out.println(
          Thread.currentThread().getName() + " " + exchanger.exchange("thread-a-data"));
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}

class B implements Runnable {
  final Exchanger<String> exchanger;

  B(Exchanger<String> exchanger) {
    this.exchanger = exchanger;
  }

  @Override
  public void run() {
    try {
      System.out.println(
          Thread.currentThread().getName() + " " + exchanger.exchange(null));
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}

public class ThreadExchanger {
  public static void main(String[] args) {
    Exchanger<String> exchanger = new Exchanger<>();
    A a = new A(exchanger);
    B b = new B(exchanger);
    Thread one = new Thread(a);
    Thread two = new Thread(b);
    one.setName("thread-one");
    two.setName("thread-two");
    one.start();
    two.start();
  }
}
