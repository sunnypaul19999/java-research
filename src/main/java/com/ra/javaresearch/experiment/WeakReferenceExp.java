package com.ra.javaresearch.experiment;

import java.lang.ref.WeakReference;

public class WeakReferenceExp {
  public static void main(String[] args) {
    Object ob = new Object();
    WeakReference<Object> w = new WeakReference<>(ob);
    System.gc();
    System.out.println(w.get());
    ob = null;
    System.gc();
    System.out.println(w.get());
  }
}
