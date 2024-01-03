package com.ra.javaresearch.lambdas;

@FunctionalInterface
interface FuncInterface {

  void func();
}

@FunctionalInterface
interface MethodRefByClassName<T> {

  // T is type of invoking object
  // value of 'o' is used to set the value 'this' assigned to ClassNameMethod func
  void func(T o);
}

@FunctionalInterface
interface MethodRefByClassNameOfClassLambda {

  // first parameter is the invoking object
  // which is used to set the value of 'this' for the assigned lambda
  void func(Lambda ob);
}

public class Lambda {

  int val;

  Lambda() {

    val = 1;
  }

  public static void main(String[] args) {
    Lambda ob = new Lambda();

    ob.objectMethodRefLambda();
    ob.classNameMethodRefLambda();
  }

  void method() {
    char c = 'a';
    ++c;

    System.out.println(
        String.format("Method Reference local variable = %c , instance variable = %d", c, val++));
  }

  public void objectMethodRefLambda() {

    FuncInterface run = () -> System.out.println("FuncInterface func");

    FuncInterface methodRef = this::method;

    methodRef.func();
    methodRef.func();
  }

  public void classNameMethodRefLambda() {

    MethodRefByClassName<Lambda> methodRef = Lambda::method;

    // observe closely to the behaviour
    // func first parameter as we know is the invoking object
    // this invoking object is the object that will provide the value for 'this' for the lambda
    // now, the invoking object that we pass in the first parameter is used to set the 'this' value
    // of the lambda
    // note: we can access value of instance variable 'val' without using 'this'
    // which is only possible if due to binding 'this'
    methodRef.func(this);

    MethodRefByClassNameOfClassLambda methodRefByClassNameLambdaOfClassLambda = Lambda::method;
  }
}

/*
output:

Method Reference local variable = b , instance variable = 1
Method Reference local variable = b , instance variable = 2
Method Reference local variable = b , instance variable = 3

Process finished with exit code 0
*/
