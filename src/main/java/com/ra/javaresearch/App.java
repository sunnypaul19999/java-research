package com.ra.javaresearch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/** Hello world! */
public class App {

  public void func(Integer i) {}

  public void func(String s) {}

  public static void main(String[] args) {
    List<Integer> list = new ArrayList<>();
    Collections.addAll(list, 1, 2, 3, 4, 5);
    int[] listToArray = list.stream().filter(i -> i % 2 != 0).mapToInt(i -> i).toArray();
    System.out.println(Arrays.toString(listToArray));

    String iFiltered =
        Arrays.toString(
            "india is my country".chars().filter(c -> c != 'i').mapToObj(c -> (char) c).toArray());
    System.out.println(iFiltered);

    long iStrCount = "india is my country".chars().filter(c -> c == 'i').count();
    System.out.println(iStrCount);

    //    new App().func(null);

    int[] intArr =
        Arrays.stream("  10 11 13 12 14 ".trim().split(" ")).mapToInt(Integer::valueOf).toArray();
    System.out.println(Arrays.toString(intArr));

    String s1 = new String("hello world!").intern();
    String s2 = new String("hello world!").intern();
    System.out.println(s1 == s2);

    String[] checkEmptyUsingLambdas =
        Arrays.stream(new String[] {"hello", "world! ", " "})
            .filter(str -> !str.trim().isEmpty())
            .toArray(String[]::new);

    System.out.println(Arrays.toString(checkEmptyUsingLambdas));

    String s =
        "india is my country"
            .chars()
            .filter(i -> i != 'i')
            .mapToObj(i -> String.valueOf((char) i))
            .reduce("", (p, n) -> p + n);
    System.out.println(s.chars().count());

    int[] arr =
        new ArrayList<Integer>()
            .stream()
                .map(Double::valueOf)
                .filter(i -> i % 2 == 0)
                .mapToInt(Double::intValue)
                .toArray();
    //
    //    new ArrayList<>().stream().collect(Collectors.toSet());

    StringBuffer stringBuffer = new StringBuffer("hello");
    stringBuffer.append(" world");
    stringBuffer.deleteCharAt(0);
    System.out.println(stringBuffer.toString());

    Integer[] arrSort = new Integer[] {3, 2, 1};
    Arrays.sort(arrSort, Integer::compare);
    System.out.println(Arrays.toString(arrSort));
    //    ThreadPoolExecutor
  }
}
