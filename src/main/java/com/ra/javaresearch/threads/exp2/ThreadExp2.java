package com.ra.javaresearch.threads.exp2;

import java.io.IOException;
import java.lang.Thread.UncaughtExceptionHandler;

/*
 * to test exceptions being thrown from run methods and how to handle them
 * */

interface ANew {
  public A aNew();
}

class IOExceptionHandler implements UncaughtExceptionHandler {

  @Override
  public void uncaughtException(Thread t, Throwable e) {
    if (e.getClass().equals(IOException.class)) {
      System.out.println("Caught IOException!");
    } else {
      System.out.println("exception not handled " + e);
    }
  }
}

class A {

  // run method should not throw checked exceptions
  public void runThrowException() throws IOException {}

  // if runtime exceptions remain unhandled then will cause thread to die (TERMINATED)
  public void runThrowRuntimeException() throws RuntimeException {
    throw new RuntimeException();
  }

  // passing exception to uncaught exception handler
  public void runThrowRuntimeExceptionUncaughtHandle() {
    try {
      throw new RuntimeException();
    } catch (RuntimeException e) {
      final Thread t = Thread.currentThread();
      t.getUncaughtExceptionHandler().uncaughtException(t, e);
    }
  }

  // passing exception to uncaught exception handler
  public void runThrowExceptionUncaughtHandle() {
    try {
      throw new IOException();
    } catch (IOException e) {
      final Thread t = Thread.currentThread();
      t.getUncaughtExceptionHandler().uncaughtException(t, e);
    }
  }
}

public class ThreadExp2 {
  public static void main(String[] args) throws InterruptedException {
    ANew aNew = A::new;

    final A a = aNew.aNew();
    final Thread t1 = new Thread(a::runThrowRuntimeException);
    final Thread t3 = new Thread(a::runThrowExceptionUncaughtHandle);

    //    t1.setUncaughtExceptionHandler(new IOExceptionHandler());
    t3.setUncaughtExceptionHandler(new IOExceptionHandler());

    t1.start();
    t3.start();

    t1.join();
    t3.join();

    System.out.println(t1.getState());
  }
}
