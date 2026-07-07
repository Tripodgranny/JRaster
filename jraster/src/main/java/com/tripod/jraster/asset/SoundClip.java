package com.tripod.jraster.asset;

import javax.sound.sampled.Clip;

public class SoundClip {

  private final Clip clip;

  protected SoundClip(Clip clip) {
    this.clip = clip;
  }
  
  public boolean isPlaying() {
    if (clip == null) 
      return false;
    
    return clip.isRunning();
  }

  public void play() {
    if (clip == null)
      return;

    if (clip.isRunning()) {
      clip.stop();
    }
    clip.setFramePosition(0);
    clip.start();
  }

  public void loop() {
    if (clip == null)
      return;
    clip.setFramePosition(0);
    clip.loop(Clip.LOOP_CONTINUOUSLY);
  }

  public void stop() {
    if (clip != null && clip.isRunning()) {
      clip.stop();
    }
  }

  protected void dispose() {
    if (clip != null) {
      if (clip.isOpen()) {
        clip.close();
      }
    }
  }

}