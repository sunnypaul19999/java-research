package com.ra.javaresearch;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

public class EnterKeyPressProgram {
  public static void main(String[] args) {
    while (true) {
      try {
        // Create a new Robot instance
        Robot robot = new Robot();

        // Wait for 1 second (1000 milliseconds)
        Thread.sleep(1000);

        // Simulate pressing the Enter key
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);

      } catch (AWTException | InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}










