package com.tripod.jraster.asset;

public class GameAssetManager {
  
  private final SpriteSheetLoader spriteSheetLoader = new SpriteSheetLoader();
  private final AudioLoader audioLoader = new AudioLoader();
  
  public GameAssetManager() {
    
  }
  
  public SpriteSheet loadSpriteSheet(String name) {
    return spriteSheetLoader.load(name);
  }

}
