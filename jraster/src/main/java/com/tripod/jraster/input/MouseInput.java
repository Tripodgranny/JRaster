package com.tripod.jraster.input;

public class MouseInput implements InputEvent {
  private final int buttonCode; // e.g., MouseEvent.BUTTON1

  public MouseInput(int buttonCode) {
      this.buttonCode = buttonCode;
  }

  @Override
  public boolean isPressed(KeyboardHandler kh, MouseHandler mh) {
      return mh.isMouseButtonDown(buttonCode);
  }

  @Override
  public boolean isJustPressed(KeyboardHandler kh, MouseHandler mh) {
      return mh.isMouseButtonJustClicked(buttonCode);
  }
}
