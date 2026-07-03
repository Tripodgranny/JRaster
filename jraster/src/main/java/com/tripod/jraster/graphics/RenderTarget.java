package com.tripod.jraster.graphics;

public class RenderTarget {
  
  public final String id;
  public double x, y;
  public boolean isVisible = true;
  public int width, height;
  private final int[] pixels;

  public RenderTarget(String id, int x, int y, int w, int h) {
    
    this.id = id;
    this.x = x;
    this.y = y;
    this.width = w;
    this.height = h;
    this.pixels = new int[width * height];
    
  }

  public void clear(int color) {
    for (int i = 0; i < pixels.length; i++) {
      pixels[i] = color;
    }
  }

  public void setIsVisible(boolean isVisible) {
    this.isVisible = isVisible;
  }

  public boolean getIsVisibile() {
    return isVisible;
  }

  public String getId() {
    return id;
  }

  /**
   * Blits another render target onto this one. Supports transparency (skipping
   * a specific alpha/color key).
   */
  public void drawTarget() {

    if (!isVisible)
      return;

    for (int yp = 0; yp < this.height; yp++) {
      int tY = (int) (yp + y);
      if (tY < 0 || tY >= this.height)
        continue; // Y-clipping

      for (int xp = 0; xp < this.width; xp++) {
        int tX = (int) (xp + x);
        if (tX < 0 || tX >= this.width)
          continue; // X-clipping

        int srcPixel = this.pixels[xp + yp * this.width];

        // Assuming 0x000000 or a specific pink/magenta is transparent
        // Or check the alpha channel if you implement ARGB color spaces
        if ((srcPixel & 0xFF000000) == 0)
          continue;

        this.pixels[tX + tY * this.width] = srcPixel;
      }
    }
  }
}