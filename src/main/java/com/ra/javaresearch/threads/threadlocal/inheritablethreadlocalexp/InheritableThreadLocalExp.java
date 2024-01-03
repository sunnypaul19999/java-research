package com.ra.javaresearch.threads.threadlocal.inheritablethreadlocalexp;

enum CarType {
  SEDAN,
  SMALL
}

class A1 {
  private static InheritableThreadLocal<String> threadLocal;

  static {
    threadLocal = new InheritableThreadLocal<>();
  }

  public String get() {
    return threadLocal.get();
  }

  public void set(String s) {
    threadLocal.set(s);
  }
}

class A2 {
  private static InheritableThreadLocal<String> threadLocal;

  A2() {
    threadLocal =
        new InheritableThreadLocal<>() {
          @Override
          public String childValue(String parentValue) {
            return parentValue + "+child-value";
          }

          //          @Override
          //          public String initialValue() {
          //            return "parent-value";
          //          }
        };
    //    System.out.println(threadLocal.get());
    //    threadLocal.set("parent-value-1");
  }

  public String get() {
    return threadLocal.get();
  }
}

public class InheritableThreadLocalExp {
  public static void main(String[] args) throws InterruptedException {
    A1 a1 = new A1();
    A2 a2 = new A2();

    Thread p1 =
        new Thread(
            () -> {
              a1.set("p1");
              Thread ch1 =
                  new Thread(
                      () -> {
                        System.out.println(a1.get());
                      });
              ch1.start();
              try {
                ch1.join();
              } catch (InterruptedException e) {
                throw new RuntimeException(e);
              }
            });

    /*
     *  initial value not provided for so null
     *  childValue method is overridden to provide childValue
     * */
    Thread p2 =
        new Thread(
            () -> {
              System.out.println(a2.get());
              Thread ch2 =
                  new Thread(
                      () -> {
                        System.out.println(a2.get());
                        Thread ch3 =
                            new Thread(
                                () -> {
                                  System.out.println(a2.get());
                                });
                        ch3.start();
                        try {
                          ch3.join();
                        } catch (InterruptedException e) {
                          throw new RuntimeException(e);
                        }
                      });
              ch2.start();
              try {
                ch2.join();
              } catch (InterruptedException e) {
                throw new RuntimeException(e);
              }
            });

    p1.start();
    p1.join();
    p2.start();
    p2.join();

    System.out.println(" -> " + a2.get());

    //    CarType car = CarType.SEDAN;
    //    String s = "bike";
    //    switch (s) {
    //      case "car" :
    //        System.out.println();
    //        break;
    //      case "bike":
    //        System.out.println();
    //        break;
    //      default:
    //        System.out.println("lkakjsfd");
    //    }
  }
}
