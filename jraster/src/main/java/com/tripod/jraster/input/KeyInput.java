package com.tripod.jraster.input;

public class KeyInput implements InputEvent {
  private final int keyCode; // e.g., KeyEvent.VK_SPACE

  public KeyInput(int keyCode) {
      this.keyCode = keyCode;
  }

  @Override
  public boolean isPressed(KeyboardHandler kh, MouseHandler mh) {
      return kh.isKeyPressed(keyCode);
  }

  @Override
  public boolean isJustPressed(KeyboardHandler kh, MouseHandler mh) {
      return kh.isKeyJustPressed(keyCode);
  }
}