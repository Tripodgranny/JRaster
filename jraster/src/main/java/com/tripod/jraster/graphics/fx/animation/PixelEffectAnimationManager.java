package com.tripod.jraster.graphics.fx.animation;

import java.util.ArrayDeque;
import com.tripod.jraster.Renderer;

public class PixelEffectAnimationManager {

  private final ArrayDeque<PixelEffectAnimation> pixelEffectAnimations = new ArrayDeque<>();

  public void addPixelEffect(PixelEffectAnimation animator) {
    pixelEffectAnimations.addLast(animator);
  }

  /**
   * Drains the queue completely, executing each effect in the exact 
   * order it was added, leaving the list pristine for the next frame cycle.
   */
  public void render(Renderer renderer, int[] pixels) {
    
    // Process and remove every effect from the queue sequentially
    while (!pixelEffectAnimations.isEmpty()) {
        PixelEffectAnimation anim = pixelEffectAnimations.poll(); // Pulls and removes the first item
        
        if (anim != null) {
            anim.execute(renderer, pixels);
        }
    }
  }

  public void clear() {
    pixelEffectAnimations.clear();
  }
  
}