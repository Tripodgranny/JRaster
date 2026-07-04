package com.tripod.jraster.graphics.fx.animation;

import com.tripod.jraster.GameCanvas;
import com.tripod.jraster.graphics.fx.PixelEffect;

public class NoPixelEffectAnimation extends PixelEffectAnimation {

  public NoPixelEffectAnimation(PixelEffect pixelEffect) {
    super(pixelEffect, 0);
  }

  @Override
  public void start() {

  }

  @Override
  public void execute(GameCanvas canvas, int[] pixels) {

    pixelEffect.applyEffect(canvas, pixels, this.pixelEffect.getX(),
        this.pixelEffect.getY(), this.pixelEffect.getWidth(),
        this.pixelEffect.getHeight());

  }

  @Override
  public boolean isComplete() {
    return false;
  }

}