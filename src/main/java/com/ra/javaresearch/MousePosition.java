package com.ra.javaresearch;

import java.awt.MouseInfo;

public class MousePosition {
  public static void main(String[] args) {
    while (true) {
      // Get the current mouse position
      java.awt.Point point = MouseInfo.getPointerInfo().getLocation();
      int x = (int) point.getX();
      int y = (int) point.getY();

      // Print the mouse position
      System.out.println("Mouse position: (" + x + ", " + y + ")");

      // Add a small delay before printing again (optional)
      try {
        Thread.sleep(1000); // Adjust delay as needed
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
