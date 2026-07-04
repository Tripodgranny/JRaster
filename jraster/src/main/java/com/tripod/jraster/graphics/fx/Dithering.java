package com.tripod.jraster.graphics.fx;

import com.tripod.jraster.GameCanvas;

public class Dithering extends PixelEffect {
  
  private int colorDepth = 0;

  public Dithering(int x, int y, int width, int height, int colorDepth) {
    super(x, y, width, height);
    this.colorDepth = colorDepth;
  }

  @Override
  public void applyEffect(GameCanvas canvas, int[] pixels, int x, int y, int w,
      int h) {
    applyDithering(canvas, pixels, colorDepth, x, y, w, h);
  }

  public void applyDithering(GameCanvas canvas, int[] pixels, int colorDepth,
      int x, int y, int width, int height) {

    // 4x4 Bayer Matrix scaled up to 0-255 range to avoid floating-point math
    int[] bayerMatrix = {0, 127, 31, 159, 191, 63, 223, 95, 47, 175, 15, 143,
        239, 111, 255, 79};

    // Calculate how large each color "step" is based on desired depth
    int step = 255 / (colorDepth - 1);

    // Calculate rendering bounds safely to prevent clipping issues
    int xStart = Math.max(0, x);
    int xEnd = Math.min(canvas.WIDTH, x + width);
    int yStart = Math.max(0, y);
    int yEnd = Math.min(canvas.HEIGHT, y + height);

    // Use yp and xp consistently throughout the loops
    for (int yp = yStart; yp < yEnd; yp++) {
      // CRITICAL: Row offset must use the full canvas width, not the sub-patch
      // width
      int rowOffset = yp * canvas.WIDTH;
      int matrixRow = (yp & 3) << 2;

      for (int xp = xStart; xp < xEnd; xp++) {
        int pixelIdx = rowOffset + xp;
        int rgb = pixels[pixelIdx];

        // 1. Extract color channels
        int r = (rgb >> 16) & 0xFF;
        int g = (rgb >> 8) & 0xFF;
        int b = rgb & 0xFF;

        // 2. Fetch threshold from Bayer matrix using local screen coordinates
        int threshold = bayerMatrix[matrixRow + (xp & 3)];

        // 3. Apply dither and quantize per channel
        r = quantizeChannel(r, threshold, step);
        g = quantizeChannel(g, threshold, step);
        b = quantizeChannel(b, threshold, step);

        // 4. Pack colors back into the raw pixel buffer
        pixels[pixelIdx] = (r << 16) | (g << 8) | b;
      }
    }
  }

  private int quantizeChannel(int color, int threshold, int step) {
    // Find where the color currently falls between palette steps
    int bucket = color / step;
    int error = color % step;

    // Normalize error to a 0-255 scale to match the Bayer matrix
    int normalizedError = (error * 255) / step;

    // If the error is higher than the layout grid threshold, push it to the
    // next color step
    if (normalizedError > threshold && bucket < 255 / step) {
      bucket++;
    }

    // Return the absolute color value capped at 255
    int finalColor = bucket * step;
    return finalColor > 255 ? 255 : finalColor;
  }
  
}
