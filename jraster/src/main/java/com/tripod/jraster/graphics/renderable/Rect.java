package com.tripod.jraster.graphics.renderable;

public class Rect implements Renderable {

  public double x, y, w, h;
  int c;
  public boolean outline;

  public Rect(double x, double y, double w, double h, int c, boolean outline) {
    super();
    this.x = x;
    this.y = y;
    this.w = w;
    this.h = h;
    this.c = c;
    this.outline = outline;
  }

  @Override
  public void execute(int[] pixels, int sw, int sh) {

    if (outline) {

      // Top and Bottom edges
      for (int xp = (int) x; xp < x + w; xp++) {
        if (xp >= 0 && xp < sw) {
          if (y >= 0 && y < sh)
            pixels[(int) (xp + y * sw)] = c;
          if (y + h - 1 >= 0 && y + h - 1 < sh)
            pixels[(int) (xp + (y + h - 1) * sw)] = c;
        }
      }

      // Left and Right edges
      for (int yp = (int) y; yp < y + h; yp++) {
        if (yp >= 0 && yp < sh) {
          if (x >= 0 && x < sw)
            pixels[(int) (x + yp * sw)] = c;
          if (x + w - 1 >= 0 && x + w - 1 < sw)
            pixels[(int) ((x + w - 1) + yp * sw)] = c;
        }
      }

    } else {

      // FILLED RECTANGLE FIXED LOGIC:

      // 1. Calculate boundaries to prevent pointless loop iterations
      // and handle off-screen clipping cleanly.
      double xStart = Math.max(0, x);
      double xEnd = Math.min(sw, x + w);
      double yStart = Math.max(0, y);
      double yEnd = Math.min(sh, y + h);

      for (int yp = (int) yStart; yp < yEnd; yp++) {
        int rowOffset = yp * sw; // Precompute the Y offset line for
                                 // optimization

        for (int xp = (int) xStart; xp < xEnd; xp++) {
          // Correctly map 2D coordinates into the 1D screen array
          pixels[xp + rowOffset] = c;
        }
      }

    }
  }

}
