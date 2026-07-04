package com.tripod.jraster.graphics.fx;

import com.tripod.jraster.GameCanvas;

public class PalletSwap extends PixelEffect {

  public PalletSwap(int x, int y, int width, int height) {
    super(x, y, width, height);
  }

  @Override
  public void applyEffect(GameCanvas canvas, int[] pixels, int x, int y, int w,
      int h) {

    int[] gameboyPalette = {0x0F380F, // Darkest Green
        0x306230, // Dark Green
        0x8BAC0F, // Light Green
        0x9BBC0F // Lightest Green
    };
    int paletteSize = gameboyPalette.length;

    int xStart = Math.max(0, x);
    int xEnd = Math.min(canvas.WIDTH, x + w);
    int yStart = Math.max(0, y);
    int yEnd = Math.min(canvas.HEIGHT, y + h);

    // Loop through the specific 2D bounding box
    for (int yp = yStart; yp < yEnd; yp++) {
      int rowOffset = yp * canvas.WIDTH;

      for (int xp = xStart; xp < xEnd; xp++) {
        int pixelIdx = rowOffset + xp;
        int rgb = pixels[pixelIdx];

        int r = (rgb >> 16) & 0xFF;
        int g = (rgb >> 8) & 0xFF;
        int b = rgb & 0xFF;
        int average = ((r + g + b) * 85) >> 8;

        int index = (average * paletteSize) >> 8;
        index = Math.max(0, Math.min(paletteSize - 1, index));

        pixels[pixelIdx] = gameboyPalette[index];
      }
    }
  }
}