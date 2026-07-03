package com.tripod.jraster.asset;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheetLoader {

  private static final String PATH = "/sprite_sheets/";

  public SpriteSheetLoader() {

  }

  public SpriteSheet load(String name) {

    try {
      BufferedImage image = ImageIO
          .read(SpriteSheetLoader.class.getResource(PATH + name + ".png"));

      int w = image.getWidth();
      int h = image.getHeight();

      int[] p = new int[w * h];
      image.getRGB(0, 0, w, h, p, 0, w);

      return new SpriteSheet(w, h, p);

    } catch (IOException e) {
      throw new RuntimeException("Failed to load sprite sheet: " + PATH + name + ".png",
          e);
    }
    
  }

}
