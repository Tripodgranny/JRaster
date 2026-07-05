package com.tripod.jraster.asset;

import com.tripod.jraster.asset.audio.AudioLoader;
import com.tripod.jraster.asset.audio.SoundClip;

public class GameAssetSystem {

  private final SpriteSheetLoader spriteSheetLoader = new SpriteSheetLoader();
  private final AudioLoader audioLoader = new AudioLoader();

  public GameAssetSystem() {

  }

  public void loadSpriteSheet(String name) {
    this.spriteSheetLoader.load(name);
  }
  
  public SpriteSheet getSpriteSheet(String name) {
    return this.spriteSheetLoader.getSpriteSheet(name);
  }

  public void loadSoundClip(String name) {
    this.audioLoader.load(name);
  }
  
  public SoundClip getSound(String name) {
    return this.audioLoader.getSound(name);
  }

  public void clear() {
    this.audioLoader.clear();
  }

}
