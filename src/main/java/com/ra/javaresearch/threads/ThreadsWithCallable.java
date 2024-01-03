package com.ra.javaresearch.threads;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class ThreadsWithCallable {
  public static void main(String[] args) throws ExecutionException, InterruptedException {
    Callable<Integer> c1 =
        () -> {
          return 1;
        };
    FutureTask<Integer> ft1 = new FutureTask<>(c1);
    Thread t1 = new Thread(ft1);
    t1.setName("running_thread_with_callable");
    t1.start();
    System.out.println(ft1.get());
  }
}
