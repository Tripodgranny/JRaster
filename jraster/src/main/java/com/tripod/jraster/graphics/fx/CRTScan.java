package com.tripod.jraster.graphics.fx;

import com.tripod.jraster.GameCanvas;

public class CRTScan extends PixelEffect {

  public CRTScan(int x, int y, int width, int height) {
    super(x, y, width, height);
  }

  @Override
  public void applyEffect(GameCanvas canvas, int[] pixels, int x, int y,
      int width, int height) {

    float scanlineStrength = 0.5f;

    int xStart = Math.max(0, x);
    int xEnd = Math.min(canvas.WIDTH, x + width);
    int yStart = Math.max(0, y);
    int yEnd = Math.min(canvas.HEIGHT, y + height);

    for (int yp = yStart; yp < yEnd; yp++) {
      if (yp % 2 == 0)
        continue;

      int rowOffset = yp * canvas.WIDTH;

      for (int xp = xStart; xp < xEnd; xp++) {
        int pixelIdx = rowOffset + xp;
        int rgb = pixels[pixelIdx];

        int r = (int) ((rgb >> 16 & 0xFF) * scanlineStrength);
        int g = (int) ((rgb >> 8 & 0xFF) * scanlineStrength);
        int b = (int) ((rgb & 0xFF) * scanlineStrength);

        pixels[pixelIdx] = (r << 16) | (g << 8) | b;
      }
    }
  }
}