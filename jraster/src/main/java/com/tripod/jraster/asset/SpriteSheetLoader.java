package com.tripod.jraster.asset;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class SpriteSheetLoader {

  private static final String PATH = "/sprite_sheets/";

  private final HashMap<String, SpriteSheet> cache = new HashMap<>();

  public SpriteSheetLoader() {

  }
  
  public SpriteSheet getSpriteSheet(String name) {
    if (cache.containsKey(name)) {
      return cache.get(name);
    }
    return null;
  }

  public void load(InputStream inputStream, String name) {

    try {
      BufferedImage image = ImageIO
          .read(inputStream);

      int w = image.getWidth();
      int h = image.getHeight();

      int[] p = new int[w * h];
      image.getRGB(0, 0, w, h, p, 0, w);

      SpriteSheet sheet = new SpriteSheet(w, h, p);
      cache.put(name, sheet);

    } catch (IOException e) {
      throw new RuntimeException(
          "Failed to load sprite sheet: " + PATH + name + ".png", e);
    }

  }

  public void clear() {
    cache.clear();
  }

}
