package com.ra.javaresearch.serialize.exp1;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

// class A implements Serializable {
//  // Final variable
//  private final int var;
//
//  // Constructor
//  public A(int var) {
//    this.var = var;
//  }
//
//  // Getter for var
//  public int getVar() {
//    return var;
//  }
//
//  @Override
//  public String toString() {
//    return "A{" + "var=" + var + '}';
//  }
// }

class A implements Serializable {
  private final int var = defaultVar();

  private int defaultVar() {
    return 1;
  }

  public int getVar() {
    return var;
  }

  @Override
  public String toString() {
    return "A{" + "var=" + var + '}';
  }
}

public class FinalVariableSerialization {
  public static void main(String[] args) {
    // Creating an object of class A
    A objA = new A();

    // Serialize the object
    try (ObjectOutputStream oos =
        new ObjectOutputStream(new FileOutputStream("serialized_object.ser"))) {
      oos.writeObject(objA);
      System.out.println("Object serialized successfully.");
    } catch (IOException e) {
      e.printStackTrace();
    }

    // Deserialize the object (just for demonstration)
    try (ObjectInputStream ois =
        new ObjectInputStream(new FileInputStream("serialized_object.ser"))) {
      A deserializedObj = (A) ois.readObject();
      System.out.println("Deserialized Object: var = " + deserializedObj.getVar());
    } catch (IOException | ClassNotFoundException e) {
      e.printStackTrace();
    }
  }
}
