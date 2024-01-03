package com.ra.javaresearch.experiment;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public class ArrayDequeExp {
  public static void main(String[] args) {
    final ArrayDeque<Integer> stack = new ArrayDeque<>();
    List<?> s = new ArrayList<>();
    stack.offer(1);
    stack.offer(2);
    stack.push(3);
    
    System.out.println(stack);
  }
}
