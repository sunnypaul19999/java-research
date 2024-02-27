package com.ra.javaresearch.threads.exp3;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

class A implements Callable<Object> {

  @Override
  public Object call() throws Exception {
    throw new Exception("my-exception");
  }
}

public class ThrowExceptionFromCallable {
  public static void main(String[] args) {
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    Future<Object> future = executorService.submit(new A());
    try {
      future.get();
    } catch (ExecutionException e) {
      System.out.println(e.getMessage());
    } catch (InterruptedException e) {
      // not-handled
    }
  }
}
