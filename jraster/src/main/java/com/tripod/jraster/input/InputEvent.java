package com.tripod.jraster.input;

public interface InputEvent {
  // Passes the handlers down to let the event check its own state
  boolean isPressed(KeyboardHandler kh, MouseHandler mh);
  boolean isJustPressed(KeyboardHandler kh, MouseHandler mh);
}