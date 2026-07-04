package com.tripod.jraster.entity;

import com.tripod.jraster.asset.Sprite;

public class SpriteAnimator implements EntityComponent {
  
  private Sprite sprite;
  private int frameIndex = 0;
  private int frameCount = 0;
  private int tickCounter = 0;
  private int animationSpeed;
  private boolean isPlaying = true;

  public SpriteAnimator(Sprite sprite, int animationSpeed) {
    this.frameCount = sprite.getFrameCount();
    this.sprite = sprite;
    this.animationSpeed = animationSpeed;
  }

  public void update() {
    if (!isPlaying || animationSpeed <= 0)
      return;

    tickCounter++;
    if (tickCounter >= animationSpeed) {
      tickCounter = 0;
      frameIndex++;
      // Automatically loop using your Sprite's bounds checking
      if (frameIndex >= sprite.getFrameCount()) {
        frameIndex = 0;
      }
    }
  }

  // Returns the exact pixel array for the current active frame
  public int[] getCurrentPixels() {
    return sprite.getPixels(frameIndex);
  }
  
  /* GETTERS */
  public Sprite getSprite() { return this.sprite; }
  public int getWidth() { return this.sprite.getWidth(); }
  public int getHeight() { return this.sprite.getHeight(); }
  public int getCurrentFrame() { return this.frameIndex; }
  public int getFrameCount() { return this.frameCount; }
  public int getAnimationSpeed() { return this.animationSpeed; }
  public boolean isPlaying() { return this.isPlaying; }
  
  /* SETTERS */
  public void setSprite(Sprite sprite) { this.sprite = sprite; }
  public void setCurrentFrame(int frameIndex) { this.frameIndex = frameIndex; }
  public void setAnimationSpeed(int speed) { this.animationSpeed = speed; }
  
  public void pause() { this.isPlaying = false; }
  public void play() { this.isPlaying = true; }

}