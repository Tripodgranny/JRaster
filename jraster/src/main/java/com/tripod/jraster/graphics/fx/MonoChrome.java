package com.tripod.jraster.graphics.fx;

import com.tripod.jraster.GameCanvas;

public class MonoChrome extends PixelEffect {
  
  private static final int MIN_THRESHOLD = 32;
  private static final int MAX_THRESHOLD = 128;
  private static final int DEFAULT_THRESHOLD = 64;
  
  private int threshold = DEFAULT_THRESHOLD;

  public MonoChrome(int x, int y, int width, int height) {
    super(x, y, width, height);
  }

  @Override
  public void applyEffect(GameCanvas canvas, int[] pixels, int x, int y, int w,
      int h) {

    int xStart = Math.max(0, x);
    int xEnd = Math.min(canvas.WIDTH, x + w);
    int yStart = Math.max(0, y);
    int yEnd = Math.min(canvas.HEIGHT, y + h);

    for (int yp = yStart; yp < yEnd; yp++) {
      int rowOffset = yp * canvas.WIDTH;

      for (int xp = xStart; xp < xEnd; xp++) {
        int pixelIdx = rowOffset + xp;
        int rgb = pixels[pixelIdx];

        int r = (rgb >> 16) & 0xFF;
        int g = (rgb >> 8) & 0xFF;
        int b = rgb & 0xFF;
        int average = ((r + g + b) * 85) >> 8;

        // Apply binary decision
        if (average < this.threshold) {
          pixels[pixelIdx] = 0x000000; // Pure Black
        } else {
          pixels[pixelIdx] = 0xFFFFFF; // Pure White
        }
      }
    }
    
  }
  
  public void setThreshold(int threshold) {
    if (threshold > MAX_THRESHOLD) threshold = MAX_THRESHOLD;
    if (threshold < MIN_THRESHOLD) threshold = MIN_THRESHOLD;
    
    this.threshold = threshold;
  }
  
  public int getThreshold() {
    return this.threshold;
  }
  
}