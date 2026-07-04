package com.tripod.jraster.graphics.renderable;

public interface Renderable {
  
  default int getDepth() {
    return 0;
  }
  
  void execute(int[] pixels, int screenWidth, int screenHeight);
  
}
