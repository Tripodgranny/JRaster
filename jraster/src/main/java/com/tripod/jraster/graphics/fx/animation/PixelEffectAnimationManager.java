package com.tripod.jraster.graphics.fx.animation;

import java.util.ArrayDeque;
import com.tripod.jraster.GameCanvas;

public class PixelEffectAnimationManager {

  // ArrayDeque is highly optimized for fast FIFO queuing
  private final ArrayDeque<PixelEffectAnimation> pixelEffectAnimations = new ArrayDeque<>();

  /**
   * Adds an effect to be processed on the current frame.
   */
  public void addPixelEffect(PixelEffectAnimation animator) {
    pixelEffectAnimations.addLast(animator);
  }

  /**
   * Updates long-running stateful animations (if any exist).
   * If every effect is truly frame-by-frame, you can leave this empty.
   */
  public void update() {
    // Left empty if effects are pushed fresh every single frame
  }

  /**
   * Drains the queue completely, executing each effect in the exact 
   * order it was added, leaving the list pristine for the next frame cycle.
   */
  public void render(GameCanvas canvas, int[] pixels) {
    
    // Process and remove every effect from the queue sequentially
    while (!pixelEffectAnimations.isEmpty()) {
        PixelEffectAnimation anim = pixelEffectAnimations.poll(); // Pulls and removes the first item
        
        if (anim != null) {
            anim.execute(canvas, pixels);
        }
    }
  }

  public void clear() {
    pixelEffectAnimations.clear();
  }
}