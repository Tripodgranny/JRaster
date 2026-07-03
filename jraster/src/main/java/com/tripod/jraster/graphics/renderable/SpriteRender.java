package com.tripod.jraster.graphics.renderable;

import com.tripod.jraster.entity.SpriteAnimator;

public class SpriteRender implements Renderable {
    private final double x;
    private final double y;
    private final SpriteAnimator animator;

    public SpriteRender(double x, double y, SpriteAnimator animator) {
        this.x = x;
        this.y = y;
        this.animator = animator;
    }

    @Override
    public void execute(int[] screenPixels, int screenWidth, int screenHeight) {
        int[] spritePixels = animator.getCurrentPixels();
        int spriteWidth = animator.getWidth();
        int spriteHeight = animator.getHeight();

        int startX = (int) this.x;
        int startY = (int) this.y;

        // Software rasterization loop for the sprite
        for (int sy = 0; sy < spriteHeight; sy++) {
            int pixelY = startY + sy;
            if (pixelY < 0 || pixelY >= screenHeight) continue; // Y-clipping

            for (int sx = 0; sx < spriteWidth; sx++) {
                int pixelX = startX + sx;
                if (pixelX < 0 || pixelX >= screenWidth) continue; // X-clipping

                int color = spritePixels[sx + sy * spriteWidth];
                
                // Assuming 0xFFFF00FF (Pink) or 0x00000000 is your transparency mask
                if (color == 0xFFFF00FF || (color >>> 24) == 0) continue; 

                screenPixels[pixelX + pixelY * screenWidth] = color;
            }
        }
    }
}