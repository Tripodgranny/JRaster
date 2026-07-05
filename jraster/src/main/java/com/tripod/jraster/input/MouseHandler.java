package com.tripod.jraster.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class MouseHandler
    implements
      MouseListener,
      MouseMotionListener,
      MouseWheelListener {

  // Track button states (Left, Middle, Right click)
  private final boolean[] buttons = new boolean[4];
  private final boolean[] justClicked = new boolean[4];
  private final boolean[] cantClick = new boolean[4];

  // Mouse coordinates relative to the component
  private int mouseX = 0;
  private int mouseY = 0;

  // Scroll wheel movement (negative = up, positive = down)
  private int scrollRotation = 0;

  /**
   * Call this at the start of your game loop's update tick to reset
   * single-click and scroll flags.
   */
  public void update() {
    scrollRotation = 0; // Reset scroll data for this frame

    for (int i = 0; i < buttons.length; i++) {
      if (!buttons[i]) {
        cantClick[i] = false;
      } else if (justClicked[i]) {
        justClicked[i] = false;
        cantClick[i] = true;
      }
    }
  }

  // --- GETTERS ---

  public int getX() {
    return mouseX;
  }
  public int getY() {
    return mouseY;
  }
  public int getScroll() {
    return scrollRotation;
  }

  /** Checks if a button is being held down (e.g., MouseEvent.BUTTON1) */
  public boolean isMouseButtonDown(int buttonCode) {
    if (buttonCode < 1 || buttonCode >= buttons.length)
      return false;
    return buttons[buttonCode];
  }

  /** Checks if a button was just clicked this frame (e.g., firing a weapon) */
  public boolean isMouseButtonJustClicked(int buttonCode) {
    if (buttonCode < 1 || buttonCode >= buttons.length)
      return false;
    return justClicked[buttonCode];
  }

  // --- MOUSE LISTENERS ---

  @Override
  public void mousePressed(MouseEvent e) {
    int code = e.getButton();
    if (code >= 1 && code < buttons.length) {
      buttons[code] = true;
      if (!cantClick[code]) {
        justClicked[code] = true;
      }
    }
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    int code = e.getButton();
    if (code >= 1 && code < buttons.length) {
      buttons[code] = false;
      cantClick[code] = false;
      justClicked[code] = false;
    }
  }

  @Override
  public void mouseMoved(MouseEvent e) {
    mouseX = e.getX();
    mouseY = e.getY();
  }

  @Override
  public void mouseDragged(MouseEvent e) {
    // Keeps updating position even if dragging a selection box
    mouseX = e.getX();
    mouseY = e.getY();
  }

  @Override
  public void mouseWheelMoved(MouseWheelEvent e) {
    scrollRotation = e.getWheelRotation();
  }

  // Unused methods required by interfaces
  @Override
  public void mouseClicked(MouseEvent e) {
  }
  @Override
  public void mouseEntered(MouseEvent e) {
  }
  @Override
  public void mouseExited(MouseEvent e) {
  }
}