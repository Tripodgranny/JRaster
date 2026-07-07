package com.tripod.jraster.asset;

import java.io.InputStream;

// TODO : Need to manage all files / assets through this 
public class GameAssetSystem {

  private final SpriteSheetLoader spriteSheetLoader = new SpriteSheetLoader();
  
  private final AudioLoader audioLoader = new AudioLoader();
  
  private final ResourceLoader resourceLoader = new PlainResourceLoader();

  public GameAssetSystem() {

  }
  
  public InputStream getAssetStream(String path) {
    return this.resourceLoader.loadResource(path);
  }

  public void loadSpriteSheet(String name) {
    this.spriteSheetLoader.load(getAssetStream("/assets/sprites/" + name + ".png"), name);
  }
  
  public SpriteSheet getSpriteSheet(String name) {
    return this.spriteSheetLoader.getSpriteSheet(name);
  }

  public SoundClip loadSoundClip(String name) {
    return this.audioLoader.load(name);
  }
  
  public SoundClip getSound(String name) {
    return this.audioLoader.getSound(name);
  }

  public void clear() {
    this.spriteSheetLoader.clear();
    this.audioLoader.clear();
  }

}
