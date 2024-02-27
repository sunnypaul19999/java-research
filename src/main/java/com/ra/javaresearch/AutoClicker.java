package com.ra.javaresearch;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;

public class AutoClicker {
  public static void main(String[] args) {
    int x = 1963; // X coordinate
    int y = 65; // Y coordinate
    int interval = 5000; // Interval in milliseconds
    int clicks = 0;
    int clickEnd = 5;
    try {
      Robot robot = new Robot();
      while (clicks < clickEnd) {
        clicks += 1;
        robot.mouseMove(x, y); // Move mouse to the specified coordinates
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK); // Simulate left mouse button press
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK); // Simulate left mouse button release
        Thread.sleep(interval); // Wait for the specified interval
      }
    } catch (AWTException | InterruptedException e) {
      e.printStackTrace();
    }
  }
}
