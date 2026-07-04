package com.tripod.jraster.graphics.fx;

import com.tripod.jraster.GameCanvas;

public abstract class PixelEffect {
  
  private int x, y, width, height;
  
  public PixelEffect(int x, int y, int width, int height) {
    super();
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
  }

  public abstract void applyEffect(GameCanvas canvas, int[] pixels, int x, int y, int w, int h);

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

}
