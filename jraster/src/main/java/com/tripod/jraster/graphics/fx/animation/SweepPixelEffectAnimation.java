package com.tripod.jraster.graphics.fx.animation;

import com.tripod.jraster.Renderer;
import com.tripod.jraster.graphics.fx.PixelEffect;

public class SweepPixelEffectAnimation extends PixelEffectAnimation {
  
  protected int xSweep = 0;
  protected int ySweep = 0;
  
  private boolean sweepHorizontal = true;
  private boolean sweepVertical = false;
  
  public SweepPixelEffectAnimation(PixelEffect pixelEffect, double animationSpeed) {
    super(pixelEffect, animationSpeed);
  }
  
  @Override
  public void start() {
    
  }

  @Override
  public void execute(Renderer renderer, int[] pixels) {
    
    if (!this.isComplete()) {
      if (sweepHorizontal) {
        xSweep += this.animationSpeed;
      } else {
        xSweep = this.pixelEffect.getWidth();
      }
      
      if (sweepVertical) {
        ySweep += this.animationSpeed;
      } else {
        ySweep += this.pixelEffect.getHeight();
      }
    }
    
    pixelEffect.applyEffect(renderer, pixels, this.pixelEffect.getX(),
        this.pixelEffect.getY(), xSweep, ySweep);
    
  }
  @Override
  public boolean isComplete() {
    return xSweep >= pixelEffect.getWidth() && ySweep >= pixelEffect.getHeight();
  }

  public boolean isSweepHorizontal() {
    return sweepHorizontal;
  }

  public void setSweepHorizontal(boolean sweepHorizontal) {
    this.sweepHorizontal = sweepHorizontal;
  }

  public boolean isSweepVertical() {
    return sweepVertical;
  }

  public void setSweepVertical(boolean sweepVertical) {
    this.sweepVertical = sweepVertical;
  }
  
  

}
