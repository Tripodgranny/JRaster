package com.tripod.jraster.graphics.fx;

import com.tripod.jraster.Renderer;

public class RadialBlur extends PixelEffect {

    private final float blurStrength = 0.02F; 
    private final int samples = 3;  
    
    public RadialBlur(int x, int y, int width, int height) {
      super(x, y, width, height);
    }

    public void applyEffect(Renderer renderer, int[] pixels, int x, int y, int w, int h) {
        // Calculate safe rendering boundaries clamped to the actual canvas dimensions
        int xStart = Math.max(0, x);
        int xEnd = Math.min(renderer.WIDTH, x + w);
        int yStart = Math.max(0, y);
        int yEnd = Math.min(renderer.HEIGHT, y + h);

        // Center of the radial blur is the midpoint of the sub-patch
        float centerX = x + (w / 2.0f);
        float centerY = y + (h / 2.0f);

        // Loop through the defined sub-patch region
        for (int yp = yStart; yp < yEnd; yp++) {
            int rowOffset = yp * renderer.WIDTH; // Step using full canvas width stride
            float dirY = yp - centerY;

            for (int xp = xStart; xp < xEnd; xp++) {
                float dirX = xp - centerX;

                int sumR = 0;
                int sumG = 0;
                int sumB = 0;

                // Sample along the vector radiating outward from the focal point
                for (int s = 0; s < samples; s++) {
                    float scale = 1.0f - (blurStrength * s);
                    
                    int sampleX = (int) (centerX + dirX * scale);
                    int sampleY = (int) (centerY + dirY * scale);

                    // Clamp sample points cleanly to the boundaries of the canvas
                    if (sampleX < 0) sampleX = 0;
                    if (sampleX >= renderer.WIDTH) sampleX = renderer.WIDTH - 1;
                    if (sampleY < 0) sampleY = 0;
                    if (sampleY >= renderer.HEIGHT) sampleY = renderer.HEIGHT - 1;

                    // Read sample pixel using canvas stride logic
                    int sampleColor = pixels[sampleY * renderer.WIDTH + sampleX];
                    
                    sumR += (sampleColor >> 16) & 0xFF;
                    sumG += (sampleColor >> 8) & 0xFF;
                    sumB += sampleColor & 0xFF;
                }

                int r = sumR / samples;
                int g = sumG / samples;
                int b = sumB / samples;

                // Write back to the active screen index
                pixels[rowOffset + xp] = (r << 16) | (g << 8) | b;
            }
        }
    }
}