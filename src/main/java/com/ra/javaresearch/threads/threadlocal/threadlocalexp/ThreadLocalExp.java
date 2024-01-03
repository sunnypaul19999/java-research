package com.ra.javaresearch.threads.threadlocal.threadlocalexp;

class A1 {
  private static ThreadLocal<Integer> threadLocal;

  A1() {
    threadLocal = new ThreadLocal<>();
  }

  public Integer get() {
    return threadLocal.get();
  }
}

class A2 {
  private static ThreadLocal<Integer> threadLocal;

  A2() {
    //    threadLocal =
    //        new ThreadLocal<>() {
    //          @Override
    //          public Integer initialValue() {
    //            return 1;
    //          }
    //        };
    threadLocal = ThreadLocal.withInitial(() -> 1);
  }

  public Integer get() {
    return threadLocal.get();
  }
}

public class ThreadLocalExp {
  public static void main(String[] args) {
    A1 a1 = new A1();
    // null
    System.out.println(a1.get());

    A2 a2 = new A2();
    // 1
    System.out.println(a2.get());

    new Thread(
            () -> {
              System.out.println(new A2().get());
            })
        .start();
  }
}
