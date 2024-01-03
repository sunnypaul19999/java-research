package com.ra.javaresearch.interfaces;

import java.io.IOException;

interface Func {
    void func() throws Exception;
}

public class Interface implements  Func{
    
    @Override
    public void func()  throws IOException{
    
    }

  public static void main(String[] args) {
    //
  }
}
