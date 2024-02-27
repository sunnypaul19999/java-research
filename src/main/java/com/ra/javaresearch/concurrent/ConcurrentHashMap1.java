package com.ra.javaresearch.concurrent;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

class Modify implements Runnable {
  private ConcurrentHashMap<Integer, String> map;

  Modify(ConcurrentHashMap<Integer, String> map) {
    this.map = map;
  }

  @Override
  public void run() {
    System.out.println("started modifying");
    for (int i = 1000; i < 100000; i++) {
      Read.isModifyStarted = true;
      map.put(i, "value = " + i);
//      try {
//        Thread.sleep(100);
//      } catch (InterruptedException e) {
//        throw new RuntimeException(e);
//      }
    }
    System.out.println("ended modifying");
  }
}

class Read implements Runnable {
  private ConcurrentHashMap<Integer, String> map;
  public static boolean isModifyStarted;

  Read(ConcurrentHashMap<Integer, String> map) {
    this.map = map;
  }

  @Override
  public void run() {
    while (!isModifyStarted) {}
    System.out.println("started reading");
    Iterator<String> itr = map.values().iterator();
    while (!itr.hasNext()) {
      while (itr.hasNext()) {
        System.out.println(itr.next());
      }
    }
  }
}

public class ConcurrentHashMap1 {
  public static void main(String[] args) throws InterruptedException {
    ConcurrentHashMap<Integer, String> map = new ConcurrentHashMap<>();
    Modify modify = new Modify(map);
    Read read = new Read(map);
    Thread tModify = new Thread(modify);
    Thread tRead = new Thread(read);
    tModify.start();
    tRead.start();
  }
}
