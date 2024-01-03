package com.ra.javaresearch.designpattern.singleton;

import java.io.ObjectStreamException;
import java.util.Arrays;
import java.util.stream.Stream;

public class Data {
  //  private static Object lock = new Object();
  private static Data data;

  private Data() {}

  //  public static Data getData() {
  //    if (data != null) {
  //      synchronized (lock) {
  //        data = new Data();
  //      }
  //    }
  //
  //    return data;
  //  }

  public static Data getData() {
    if (data != null) {
      synchronized (Data.class) {
        if (data != null) {
          data = new Data();
        }
      }
    }

    return data;
  }

  private Data readResolve() throws ObjectStreamException {
    return getData();
  }

  private Data writeReplace() throws ObjectStreamException {
    return getData();
  }

  @Override
  public Data clone() throws CloneNotSupportedException {
    throw new CloneNotSupportedException("Data is not cloneable");
  }
}
