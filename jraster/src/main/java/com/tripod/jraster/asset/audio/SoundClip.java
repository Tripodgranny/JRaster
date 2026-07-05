package com.tripod.jraster.asset.audio;

import javax.sound.sampled.Clip;

public class SoundClip {

  private final Clip clip;

  protected SoundClip(Clip clip) {
    this.clip = clip;
  }

  /**
   * Plays the sound effect from the beginning.
   */
  public void play() {
    if (clip == null)
      return;

    if (clip.isRunning()) {
      clip.stop();
    }
    clip.setFramePosition(0);
    clip.start();
  }

  /**
   * Loops the sound continuously (perfect for background music).
   */
  public void loop() {
    if (clip == null)
      return;
    clip.setFramePosition(0);
    clip.loop(Clip.LOOP_CONTINUOUSLY);
  }

  /**
   * Stops the playing sound.
   */
  public void stop() {
    if (clip != null && clip.isRunning()) {
      clip.stop();
    }
  }

  /**
   * Clean up the OS audio line resource.
   */
  protected void dispose() {
    if (clip != null) {
      if (clip.isOpen()) {
        clip.close();
      }
    }
  }
  
}