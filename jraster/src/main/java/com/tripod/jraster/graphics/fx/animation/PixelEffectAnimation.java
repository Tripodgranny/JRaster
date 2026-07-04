package com.tripod.jraster.graphics.fx.animation;

import com.tripod.jraster.GameCanvas;
import com.tripod.jraster.graphics.fx.PixelEffect;

public abstract class PixelEffectAnimation {

  protected PixelEffect pixelEffect;
  protected double animationSpeed;
  
  protected boolean loop = false;

  public PixelEffectAnimation(PixelEffect pixelEffect, double animationSpeed) {
    super();
    this.pixelEffect = pixelEffect;
    this.animationSpeed = animationSpeed;
  }
  
  public boolean getLooping() {
    return this.loop;
  }
  
  public void setLoop(boolean loop) {
    this.loop = loop;
  }
  
  public boolean getIsCompleted() {
    return isComplete();
  }

  public abstract void start();

  public abstract void execute(GameCanvas canvas, int[] pixels);

  public abstract boolean isComplete();

}
