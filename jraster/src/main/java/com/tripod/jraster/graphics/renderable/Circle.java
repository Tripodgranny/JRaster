package com.tripod.jraster.graphics.renderable;

public class Circle implements Renderable {
  private final int xc, yc, radius, color;
  private final boolean outline;

  public Circle(double xc, double yc, int radius, int color, boolean outline) {
    this.xc = (int) xc;
    this.yc = (int) yc;
    this.radius = radius;
    this.color = color;
    this.outline = outline;
  }

  private void drawPixel(int x, int y, int[] pixels, int sw, int sh) {
    if (x >= 0 && x < sw && y >= 0 && y < sh) {
      pixels[x + y * sw] = color;
    }
  }

  // Helper method to draw a clipped horizontal scanline safely
  private void drawScanline(int x1, int x2, int y, int[] pixels, int sw,
      int sh) {
    // If the entire row is off-screen vertically, skip it
    if (y < 0 || y >= sh)
      return;

    // Constrain the horizontal bounds to the screen dimensions
    int startX = Math.max(0, Math.min(x1, x2));
    int endX = Math.min(sw - 1, Math.max(x1, x2));

    int rowOffset = y * sw;
    for (int x = startX; x <= endX; x++) {
      pixels[x + rowOffset] = color;
    }
  }

  @Override
  public void execute(int[] pixels, int sw, int sh) {
    int x = 0;
    int y = radius;
    int d = 3 - 2 * radius;

    while (y >= x) {
      if (outline) {
        // Draw all 8 octants
        drawPixel(xc + x, yc + y, pixels, sw, sh);
        drawPixel(xc - x, yc + y, pixels, sw, sh);
        drawPixel(xc + x, yc - y, pixels, sw, sh);
        drawPixel(xc - x, yc - y, pixels, sw, sh);
        drawPixel(xc + y, yc + x, pixels, sw, sh);
        drawPixel(xc - y, yc + x, pixels, sw, sh);
        drawPixel(xc + y, yc - x, pixels, sw, sh);
        drawPixel(xc - y, yc - x, pixels, sw, sh);
      } else {
        // Connect the mirrored pairs with horizontal lines to fill the circle
        drawScanline(xc - x, xc + x, yc + y, pixels, sw, sh); // Lower middle
                                                              // span
        drawScanline(xc - x, xc + x, yc - y, pixels, sw, sh); // Upper middle
                                                              // span
        drawScanline(xc - y, xc + y, yc + x, pixels, sw, sh); // Lower wide span
        drawScanline(xc - y, xc + y, yc - x, pixels, sw, sh); // Upper wide span
      }

      x++;
      if (d > 0) {
        y--;
        d = d + 4 * (x - y) + 10;
      } else {
        d = d + 4 * x + 6;
      }
    }
  }
}