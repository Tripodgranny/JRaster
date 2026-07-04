package com.tripod.jraster.graphics.fx;

import java.util.Arrays;
import com.tripod.jraster.GameCanvas;

public class Ripple extends PixelEffect {

  // Track an internal time value that increments per frame execution
  private float time = 0.0f;
  private float waveSpeed = 0.15f; 
  private float amplitude = 2.0f;   // Maximum horizontal shift in pixels
  private float frequency = 0.2f;   // Frequency of the sine wave ripple

  public Ripple(int x, int y, int width, int height) {
    super(x, y, width, height);
  }

  @Override
  public void applyEffect(GameCanvas canvas, int[] pixels, int x, int y, int w, int h) {
    
    // 1. Snapshot the current state of the screen buffer to read from
    int[] sourcePixels = Arrays.copyOf(pixels, pixels.length);
    
    // Increment time internally so it doesn't jump based on system clock hiccups
    time += waveSpeed;

    // Calculate strict rendering boundaries
    int xStart = Math.max(0, x);
    int xEnd = Math.min(canvas.WIDTH, x + w);
    int yStart = Math.max(0, y);
    int yEnd = Math.min(canvas.HEIGHT, y + h);

    for (int yp = yStart; yp < yEnd; yp++) {
      // Calculate the horizontal offset for this specific row using sine math
      int xOffset = (int) (amplitude * Math.sin((yp * frequency) + time));
      
      int rowOffset = yp * canvas.WIDTH;
        
      for (int xp = xStart; xp < xEnd; xp++) {
        // Offset the sampling location horizontally
        int readX = xp + xOffset;
        int readY = yp; 

        int writeIdx = rowOffset + xp;
        
        // 2. Perform safe coordinate sampling against canvas boundaries
        if (readX >= 0 && readX < canvas.WIDTH && readY >= 0 && readY < canvas.HEIGHT) {
            pixels[writeIdx] = sourcePixels[readY * canvas.WIDTH + readX];
        } else {
            // Edge bleed cleanup: write a fallback hue if sampling outside bounds
            pixels[writeIdx] = 0x000000; 
        }
      }
    }
    
  }

  public float getWaveSpeed() {
    return waveSpeed;
  }

  public void setWaveSpeed(float waveSpeed) {
    this.waveSpeed = waveSpeed;
  }

  public float getAmplitude() {
    return amplitude;
  }

  public void setAmplitude(float amplitude) {
    this.amplitude = amplitude;
  }

  public float getFrequency() {
    return frequency;
  }

  public void setFrequency(float frequency) {
    this.frequency = frequency;
  }

}