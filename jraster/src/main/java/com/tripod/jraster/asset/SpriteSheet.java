package com.tripod.jraster.asset;

import java.awt.image.BufferedImage;

public class SpriteSheet {

  private int width, height;
  private BufferedImage image;
  private int[] pixels;
  
  protected SpriteSheet(int width, int height, int[] pixels) {
    this.width = width;
    this.height = height;
    this.pixels = pixels;
  }
  
  public int getWidth() {
    return width;
  }
  
  public int getHeight() {
    return height;
  }
  
  public BufferedImage getImage() {
    return image;
  }
  
  public int[] getPixels() {
    return pixels;
  }

}
