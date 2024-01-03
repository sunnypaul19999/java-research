//package com.ra.javaresearch.threads;
//
//import java.util.concurrent.Executors;
//
//class A {
//
//  synchronized void syncMethod() {
//
//    System.out.println(
//        String.format("%s in method:taskInSync of Task", Thread.currentThread().getName()));
//
//    try {
//      Thread.sleep(10000);
//    } catch (InterruptedException e) {
//      throw new RuntimeException(e);
//    }
//  }
//
//  void noSync() {
//    this.eq
//    System.out.println(
//        String.format("%s in method:noSync of Task", Thread.currentThread().getName()));
//  }
//}
//
//class Task {
//
//  final A obA;
//
//  Task() {
//
//    obA = new A();
//  }
//
//  void syncObject(A a) {
//
//    // if object's 'a' monitor is locked by some other thread we cannot access this synchronized
//    // block
//    synchronized (a) {
//      System.out.println(
//          String.format("%s in method:syncBlock of Task", Thread.currentThread().getName()));
//
//      try {
//        a.notifyAll();
//        Thread.sleep(5000);
//      } catch (InterruptedException e) {
//        throw new RuntimeException(e);
//      }
//    }
//  }
//}
//
//public class SyncThread {
//
//  public static void main(String[] args) {
//
//    Task task = new Task();
//
//    A a = new A();
//
//    A b = new A();
//
//    Thread t3 =
//        new Thread(
//            () -> {
//
//              // Thread3 acquires lock on object 'a'
//              // now no other thread can enter in object 'a' synchronized method
//              // or synchronized block where object 'a' reference is used
//              a.syncMethod();
//            });
//    t3.setName("Thread3");
//
//    Thread t2 =
//        new Thread(
//            () -> {
//              a.noSync();
//
//              System.out.println("b trying to access sync method of A");
//              // remember object 'a' is monitor i locked not b's
//              b.syncMethod();
//
//              // while object 'a' monitor is locked by Thread3
//              // Thread2 trying to enter synchronized method 'syncMethod' of object 'a'
//              // or synchronized block with object 'a' reference used then Thread2 goes
//              // into waiting state until Thread3 exits
//              task.syncObject(a);
//            });
//    t2.setName("Thread2");
//
//    t3.start();
//
//    t2.start();
//  }
//}
