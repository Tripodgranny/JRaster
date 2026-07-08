package com.tripod.jraster.graphics.fx;

import com.tripod.jraster.Renderer;

public abstract class PixelEffect {
  
  private int x, y, width, height;
  
  public PixelEffect(int x, int y, int width, int height) {
    super();
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
  }

  public abstract void applyEffect(Renderer renderer, int[] pixels, int x, int y, int w, int h);

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

  public void setX(int x) {
    this.x = x;
  }

  public void setY(int y) {
    this.y = y;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  public void setHeight(int height) {
    this.height = height;
  }
  
  public void setPositionAndScale(double x, double y, double w, double h) {
    this.x = (int) x;
    this.y = (int) y;
    this.width = (int) w;
    this.height = (int) h;
  }
}
