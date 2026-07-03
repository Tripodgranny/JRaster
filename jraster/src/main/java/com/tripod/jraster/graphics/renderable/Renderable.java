package com.tripod.jraster.graphics.renderable;

public interface Renderable {
  void execute(int[] pixels, int screenWidth, int screenHeight);
}
